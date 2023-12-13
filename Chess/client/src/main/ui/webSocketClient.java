package ui;

import chess.*;
import chess.ChessBoard;
import chess.Board;

import com.google.gson.*;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.lang.reflect.Type;
import java.net.URI;

public class webSocketClient extends Endpoint {
    public Session session;
    public NotificationHandler notificationHandler;

    public webSocketClient(String url, NotificationHandler handler) throws Exception {
        this.notificationHandler = handler;
        URI uri = new URI(url);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this , uri);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                try{
                    ServerMessage serverTalk = new Gson().fromJson(message, ServerMessage.class);

                    switch (serverTalk.getServerMessageType()) {
                        case LOAD_GAME -> notificationHandler.updateBoard(deserializeGame(message.substring(8, message.length()-33)));
                        case NOTIFICATION -> notificationHandler.message(new Gson().fromJson(message, NotificationMessage.class).message);
                        case ERROR -> notificationHandler.error(new Gson().fromJson(message, ErrorMessage.class).toString());
                    }
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void send(UserGameCommand msg) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(msg));
    }

    @Override
    public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {}

    public Game deserializeGame(String gameString){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(gameString, Game.class);
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
