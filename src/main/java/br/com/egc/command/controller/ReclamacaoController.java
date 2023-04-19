package br.com.egc.command.controller;

import br.com.egc.command.converter.ReclamacaoConverter;
import br.com.egc.command.dto.ReclamacaoRequest;
import br.com.egc.command.dto.ReclamacaoResponse;
import br.com.egc.command.dto.ReclamacaoSearchRequest;
import br.com.egc.command.service.ReclamacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/reclamacoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReclamacaoController {

    private final ReclamacaoService reclamacaoService;

    private final ReclamacaoConverter reclamacaoConverter;


    @PostMapping()
    public ResponseEntity<ReclamacaoResponse> adicionarReclamacao(@Valid @RequestBody ReclamacaoRequest reclamacaoRequest) {
        return ResponseEntity.ok(
                reclamacaoConverter.toReclamacaoResponse(
                        reclamacaoService.salvarReclamacao(
                                reclamacaoConverter.toReclamacao(reclamacaoRequest))));
    }

    @GetMapping
    public ResponseEntity<List<ReclamacaoResponse>> buscarReclamacao(@Valid ReclamacaoSearchRequest reclamacaoSearchRequest) {
        return ResponseEntity.ok(
                reclamacaoConverter.toReclamacaoResponseList(
                        reclamacaoService.buscarReclamacao(
                                reclamacaoSearchRequest.getUuidCliente(), reclamacaoSearchRequest.getCodigoPedido())));
    }

}
