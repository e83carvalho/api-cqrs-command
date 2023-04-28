package br.com.egc.command.controller;

import br.com.egc.command.converter.ReclamacaoConverter;
import br.com.egc.command.dto.ReclamacaoRequest;
import br.com.egc.command.dto.ReclamacaoResponse;
import br.com.egc.command.service.ReclamacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
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

    @PostMapping("/{codigoReclamacao}/imagens")
    public ResponseEntity<ReclamacaoResponse> adicionarImagens(@PathVariable String codigoReclamacao,
                                                               @RequestParam("imagens") List<MultipartFile> imagens) throws IOException {
        return ResponseEntity.ok(
                reclamacaoConverter.toReclamacaoResponse(
                        reclamacaoService.adicionarImagens(codigoReclamacao, imagens)));
    }

    @DeleteMapping("/{codigoReclamacao}/imagens/{nomeArquivo}")
    public void removerImagens(@PathVariable String codigoReclamacao, @PathVariable String nomeArquivo) throws IOException {
        reclamacaoService.removerImagens(codigoReclamacao, nomeArquivo);
    }


}
