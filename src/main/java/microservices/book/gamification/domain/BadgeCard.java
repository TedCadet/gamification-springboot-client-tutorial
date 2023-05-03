package microservices.book.gamification.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record BadgeCard(String badgeId, String userId, Long badgeTimestamp, Badge badge) {

  public BadgeCard() {
    this(null, null, 0L, null);
  }
  public BadgeCard(String userId, Badge badge) {
    this(null, userId, System.currentTimeMillis(), badge);
  }
}
