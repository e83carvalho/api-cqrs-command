package br.com.egc.command.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "reclamacao")
public class Reclamacao {


    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String codigoReclamacao;
    @DynamoDBAttribute
    private String codigoCliente;
    @DynamoDBAttribute
    private String codigoPedido;
    @DynamoDBAttribute
    private String descricao;
    @DynamoDBAttribute
    private String status;
    @DynamoDBAttribute
    private List<String> files;

}