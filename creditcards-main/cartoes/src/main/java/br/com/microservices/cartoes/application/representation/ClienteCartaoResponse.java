package br.com.microservices.cartoes.application.representation;

import br.com.microservices.cartoes.application.service.ClienteCartaoService;
import br.com.microservices.cartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCartaoResponse {
  private String nome;
  private String bandeira;
  private BigDecimal limiteLiberado;

  public static ClienteCartaoResponse fromModel(
          ClienteCartao model
  ){
    return new ClienteCartaoResponse(
            model.getCartao().getNome(),
            model.getCartao().getBandeira().toString(),
            model.getLimite()
    );
  }
}
