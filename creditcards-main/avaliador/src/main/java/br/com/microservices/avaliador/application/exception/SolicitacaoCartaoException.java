package br.com.microservices.avaliador.application.exception;

public class SolicitacaoCartaoException extends RuntimeException{
  public SolicitacaoCartaoException(String msg) {
    super(msg);
  }
}
