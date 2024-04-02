package br.com.microservices.avaliador.application;

import br.com.microservices.avaliador.application.exception.ComunicacaoMicrosservicesException;
import br.com.microservices.avaliador.application.exception.DadosClienteNotFoundException;
import br.com.microservices.avaliador.application.exception.SolicitacaoCartaoException;
import br.com.microservices.avaliador.application.service.AvaliadorCreditoService;
import br.com.microservices.avaliador.domain.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes-credito")
@Slf4j
public class AvaliadorController {

  private final AvaliadorCreditoService service;

  public AvaliadorController(AvaliadorCreditoService service) {
    this.service = service;
  }

  @GetMapping
  public String status() {
    log.info("obtendo o status do microservice de clientes");
    return "ok";
  }

  @GetMapping(value = "situacao-cliente", params = "cpf")
  public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
    try {
      SituacaoCliente situacaoCliente =
              service.obterSituacaoCliente(cpf);
      return ResponseEntity.ok(situacaoCliente);
    } catch (DadosClienteNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (ComunicacaoMicrosservicesException e) {
      return ResponseEntity.status(
              HttpStatus.resolve(e.getStatus()))
              .body(e.getMessage());
    }
  }
@PostMapping
  public ResponseEntity realizarAvaliacao(
          @RequestBody DadosAvaliacao dados){
  try {
    RetornoAvaliacaoCliente retorno =
            service.realizarAvaliacao(dados.getCpf(),dados.getRenda());
    return ResponseEntity.ok(retorno);
  } catch (DadosClienteNotFoundException e) {
    return ResponseEntity.notFound().build();
  } catch (ComunicacaoMicrosservicesException e) {
    return ResponseEntity.status(
                    HttpStatus.resolve(e.getStatus()))
            .body(e.getMessage());
  }
  }

  @PostMapping("solicitacoes-cartao")
  public ResponseEntity solicitarCartao
          (@RequestBody DadosSolicitacaoEmissaoCartao dados){
    try{
      ProtocoloSolicitacaoCartao
              protocoloSolicitacaoCartao =
              service.solicitarEmissaoCartao(dados);
        return ResponseEntity.ok(protocoloSolicitacaoCartao);
    }catch (SolicitacaoCartaoException ex){
      return ResponseEntity.internalServerError()
              .body(ex.getMessage());
    }
  }
}
