package com.grainger.cimprofile.interview.userprofile.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Slf4j
public class KafkaConfig {

  @Bean
  public NewTopic topic() {
    return TopicBuilder.name("profile")
      .partitions(3)
      .replicas(1)
      .build();
  }

  @KafkaListener(id = "id", topics = "profile")
  public void listen(String in) {
    log.info("Profile topic consumed: " + in);
  }

}
