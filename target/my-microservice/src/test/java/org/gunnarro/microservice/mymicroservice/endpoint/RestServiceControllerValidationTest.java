package org.gunnarro.microservice.mymicroservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;
import org.gunnarro.microservice.mymicroservice.domain.subscription.Subscription;
import org.gunnarro.microservice.mymicroservice.repository.DbRepository;
import org.gunnarro.microservice.mymicroservice.service.impl.MyServiceImpl;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Disabled
@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {MyServiceImpl.class, DbRepository.class})
class RestServiceControllerValidationTest extends DefaultTestConfig {

    @Autowired
    private RestServiceController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private MyServiceImpl myServiceMock;

    @Override
    @BeforeEach
    public void before() {
        super.before();
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void whenControllerInjectedThenNotNull() {
        Assertions.assertNotNull(controller);
    }

    @Test
    void updateSubscriptionValidateDataInputFailed() throws Exception {
        Subscription subscription = Subscription.builder()
                .subscriptionId(12345678)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/restservice/v1/subscriptions/12345678")
                .content(objectMapper.writeValueAsString(subscription))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpMessage", Is.is("Bad Request, {billingType=billingType can not be null or empty}")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getSubscription() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restservice/v1/subscriptions/12345678")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
