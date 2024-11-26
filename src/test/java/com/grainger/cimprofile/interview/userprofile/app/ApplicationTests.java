package com.grainger.cimprofile.interview.userprofile.app;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  ConsumerFactory<String, String> cf;

  @Test
  void contextLoads() {
  }

  @Test
  void createProfile() throws Exception {
    String request = """
      {
        "name": "frodo baggins",
        "email": "frodo@lotr.com"
      }
      """;

    String expectedResponse = """
      {
        "name": "frodo baggins",
        "email": "frodo@lotr.com"
      }
      """;

    mockMvc.perform(post("/profiles")
        .contentType("application/json")
        .content(request))
      .andExpect(status().isCreated())
      .andExpect(MockMvcResultMatchers.content().json(expectedResponse));

    try (Consumer<String, String> consumerTest = cf.createConsumer("test_id", "test")) {
      consumerTest.subscribe(Collections.singleton("profile"));
      ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumerTest);
      ConsumerRecord<String, String> kafkaRecord = records.iterator().next();
      JSONAssert.assertEquals(expectedResponse, kafkaRecord.value(), false);
    }

  }

}
