package br.com.microservices.avaliador.domain.model;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class CartaoAprovado {
  private String cartao;
  private String bandeira;
  private BigDecimal limiteAprovado;
}
