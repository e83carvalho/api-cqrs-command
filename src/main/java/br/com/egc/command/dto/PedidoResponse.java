package br.com.egc.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoResponse {

    private String codigoPedido;

    private String codigoCliente;

    private String status;

}
