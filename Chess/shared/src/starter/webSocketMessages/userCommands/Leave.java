package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{
    public Leave(CommandType commandType, String authToken, String gameID, String username) {
        super(commandType, authToken, gameID, username);
    }
}