package microservices.book.gamification.services;

import microservices.book.gamification.domain.GameStats;

public interface GameService {
  GameStats newAttemptForUser(String userId, String attemptId, boolean correct);
  GameStats retrieveStatsForUser(String userId);
}
