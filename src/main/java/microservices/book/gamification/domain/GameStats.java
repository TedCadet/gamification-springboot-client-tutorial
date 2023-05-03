package microservices.book.gamification.domain;

import java.util.Collections;
import java.util.List;

public record GameStats(String userId, int score, List<Badge> badges) {
  public static GameStats emptyStats(String userId) {
    return new GameStats(userId,0, Collections.emptyList());
  }
}
