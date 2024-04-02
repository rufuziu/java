package br.com.microservices.cartoes.application.service;

import br.com.microservices.cartoes.domain.ClienteCartao;
import br.com.microservices.cartoes.infra.repository.ClienteCartaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteCartaoService {
  private final ClienteCartaoRepository repository;

  public ClienteCartaoService(ClienteCartaoRepository repository) {
    this.repository = repository;
  }

  public List<ClienteCartao> getCartoesByCpf(String cpf) {
    return repository.findByCpf(cpf);
  }
}
