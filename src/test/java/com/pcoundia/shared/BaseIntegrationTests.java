package com.pcoundia.shared;

import com.pcoundia.products.infrastructure.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.config.name=application-test"
)
@Profile("test")

@Slf4j
public class BaseIntegrationTests {

    @LocalServerPort
    public int port;

    @Value("${server.address:localhost}")
    public String host;

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public WebTestClient webTestClient;

    @BeforeEach
    public void cleanDatabase() {
        productRepository.deleteAll().block();
    }

    public String getBaseUrl() {
        String uri = "http://"+this.host + ":" + this.port + "/api";

        log.info("***************** URI ********************");
        log.info(uri);

        return uri;
    }

    public WebTestClient.ResponseSpec get(String uri) {
        return webTestClient
            .get()
            .uri(this.getBaseUrl() + uri)
            .exchange();
    }

    public WebTestClient.ResponseSpec delete(String uri) {
        return webTestClient
            .delete()
            .uri(this.getBaseUrl() + uri)
            .exchange();
    }

    public WebTestClient.ResponseSpec post(String uri,Object request) {
        return webTestClient
            .post()
            .uri(this.getBaseUrl() + uri)
            .bodyValue(request)
            .exchange();
    }
    public WebTestClient.ResponseSpec put(String uri,Object request) {
        return webTestClient
            .put()
            .uri(this.getBaseUrl() + uri)
            .bodyValue(request)
            .exchange();
    }
}
