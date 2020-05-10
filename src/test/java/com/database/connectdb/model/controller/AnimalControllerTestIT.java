package com.database.connectdb.model.controller;

import com.database.connectdb.model.Animal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(AnimalController.class)
//@ContextConfiguration(classes = ExampleApplicationTests.class)
@SpringBootTest
class AnimalControllerTestIT {


    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    ObjectMapper objectMapper = new ObjectMapper();

    Animal amimalToTest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        amimalToTest =Animal.builder().id(1).name("Red Horse").build();
    }

    @Test
    void createAndFinAnimal() throws Exception {

        // Create a new Animal
        String animal = objectMapper.writeValueAsString(amimalToTest);

        RequestBuilder requestCreate = MockMvcRequestBuilders.post("/animal/create")
                .content(animal).contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult mvcResultCreate = mockMvc.perform(requestCreate).andReturn();
        Integer response = objectMapper.readValue(mvcResultCreate.getResponse().getContentAsString(), Integer.class);

        assertThat(response).isEqualTo(amimalToTest.getId());

        // Obtain a last animal created
        RequestBuilder request = MockMvcRequestBuilders.get("/animal/find/1");
        MvcResult mvcResult = mockMvc.perform(request).andReturn();
        Animal animalResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Animal.class);

        assertThat(animalResponse.getId()).isEqualTo(amimalToTest.getId());
        assertThat(animalResponse.getName()).isEqualTo(amimalToTest.getName());
    }
}