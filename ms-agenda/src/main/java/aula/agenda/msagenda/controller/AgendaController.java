package aula.agenda.msagenda.controller;

import aula.agenda.msagenda.dto.AgendaDTO;
import aula.agenda.msagenda.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    @PostMapping("/")
    public String incluirAgenda(@RequestBody AgendaDTO dto) {
        agendaService.registrarAgenda(dto);
        return "Agendamento feito com sucesso!";
    }

    @GetMapping("/")
    public List<AgendaDTO> listarTodasAsAgendas() {
        return agendaService.listarTodas();
    }
}
