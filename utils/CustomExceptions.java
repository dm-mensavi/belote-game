package utils;

/**
 * Custom exceptions for error handling in the pub management and belote system.
 */
public class CustomExceptions {

    /**
     * Exception thrown when there are not enough players for a game.
     */
    public static class NotEnoughPlayersException extends Exception {
        public NotEnoughPlayersException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when a player attempts an illegal action in the game.
     */
    public static class IllegalGameActionException extends Exception {
        public IllegalGameActionException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when an invalid drink order is made.
     */
    public static class InvalidDrinkOrderException extends Exception {
        public InvalidDrinkOrderException(String message) {
            super(message);
        }
    }

    /**
     * Exception thrown when a team attempts to register without the required number of players.
     */
    public static class InvalidTeamException extends Exception {
        public InvalidTeamException(String message) {
            super(message);
        }
    }
}
