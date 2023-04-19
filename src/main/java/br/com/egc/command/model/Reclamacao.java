package br.com.egc.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reclamacao {
    private String uuidReclamacao;
    private String uuidCliente;
    private String codigoPedido;
    private String descricao;
    private String status;

}