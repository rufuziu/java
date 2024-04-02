package br.com.microservices.cartoes.application;

import br.com.microservices.cartoes.application.representation.CartaoSaveRequest;
import br.com.microservices.cartoes.application.representation.ClienteCartaoResponse;
import br.com.microservices.cartoes.application.service.CartaoService;
import br.com.microservices.cartoes.application.service.ClienteCartaoService;
import br.com.microservices.cartoes.domain.Cartao;
import br.com.microservices.cartoes.domain.ClienteCartao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cartoes")
@Slf4j
public class CartoesResource {

  private final CartaoService cartaoService;
  private final ClienteCartaoService clienteCartaoService;
  public CartoesResource(CartaoService cartaoService,
                         ClienteCartaoService clienteCartaoService) {
    this.cartaoService = cartaoService;
    this.clienteCartaoService = clienteCartaoService;
  }

  @GetMapping
  public String status() {
    log.info("obtendo o status do microservice de cartoes");
    return "ok";
  }

  @PostMapping
  public ResponseEntity cadastrar(@RequestBody
                             CartaoSaveRequest request) {
    var cartao = request.toModel();
    cartaoService.save(cartao);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
  @GetMapping(params = "renda")
  public ResponseEntity<List<Cartao>> getCartoesRendaMax
  (@RequestParam("renda")Long renda){
    return ResponseEntity.ok(
            cartaoService.getCartoesRendaMenorIgual(renda));
  }

  @GetMapping(params = "cpf")
  public ResponseEntity<List<ClienteCartaoResponse>> getCartoesCliente
          (@RequestParam("cpf")String cpf){
    List<ClienteCartao> lista = clienteCartaoService.getCartoesByCpf(cpf);
    List<ClienteCartaoResponse> resultList =
            lista.stream().map(ClienteCartaoResponse::fromModel)
                    .toList();
    return ResponseEntity.ok(resultList);
  }
}
