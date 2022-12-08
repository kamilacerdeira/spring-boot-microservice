# Trabalho A3

## Backend em microserviços para agendamento de salas.

Criado os serviços para agendamento de salas por parte dos funcionários de uma empresa.

## Utiliza-se

* ConfigServer para banco em configuração em arquivo local porta 9090
* Banco H2 persistindo em disco
* Eureka Service Discovery porta 8761
* Spring Gateway porta 8000
* Microservice funcionario porta 8092
* Microservice sala porta 8091
* Microservice agenda porta 9095

### Config server

É necessário atualizar o caminho dos arquivos onde estarão as configurações de banco. Eles se encontram no mesmo
projeto, porém o caminho é absoluto e é necessário atualizar no arquivo application.yml do cf-server-config
