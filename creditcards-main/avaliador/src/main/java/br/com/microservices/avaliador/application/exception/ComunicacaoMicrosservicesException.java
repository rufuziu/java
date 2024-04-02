package br.com.microservices.avaliador.application.exception;

import lombok.Getter;

public class ComunicacaoMicrosservicesException extends Exception{
  @Getter
  private Integer status;
  public ComunicacaoMicrosservicesException(String msg,
                                            Integer status) {
    super(msg);
    this.status = status;
  }
}
