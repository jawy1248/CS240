package ui;

import chess.*;
import chess.ChessBoard;
import chess.Board;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import response.ListGames_Resp;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Scanner;

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
                        case LOAD_GAME -> notificationHandler.updateBoard(deserializeGame(deserializeMessage(message)));
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

    private String deserializeMessage(String message) {
        Scanner scanner = new Scanner(message);
        String gameString = null;
        while(scanner.hasNext()){
            String temp = scanner.nextLine();
            gameString = temp.substring(8, temp.length()-33);
        }
        return gameString;
    }

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
