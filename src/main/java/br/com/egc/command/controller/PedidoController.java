package br.com.egc.command.controller;

import br.com.egc.command.converter.PedidoConverter;
import br.com.egc.command.dto.PedidoRequest;
import br.com.egc.command.dto.PedidoResponse;
import br.com.egc.command.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController {

    private final PedidoService pedidoService;

    private final PedidoConverter pedidoConverter;


    @PostMapping()
    public ResponseEntity<PedidoResponse> adicionarPedido(@Valid @RequestBody PedidoRequest pedidoRequest) {
        return ResponseEntity.ok(
                pedidoConverter.toPedidoResponse(
                        pedidoService.salvar(
                                pedidoConverter.toPedido(pedidoRequest))));
    }

}
