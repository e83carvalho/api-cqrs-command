package br.com.egc.command.converter;


import br.com.egc.command.dto.PedidoRequest;
import br.com.egc.command.dto.PedidoResponse;
import br.com.egc.command.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoConverter {

    private final ModelMapper modelMapper;

    public PedidoResponse toPedidoResponse(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResponse.class);
    }

    public Pedido toPedido(PedidoRequest pedidoRequest) {
        return modelMapper.map(pedidoRequest, Pedido.class);
    }

    public List<PedidoResponse> toPedidoResponseList(List<Pedido> pedidoList) {
        return pedidoList.stream().map(this::toPedidoResponse).collect(Collectors.toList());
    }
}