package br.com.egc.command.service;

import br.com.egc.command.model.Reclamacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReclamacaoService {
    public Reclamacao salvarReclamacao(Reclamacao reclamacao) {
        reclamacao.setUuidReclamacao(UUID.randomUUID().toString());
        reclamacao.setStatus("ABERTO");
        return reclamacao;
    }

    public List<Reclamacao> buscarReclamacao(String uuidCliente, String codigoPedido) {
        List<Reclamacao> reclamacaoList = new ArrayList<>();
        reclamacaoList.add(Reclamacao.builder()
                .uuidReclamacao(UUID.randomUUID().toString())
                .status("ABERTO")
                .build());
        reclamacaoList.add(Reclamacao.builder()
                .uuidReclamacao(UUID.randomUUID().toString())
                .status("ABERTO")
                .build());
        return reclamacaoList;
    }
}
