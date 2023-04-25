package br.com.egc.command.repository;

import br.com.egc.command.model.Reclamacao;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReclamacaoRepository {

    private final DynamoDBMapper mapper;


    public Reclamacao save(Reclamacao reclamacao) {
        mapper.save(reclamacao);
        return reclamacao;
    }

    public Reclamacao findByCodigoReclamacao(String codigoReclamacao) {
        return mapper.load(Reclamacao.class, codigoReclamacao);

    }

    public Reclamacao update(String codigoReclamacao, Reclamacao reclamacao) {
        mapper.save(reclamacao, new DynamoDBSaveExpression().withExpectedEntry("codigoReclamacao",
                new ExpectedAttributeValue(new AttributeValue().withS(codigoReclamacao))));
        return reclamacao;
    }

    public Boolean delete(String codigoReclamacao) {
        mapper.delete(codigoReclamacao);
        return Boolean.TRUE;
    }

}
