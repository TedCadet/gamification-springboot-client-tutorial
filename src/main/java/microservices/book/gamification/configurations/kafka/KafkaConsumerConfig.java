package microservices.book.gamification.configurations.kafka;

import java.util.HashMap;
import java.util.Map;
import microservices.book.gamification.events.MultiplicationSolvedEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;
  @Value("${kafka.groupId}")
  private String groupId;

  @Bean
  public ConsumerFactory<String, MultiplicationSolvedEvent>
  multiplicationEventConsumerFactory() {
    Map<String, Object> props = new HashMap<>();

    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

    return new DefaultKafkaConsumerFactory<>(props,
        new StringDeserializer(),
        new JsonDeserializer<>(MultiplicationSolvedEvent.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, MultiplicationSolvedEvent>
  multiplicationEventKafkaListenerContainerFactory() {

    ConcurrentKafkaListenerContainerFactory<String, MultiplicationSolvedEvent> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(multiplicationEventConsumerFactory());

    return factory;
  }
}
