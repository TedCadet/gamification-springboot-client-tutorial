package microservices.book.gamification.controllers;

import lombok.AllArgsConstructor;
import microservices.book.gamification.domain.GameStats;
import microservices.book.gamification.services.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
@AllArgsConstructor
public class UserStatsController {

  private final GameService gameService;

  @GetMapping
  public GameStats getStatsForUser(@RequestParam("userId") final String userId) {
    return gameService.retrieveStatsForUser(userId);
  }
}
