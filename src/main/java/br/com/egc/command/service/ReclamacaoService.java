package br.com.egc.command.service;

import br.com.egc.command.model.Reclamacao;
import br.com.egc.command.repository.PedidoRepository;
import br.com.egc.command.repository.ReclamacaoRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReclamacaoService {

    private final ReclamacaoRepository reclamacaoRepository;

    private final PedidoRepository pedidoRepository;

    private final AmazonS3 s3Client;
    @Value("${aws.s3.bucketname}")
    private String bucketName;

    public Reclamacao salvarReclamacao(Reclamacao reclamacao) {

        var pedido = pedidoRepository.findByCodigoPedido(reclamacao.getCodigoPedido());

        if (pedido == null) {
            throw new RuntimeException("Pedido não localizado");
        }

        if (!pedido.getCodigoCliente().equals(reclamacao.getCodigoCliente())) {
            throw new RuntimeException("Pedido não pertence ao cliente informado");
        }

        reclamacao.setStatusReclamacao("ABERTO");
        reclamacaoRepository.save(reclamacao);

        if (pedido.getCodigoReclamacoes() == null) {
            pedido.setCodigoReclamacoes(new ArrayList<>());
        }
        pedido.getCodigoReclamacoes().add(reclamacao.getCodigoReclamacao());
        pedidoRepository.update(reclamacao.getCodigoPedido(), pedido);

        return reclamacao;
    }

    public Reclamacao buscarReclamacao(String codigoReclamacao) {

        var reclamacao = reclamacaoRepository.findByCodigoReclamacao(codigoReclamacao);

        if (reclamacao == null) {
            throw new RuntimeException("Reclamação não localizada");
        }

        return reclamacao;
    }

    public Reclamacao adicionarImagens(String codigoReclamacao, List<MultipartFile> imagens) throws IOException {

        var reclamacao = buscarReclamacao(codigoReclamacao);

        if (reclamacao.getImagens() == null) {
            reclamacao.setImagens(new ArrayList<>());
        }
        var totalImagens = imagens.size() + reclamacao.getImagens().size();

        if (totalImagens > 5) {
            throw new RuntimeException("Limite máximo de 5 imagens excedido");
        }

        for (MultipartFile imagem : imagens) {
            if (imagem.getSize() > 10 * 1024 * 1024) {
                throw new RuntimeException("Imagem " + imagem.getOriginalFilename() + " excede o tamanho máximo permitido de 10MB");
            }
        }

        for (MultipartFile imagem : imagens) {
            String nomeArquivo = imagem.getOriginalFilename();
            String key = UUID.randomUUID().toString() + "-" + imagem.getOriginalFilename();
            byte[] conteudo = imagem.getBytes();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(imagem.getContentType());
            metadata.setContentLength(conteudo.length);

            var putObjectRequest = new PutObjectRequest(bucketName, "reclamacao/" + key, new ByteArrayInputStream(conteudo), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            s3Client.putObject(putObjectRequest);

            var url = s3Client.getUrl(bucketName, "reclamacao/" + key);

            reclamacao.getImagens().add(url.toString());

        }

        reclamacaoRepository.save(reclamacao);

        return reclamacao;
    }

    public void removerImagens(String codigoReclamacao, String nomeArquivo) throws RuntimeException {


        var reclamacao = buscarReclamacao(codigoReclamacao);

        if (reclamacao.getImagens().contains(nomeArquivo)) {

            reclamacao.getImagens().remove(nomeArquivo);

            String caminhoArquivo = "/reclamacao/" + nomeArquivo;

            var deleteObjectRequest = new DeleteObjectRequest(
                    bucketName, caminhoArquivo);
            try {
                s3Client.deleteObject(deleteObjectRequest);
            } catch (Exception e) {
                throw new RuntimeException("Não foi possível excluir arquivo");
            }

            reclamacaoRepository.save(reclamacao);

        } else {
            throw new RuntimeException("Arquivo não localizado na reclamação");
        }


    }

    public String buscarImagens(String codigoReclamacao, String nomeArquivo) throws RuntimeException {


        var reclamacao = buscarReclamacao(codigoReclamacao);

        if (reclamacao.getImagens().contains(nomeArquivo)) {

            var url = s3Client.getUrl(bucketName, "reclamacao/" + nomeArquivo);

            return url.toString();

        } else {
            throw new RuntimeException("Arquivo não localizado na reclamação");
        }


    }

}
