package br.com.microservices.cartoes.application.service;

import br.com.microservices.cartoes.domain.Cartao;
import br.com.microservices.cartoes.infra.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartaoService {
  private final CartaoRepository repository;

  public CartaoService(CartaoRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public Cartao save(Cartao cartao) {
    return repository.save(cartao);
  }
  public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
    var rendaBigDecimal =
            BigDecimal.valueOf(renda);
    return repository.findByRendaLessThanEqual(rendaBigDecimal);
  }
}
