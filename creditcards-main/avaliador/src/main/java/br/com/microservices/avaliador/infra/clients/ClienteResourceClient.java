package br.com.microservices.avaliador.infra.clients;

import br.com.microservices.avaliador.domain.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "clientes", path="/clientes")
public interface ClienteResourceClient {
  @GetMapping(params = "cpf")
  ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);
}
