package microservices.book.gamification.services;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import microservices.book.gamification.domain.LeaderBoardRow;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LeaderBoardServiceImpl implements LeaderBoardService {

  //TODO: implement it
  @Override
  public List<LeaderBoardRow> getCurrentLeaderBoard() {
    return Collections.emptyList();
  }
}
