package br.com.microservices.cartoes.application.representation;

import br.com.microservices.cartoes.domain.BandeiraCartao;
import br.com.microservices.cartoes.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {
  private String nome;
  private BandeiraCartao bandeira;
  private BigDecimal renda;
  private BigDecimal limite;
  public Cartao toModel(){
    return new Cartao(nome,bandeira,renda,limite);
  }
}
