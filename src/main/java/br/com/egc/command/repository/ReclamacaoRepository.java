//package br.com.egc.command.repository;
//
//import br.com.egc.command.model.Reclamacao;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
//import com.amazonaws.services.dynamodbv2.datamodeling.S3Link;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//
//import java.io.File;
//import java.net.URI;
//import java.util.List;
//import java.util.UUID;
//
//public class ReclamacaoRepository {
//
//    private final DynamoDBMapper mapper;
//    private final AmazonS3 s3Client;
//    private final String bucketName;
//
//    public ReclamacaoRepository(String region, String bucketName) {
//        this.mapper = new DynamoDBMapper(buildDynamoDBClient(region));
//        this.s3Client = buildS3Client(region);
//        this.bucketName = bucketName;
//    }
//
//    private AmazonDynamoDB buildDynamoDBClient(String region) {
//        return AmazonDynamoDBClientBuilder.standard()
//                .withRegion(region)
//                .build();
//    }
//
//    private AmazonS3 buildS3Client(String region) {
//        String s3Endpoint = String.format("https://s3.%s.amazonaws.com", region);
//        URI s3URI = URI.create(s3Endpoint);
//        return AmazonS3ClientBuilder.standard()
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3URI.toString(), region))
//                .build();
//    }
//
//    public void salvarReclamacao(Reclamacao reclamacao, List<File> imagens) {
//        // Gerar UUID para a reclamação e para as imagens
//        UUID uuid = UUID.randomUUID();
//        reclamacao.setUuid(uuid);
//        for (int i = 0; i < imagens.size(); i++) {
//            S3Link imagemLink = mapper.createS3Link(
//                    s3Client.getRegion(),
//                    bucketName,
//                    String.format("imagens/reclamacao-%s-imagem-%d", uuid.toString(), i)
//            );
//            reclamacao.getImagens().add(imagemLink);
//
//            // Enviar imagem para o S3
//            File imagem = imagens.get(i);
//            s3Client.putObject(new PutObjectRequest(bucketName, imagemLink.getBucketName(), imagem));
//        }
//
//        // Salvar reclamação no DynamoDB
//        mapper.save(reclamacao);
//    }
//}
