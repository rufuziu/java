package br.com.microservices.avaliador.infra.mqueue;

import br.com.microservices.avaliador.domain.model.DadosSolicitacaoEmissaoCartao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {
  private final RabbitTemplate rabbitTemplate;
  private final Queue queueEmissaoCartoes;

  public void solicitarEmissaoCartao(
          DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
    var json = converIntoJson(dados);
    rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);
  }

  private String converIntoJson(DadosSolicitacaoEmissaoCartao dados)
          throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    var json = mapper.writeValueAsString(dados);
    return json;
  }
}
