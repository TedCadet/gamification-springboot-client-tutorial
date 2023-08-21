package microservices.book.gamification.controllers;

import java.util.List;
import lombok.AllArgsConstructor;
import microservices.book.gamification.domain.LeaderBoardRow;
import microservices.book.gamification.services.LeaderBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leaders")
@AllArgsConstructor
public class LeaderBoardController {

  private final LeaderBoardService leaderBoardService;

  @GetMapping
  public List<LeaderBoardRow> getLeaderBoard() {
    return leaderBoardService.getCurrentLeaderBoard();
  }

}
