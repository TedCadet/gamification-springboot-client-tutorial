package microservices.book.gamification.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record ScoreCard(String cardId,
                        String userId,
                        String attemptId,
                        Long scoreTimestamp,
                        int score) {
  public static int DEFAULT_SCORE = 10;

  public ScoreCard() {
    this(null, null, null, 0L, 0);
  }

  public ScoreCard(String userId, String attemptId) {
    this(null, userId, attemptId, System.currentTimeMillis(), DEFAULT_SCORE);
  }
}
