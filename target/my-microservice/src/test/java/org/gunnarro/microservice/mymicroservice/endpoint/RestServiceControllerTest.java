package org.gunnarro.microservice.mymicroservice.endpoint;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;
import org.gunnarro.microservice.mymicroservice.repository.impl.JdbcRepositoryImpl;
import org.gunnarro.microservice.mymicroservice.service.impl.MyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO Remove or Refactor. This class only serves as an example
 */
@ContextConfiguration(classes = { MyServiceImpl.class, JdbcRepositoryImpl.class })
class RestServiceControllerTest extends DefaultTestConfig {
    @Mock
    private MyServiceImpl myServiceMock;
    @InjectMocks
    private RestServiceController restServiceController;

    @Test
    void isAlive() {
        ResponseEntity<Void> response = restServiceController.isAlive();
        assertThat(response).isNotNull();
        Assertions.assertEquals("<200 OK OK,[]>", response.toString());
    }
}