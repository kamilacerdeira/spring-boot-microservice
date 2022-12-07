package aula.agenda.msagenda.service;

import aula.agenda.msagenda.dto.AgendaDTO;
import aula.agenda.msagenda.dto.DadosFuncionarioDTO;
import aula.agenda.msagenda.dto.DadosSalaDTO;
import aula.agenda.msagenda.model.Agenda;
import aula.agenda.msagenda.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AgendaService {

    private static final String HTTP_SALA_SALA = "http://sala/sala/";
    private static final String HTTP_FUNCIONARIO_FUNCIONARIO = "http://funcionario/funcionario/";


    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private RestTemplate restTemplate;

    public void registrarAgenda(AgendaDTO dto) {
        DadosSalaDTO salaDTO = consumirSala(dto);
        DadosFuncionarioDTO funcionarioDTO = consumirFuncionario(dto);

        Agenda agenda = new Agenda();
        agenda.setResponsavel(funcionarioDTO.getId());
        agenda.setSala(salaDTO.getId());
        agenda.setDataHoraReserva(dto.converteParaAgenda().getDataHoraReserva());
        agendaRepository.save(agenda);
    }

    private DadosSalaDTO consumirSala(AgendaDTO dto) {
        ResponseEntity<DadosSalaDTO> exchange =
                restTemplate.exchange(HTTP_SALA_SALA + dto.getIdSala(),
                        HttpMethod.GET, null, DadosSalaDTO.class);
        DadosSalaDTO salaDTO = exchange.getBody();
        return salaDTO;
    }

    private DadosFuncionarioDTO consumirFuncionario(AgendaDTO dto) {
        ResponseEntity<DadosFuncionarioDTO> exchange =
                restTemplate.exchange(HTTP_FUNCIONARIO_FUNCIONARIO + dto.getIdResponsavel(),
                        HttpMethod.GET, null, DadosFuncionarioDTO.class);
        DadosFuncionarioDTO funcionarioDTO = exchange.getBody();
        return funcionarioDTO;
    }

    public List<AgendaDTO> listarTodas() {
        List<Agenda> agendaList = agendaRepository.findAll();
        List<AgendaDTO> infoAgendaDTOS = new ArrayList<>();
        for (Agenda agenda : agendaList) {
            AgendaDTO dto = new AgendaDTO();
            dto.setId(agenda.getId());
            dto.setIdResponsavel(agenda.getResponsavel());
            dto.setResponsavel(buscarNomeFuncionario(agenda.getResponsavel()));
            dto.setIdSala(agenda.getSala());
            dto.setSala(buscarNomeSala(agenda.getSala()));
            dto.setDataHoraReserva(agenda.getDataHoraReserva().toString());
            infoAgendaDTOS.add(dto);
        }
        return infoAgendaDTOS;
    }

    private String buscarNomeFuncionario(Long responsavelID) {
        ResponseEntity<DadosFuncionarioDTO> exchange =
                restTemplate.exchange(HTTP_FUNCIONARIO_FUNCIONARIO + responsavelID,
                        HttpMethod.GET, null, DadosFuncionarioDTO.class);
        DadosFuncionarioDTO funcionarioDTO = exchange.getBody();
        return funcionarioDTO.getNome();
    }

    private String buscarNomeSala(Long salaID) {
        ResponseEntity<DadosSalaDTO> exchange =
                restTemplate.exchange(HTTP_SALA_SALA + salaID,
                        HttpMethod.GET, null, DadosSalaDTO.class);
        DadosSalaDTO salaDTO = exchange.getBody();
        return salaDTO.getNome();
    }
}
