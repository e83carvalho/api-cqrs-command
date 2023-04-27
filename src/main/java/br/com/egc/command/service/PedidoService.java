package br.com.egc.command.service;

import br.com.egc.command.model.Pedido;
import br.com.egc.command.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public Pedido salvar(Pedido pedido) {
        pedido.setStatus("ABERTO");
        pedidoRepository.save(pedido);
        return pedido;
    }

}
