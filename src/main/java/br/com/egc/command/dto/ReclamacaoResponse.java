package br.com.egc.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReclamacaoResponse {

    private String uuidReclamacao;

    private String uuidCliente;

    private String codigoPedido;

    private String descricao;

    private String status;
}
