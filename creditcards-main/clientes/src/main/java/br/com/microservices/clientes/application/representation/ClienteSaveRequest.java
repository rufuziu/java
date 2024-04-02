package br.com.microservices.clientes.application.representation;

import br.com.microservices.clientes.domain.Cliente;
import lombok.Data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Data
public class ClienteSaveRequest {
  private String cpf;
  private String nome;
  private Integer idade;
  public Cliente toModel(){
    return new Cliente(cpf,nome,idade);
  }
}
