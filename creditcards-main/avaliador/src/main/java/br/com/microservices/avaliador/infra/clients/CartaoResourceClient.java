package br.com.microservices.avaliador.infra.clients;

import br.com.microservices.avaliador.domain.model.Cartao;
import br.com.microservices.avaliador.domain.model.ClienteCartao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "cartoes", path="/cartoes")
public interface CartaoResourceClient {
  @GetMapping(params = "cpf")
  ResponseEntity<List<ClienteCartao>> getCartoesCliente
          (@RequestParam("cpf")String cpf);
  @GetMapping(params = "renda")
  public ResponseEntity<List<Cartao>> getCartoesRendaMax
          (@RequestParam("renda")Long renda);
}
