package br.com.egc.command.repository;

import br.com.egc.command.model.Pedido;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PedidoRepository {

    private final DynamoDBMapper mapper;

    public Pedido save(Pedido pedido) {
        mapper.save(pedido);
        return pedido;
    }

    public Pedido findByCodigoPedido(String codigoPedido) {
        return mapper.load(Pedido.class, codigoPedido);

    }

    public Pedido update(String codigoPedido, Pedido pedido) {
        mapper.save(pedido, new DynamoDBSaveExpression().withExpectedEntry("codigoPedido",
                new ExpectedAttributeValue(new AttributeValue().withS(codigoPedido))));
        return pedido;
    }


}
