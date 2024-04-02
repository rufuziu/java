package br.com.microservices.cartoes.infra.mqueue;

import br.com.microservices.cartoes.domain.Cartao;
import br.com.microservices.cartoes.domain.ClienteCartao;
import br.com.microservices.cartoes.domain.DadosSolicitacaoEmissaoCartao;
import br.com.microservices.cartoes.infra.repository.CartaoRepository;
import br.com.microservices.cartoes.infra.repository.ClienteCartaoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {
  private final CartaoRepository cartaoRepository;
  private final ClienteCartaoRepository clienteCartaoRepository;
  @RabbitListener(queues = "${mq.queues.cartoes-emissao}")
  public void receberSolicitacaoEmissao(@Payload String payload){
    try{
      var mapper = new ObjectMapper();
      DadosSolicitacaoEmissaoCartao dados =
      mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
      Cartao cartao =
              cartaoRepository
                      .findById(dados.getIdCartao()).orElseThrow();
      ClienteCartao clienteCartao = new ClienteCartao();
      clienteCartao.setCartao(cartao);
      clienteCartao.setCpf(dados.getCpf());
      clienteCartao.setLimite(dados.getLimiteLiberado());

      clienteCartaoRepository.save(clienteCartao);

    }catch (Exception ex){
      log.error("Erro ao receber solicitação de emissão de cartão: {}",
              ex.getMessage());
    }

  }
}
