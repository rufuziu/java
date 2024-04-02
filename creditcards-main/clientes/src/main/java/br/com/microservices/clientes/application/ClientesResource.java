package br.com.microservices.clientes.application;

import br.com.microservices.clientes.application.representation.ClienteSaveRequest;
import br.com.microservices.clientes.application.service.ClienteService;
import br.com.microservices.clientes.domain.Cliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@Slf4j
public class ClientesResource {

  private final ClienteService service;

  public ClientesResource(ClienteService service) {
    this.service = service;
  }

  @GetMapping
  public String status() {
    log.info("obtendo o status do microservice de clientes");
    return "ok";
  }

  @PostMapping
  public ResponseEntity save(@RequestBody
                             ClienteSaveRequest request) {
    var cliente = request.toModel();
    service.save(cliente);
    URI headerLocation = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .query("cpf={cpf}")
            .buildAndExpand(cliente.getCpf())
            .toUri();
    return ResponseEntity.created(headerLocation).build();
  }

  @GetMapping(params = "cpf")
  public ResponseEntity dadosCliente(
          @RequestParam("cpf") String cpf){
    var cliente = service.getByCpf(cpf);
    return cliente.isEmpty() ?
            ResponseEntity.notFound().build() :
            ResponseEntity.ok(cliente);
  }
}
