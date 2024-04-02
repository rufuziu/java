package br.com.microservices.cartoes.infra.repository;

import br.com.microservices.cartoes.domain.Cartao;
import br.com.microservices.cartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ClienteCartaoRepository
        extends JpaRepository<ClienteCartao,Long> {
  List<ClienteCartao> findByCpf(String cpf);
}
