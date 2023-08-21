package microservices.book.gamification.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.events.MultiplicationSolvedEvent;
import microservices.book.gamification.services.GameService;
import org.apache.kafka.clients.consumer.OffsetOutOfRangeException;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class MultiplicationEventHandler {

  private GameService gameService;

  @KafkaListener(
      topics = "multiplications-events",
      containerFactory = "multiplicationEventKafkaListenerContainerFactory"
  )
  public void handleMultiplicationSolvedEvent(
      MultiplicationSolvedEvent multiplicationSolvedEvent) {
    log.info("Multiplication solved event received: {}",
        multiplicationSolvedEvent.multiplicationResultAttemptId());

    try {
      gameService.newAttemptForUser(multiplicationSolvedEvent.userId(),
          multiplicationSolvedEvent.multiplicationResultAttemptId(),
          multiplicationSolvedEvent.correct());
    } catch (KafkaException ex) {
      log.error("error while consumming messages from Kafka", ex);
    } catch (OffsetOutOfRangeException ex) {
      log.error("Offset out of range error occurred while consuming messages from Kafka", ex);
    } catch (Exception ex) {
      log.error("error when trying to process MultiplicationSolvedEvent", ex);
    }
  }
}
