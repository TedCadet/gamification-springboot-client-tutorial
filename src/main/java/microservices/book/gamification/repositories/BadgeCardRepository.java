package microservices.book.gamification.repositories;

import java.util.List;
import microservices.book.gamification.domain.BadgeCard;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BadgeCardRepository extends MongoRepository<BadgeCard, String> {
  List<BadgeCard> findByUserIdOrderByBadgeTimestampDesc(final String userId);
}
