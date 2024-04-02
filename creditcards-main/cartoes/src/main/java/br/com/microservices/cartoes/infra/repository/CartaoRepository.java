package br.com.microservices.cartoes.infra.repository;

import br.com.microservices.cartoes.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartaoRepository
        extends JpaRepository<Cartao,Long> {
  List<Cartao> findByRendaLessThanEqual(BigDecimal renda);
}
