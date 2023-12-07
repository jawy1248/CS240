package ui;

import chess.*;
import chess.ChessGame;
import com.google.gson.*;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.lang.reflect.Type;
import java.net.URI;


public class webSocketClient extends Endpoint {
    public interface WebSocketClientObserver{
        void updateBoard(String message);
        void message();
    }

    Session session;
    NotificationHandler notificationHandler;

    public webSocketClient(String url, NotificationHandler handler) throws Exception {
        //connect to the server and then sends cmd line args to socket on the server side
        this.notificationHandler = handler;
        System.out.println("IN WS");
        URI uri = new URI(url);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this , uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                try{
                    ServerMessage serverTalk = new Gson().fromJson(message, ServerMessage.class);
                    switch (serverTalk.getServerMessageType()) {
                        case LOAD_GAME:
                            notificationHandler.updateBoard(deserializeBoard(message));

                        case NOTIFICATION:
                            notificationHandler.message(new Gson().fromJson(message, NotificationMessage.class).message);

                        case ERROR:
                            notificationHandler.error(new Gson().fromJson(message, ErrorMessage.class).toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }
    public void send(UserGameCommand msg) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(msg));
    }

    @Override
    public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {

    }

    public ChessGame deserializeBoard(String gameString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(gameString, ChessGame.class);
    }

    public static class ChessBoardAdapter implements JsonDeserializer<ChessBoard> {
        public ChessBoard deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
            Gson gson = gsonBuilder.create();
            return gson.fromJson(el, Board.class);
        }
    }

    public static class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {
        public ChessPiece deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            return new Gson().fromJson(el, Piece.class);
        }
    }
}
