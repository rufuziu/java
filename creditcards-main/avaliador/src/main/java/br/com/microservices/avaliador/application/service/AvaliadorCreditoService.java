package br.com.microservices.avaliador.application.service;

import br.com.microservices.avaliador.application.exception.ComunicacaoMicrosservicesException;
import br.com.microservices.avaliador.application.exception.DadosClienteNotFoundException;
import br.com.microservices.avaliador.application.exception.SolicitacaoCartaoException;
import br.com.microservices.avaliador.domain.model.*;
import br.com.microservices.avaliador.infra.clients.CartaoResourceClient;
import br.com.microservices.avaliador.infra.clients.ClienteResourceClient;
import br.com.microservices.avaliador.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
  private final ClienteResourceClient clienteResourceClient;
  private final CartaoResourceClient cartaoResourceClient;
  private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

  public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ComunicacaoMicrosservicesException {
    try {
      ResponseEntity<DadosCliente> dadosClienteResponse =
              clienteResourceClient.dadosCliente(cpf);
      ResponseEntity<List<ClienteCartao>> clienteCartaoResponse =
              cartaoResourceClient.getCartoesCliente(cpf);
      return SituacaoCliente
              .builder()
              .cliente(dadosClienteResponse.getBody())
              .cartoes(clienteCartaoResponse.getBody())
              .build();
    } catch(FeignException.FeignClientException ex){
      int status = ex.status();
      if(HttpStatus.NOT_FOUND.value() == status){
        throw new DadosClienteNotFoundException();
      }
      throw new ComunicacaoMicrosservicesException(ex.getMessage(),
              status);
    }
  }

  public RetornoAvaliacaoCliente realizarAvaliacao(String cpf,
                                                   Long renda)
          throws DadosClienteNotFoundException,
          ComunicacaoMicrosservicesException {
    try {
      ResponseEntity<DadosCliente> dadosClienteResponse =
              clienteResourceClient.dadosCliente(cpf);
      ResponseEntity<List<Cartao>> cartaoResponse =
              cartaoResourceClient.getCartoesRendaMax(renda);

      List<Cartao> cartoes = cartaoResponse.getBody();
      var listaAprovados =
      cartoes.stream().map(cartao -> {
        DadosCliente dadosCliente = dadosClienteResponse.getBody();

        BigDecimal limiteBasico = cartao.getLimiteBasico();
        BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
        var fator = idadeBD.divide(BigDecimal.valueOf(10));
        BigDecimal limiteAprovado =  fator.multiply(limiteBasico);

        CartaoAprovado aprovado = new CartaoAprovado();
        aprovado.setCartao(cartao.getNome());
        aprovado.setBandeira(cartao.getBandeira());
        aprovado.setLimiteAprovado(limiteAprovado);
        return aprovado;
      }).toList();

      return RetornoAvaliacaoCliente
              .builder()
              .cartoes(listaAprovados)
              .build();
    } catch(FeignException.FeignClientException ex){
      int status = ex.status();
      if(HttpStatus.NOT_FOUND.value() == status){
        throw new DadosClienteNotFoundException();
      }
      throw new ComunicacaoMicrosservicesException
              (ex.getMessage(),
              status);
    }
  }

  public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
    try{
      emissaoCartaoPublisher.solicitarEmissaoCartao(dados);
      return new ProtocoloSolicitacaoCartao(UUID.randomUUID().toString());
    }catch (Exception ex){
      throw new SolicitacaoCartaoException(ex.getMessage());
    }
  }
}
