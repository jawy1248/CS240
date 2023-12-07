package webSocketMessages.userCommands;

public class Resign extends UserGameCommand{
    public Resign(CommandType commandType, String authToken, String gameID, String username) {
        super(commandType, authToken, gameID, username);
    }
}