package microservices.book.gamification.events;

public record MultiplicationSolvedEvent(String multiplicationResultAttemptId,
                                        String userId,
                                        boolean correct) {

}
