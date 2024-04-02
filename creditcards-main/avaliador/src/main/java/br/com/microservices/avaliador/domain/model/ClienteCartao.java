package br.com.microservices.avaliador.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClienteCartao {
  private String nome;
  private String bandeira;
  private BigDecimal limiteLiberado;
}
