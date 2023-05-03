package microservices.book.gamification.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.domain.Badge;
import microservices.book.gamification.domain.BadgeCard;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.domain.ScoreCard;
import microservices.book.gamification.repositories.BadgeCardRepository;
import microservices.book.gamification.repositories.ScoreCardRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameServiceImpl implements GameService {
  private ScoreCardRepository scoreCardRepository;
  private BadgeCardRepository badgeCardRepository;

  public GameServiceImpl(ScoreCardRepository scoreCardRepository, BadgeCardRepository badgeCardRepository) {
    this.scoreCardRepository = scoreCardRepository;
    this.badgeCardRepository = badgeCardRepository;
  }
  @Override
  public GameStats newAttemptForUser(String userId, String attemptId, boolean correct) {
    if(correct) {
      ScoreCard scoreCard = new ScoreCard(userId, attemptId);
      scoreCardRepository.save(scoreCard);

      log.info("User with {} scored {} points for attemptId {}", userId, scoreCard.score(), attemptId);
      List<BadgeCard> badgeCards = processForBadges(userId);
      List<Badge> badges = badgeCards.stream()
          .map(BadgeCard::badge)
          .toList();

      return new GameStats(userId, scoreCard.score(), badges);
    }

    return  GameStats.emptyStats(userId);
  }

  @Override
  public GameStats retrieveStatsForUser(String userId) {
    int score = scoreCardRepository.getTotalScoreForUser(userId);
    List<BadgeCard> badgeCards = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);

    List<Badge> badges = badgeCards.stream().map(BadgeCard::badge).toList();

    return new GameStats(userId, score, badges);
  }

  private List<BadgeCard> processForBadges(String userId) {
    List<BadgeCard> badgeCards = Collections.emptyList();

    int totalScore = scoreCardRepository.getTotalScoreForUser(userId);

    log.info("New score for user {} is {}", userId, totalScore);

    List<ScoreCard> scoreCardList = scoreCardRepository.findByUserIdOrderByScoreTimestampDesc(userId);
    List<BadgeCard> badgeCardList = badgeCardRepository.findByUserIdOrderByBadgeTimestampDesc(userId);
    
    // Badges depending on score
    checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.BRONZE_MULTIPLICATOR, totalScore,100, userId)
        .ifPresent(badgeCards::add);
    checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore,500, userId)
        .ifPresent(badgeCards::add);
    checkAndGiveBadgeBasedOnScore(badgeCardList, Badge.SILVER_MULTIPLICATOR, totalScore,999, userId)
        .ifPresent(badgeCards::add);

    // First won badge
    if(scoreCardList.size() == 1 && !containsBadge(badgeCardList, Badge.FIRST_WON)) {
      BadgeCard firstWonBadge = giveBadgeToUser(Badge.FIRST_WON, userId);
      badgeCards.add(firstWonBadge);
    }

    return badgeCards;
  }

  private Optional<BadgeCard> checkAndGiveBadgeBasedOnScore(List<BadgeCard> badgeCardList,
      Badge badge, int totalScore, int scoreThreshold, String userId) {
    if(totalScore >= scoreThreshold && !containsBadge(badgeCardList, badge)) {
      return Optional.of(giveBadgeToUser(badge, userId));
    }
    return Optional.empty();
  }

  private boolean containsBadge(List<BadgeCard> badgeCardList, Badge badge) {
    return badgeCardList.stream().anyMatch(b -> b.badge().equals(badge));
  }

  private BadgeCard giveBadgeToUser(Badge badge, String userId) {
    BadgeCard badgeCard = new BadgeCard(userId, badge);

    log.info("User with id {} won a new badge: {}", userId, badge);
    return badgeCard;
  }
}
