package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{
    public JoinObserver(CommandType commandType, String authToken, String gameID, String username) {
        super(commandType, authToken, gameID, username);
    }
}