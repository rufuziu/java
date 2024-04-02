package br.com.microservices.avaliador.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetornoAvaliacaoCliente {
private List<CartaoAprovado> cartoes;
}
