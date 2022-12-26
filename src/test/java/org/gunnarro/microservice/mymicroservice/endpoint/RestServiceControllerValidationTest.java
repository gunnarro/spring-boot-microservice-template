package org.gunnarro.microservice.mymicroservice.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;
import org.gunnarro.microservice.mymicroservice.domain.subscription.Subscription;
import org.gunnarro.microservice.mymicroservice.repository.DbRepository;
import org.gunnarro.microservice.mymicroservice.service.impl.MyServiceImpl;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {RestServiceController.class, RestExceptionHandler.class})
class RestServiceControllerValidationTest extends DefaultTestConfig {

    @Autowired
    private RestServiceController controller;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MyServiceImpl myServiceMock;

    @MockBean
    DbRepository jdbcRepository;

    @Override
    @BeforeEach
    public void before() {
        super.before();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void whenControllerInjectedThenNotNull() {
        Assertions.assertNotNull(controller);
    }

    @Test
    void createSubscriptionInputValidationError() throws Exception {
        Subscription subscription = Subscription.builder()
                .subscriptionId(12345678)
                .customerId(23)
                .name("mobile-#")
                .type("data")
                .password("mypass")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.post("/restservice/v1/subscriptions")
                        .content(objectMapper.writeValueAsString(subscription))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Is.is("Service Input Validation Error. name Can only contain lower and uppercase alphabetic chars.")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateSubscriptionInputValidationError() throws Exception {
        Subscription subscription = Subscription.builder()
                .subscriptionId(12345678)
                .customerId(23)
                .name("mobiledata-{#}")
                .type("")
                .password("mypass")
                .build();
        mockMvc.perform(MockMvcRequestBuilders.put("/restservice/v1/subscriptions/12345678")
                        .content(objectMapper.writeValueAsString(subscription))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Is.is("Service Input Validation Error. name Can only contain lower and uppercase alphabetic chars., type Can only contain lower and uppercase alphabetic chars.")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getSubscription() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/restservice/v1/subscriptions/12345678")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }
}
