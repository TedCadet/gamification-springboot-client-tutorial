package microservices.book.gamification.repositories;

import java.util.List;
import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.domain.ScoreCard;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScoreCardRepository extends MongoRepository<ScoreCard, String> {
  @Aggregation(pipeline = {
      """
      { $group:
        {
          _id: { userId: ?0 },
          totalScore: {$sum: $score}
        }
      }
      """
  })
  int getTotalScoreForUser(String userId);

  @Aggregation(pipeline = {
      """
      { $group:
        {
          _id: $userId,
          totalScore: {$sum: $score}
        }
      }
      """,
      "{ $sort: { $userId: -1 } }",
      "{ $limit: 10 }"
  })
  List<LeaderBoardRow> findFirst10();

  List<ScoreCard> findByUserIdOrderByScoreTimestampDesc(final String userId);
}
