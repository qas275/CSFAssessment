package vttp2022.csf.assessment.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class appConfig {
    
    @Value("${SPACES_ACCESS}")
    public String spacesAccess;
    @Value("${SPACES_SECRET}")
    public String spacesSecret;

    @Bean
    public AmazonS3 getAmazonS3(){
        BasicAWSCredentials cred =  new BasicAWSCredentials(spacesAccess, spacesSecret);
        EndpointConfiguration epConfiguration = new EndpointConfiguration("sgp1.digitaloceanspaces.com", "sgp1");
        return AmazonS3ClientBuilder.standard().withEndpointConfiguration(epConfiguration).withCredentials(new AWSStaticCredentialsProvider(cred)).build();
    }
}
