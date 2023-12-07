package ui;

import chess.*;
import chess.ChessGame;
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
    //Is this an override, came when added the extends Endpoint
    @Override
    public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {

    }

    public ChessGame deserializeBoard(String gameString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(gameString, ChessGame.class);

    }
    class ListAdapter implements JsonDeserializer<ChessGame> {
        @Override
        public ChessGame deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return jsonDeserializationContext.deserialize(jsonElement,ChessGameImplmentation.class);
        }
    }

    public static class GameAdapt extends TypeAdapter<ChessGameImplmentation> {

        @Override
        public void write(JsonWriter jsonWriter, ChessGameImplmentation gameImplmentation) throws IOException {

        }

        @Override
        public ChessGameImplmentation read(JsonReader jsonReader) throws IOException, IOException {
            ChessGameImplmentation model = new ChessGameImplmentation();
            ChessBoardImplmentation boardImplmentation = new ChessBoardImplmentation();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                String temp = jsonReader.nextName();
                if (temp.equals("game")) {
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        String temp2 = jsonReader.nextName();
                        switch (temp2) {
                            default:
                                jsonReader.skipValue(); // Skip unrecognized fields
                                break;
                            case "teamTurn":
                                JsonToken teamTurn = jsonReader.peek();
                                if (teamTurn == JsonToken.NULL) {
                                    jsonReader.nextNull();  // Consume the null value
                                    // Handle the case when whiteUsername is null, e.g., set a default value or do nothing
                                } else {
                                    model.setTeamTurn(ChessGame.TeamColor.valueOf(jsonReader.nextString()));
                                }
                                break;
                            case "whiteUsername":
                                JsonToken whiteUsernameToken = jsonReader.peek();
                                if (whiteUsernameToken == JsonToken.NULL) {
                                    jsonReader.nextNull();  // Consume the null value
                                    // Handle the case when whiteUsername is null, e.g., set a default value or do nothing
                                } else {
                                    model.setWhiteUsername(jsonReader.nextString());
                                }
                                break;
                            case "blackUsername":
                                JsonToken blackusernameToken = jsonReader.peek();
                                if (blackusernameToken == JsonToken.NULL) {
                                    jsonReader.nextNull();  // Consume the null value
                                    // Handle the case when whiteUsername is null, e.g., set a default value or do nothing
                                } else {
                                    model.setBlackUsername(jsonReader.nextString());
                                }
                                break;
                            case "turnCount":
                                JsonToken turnCount = jsonReader.peek();
                                if (turnCount == JsonToken.NULL) {
                                    jsonReader.nextNull();  // Consume the null value
                                    // Handle the case when whiteUsername is null, e.g., set a default value or do nothing
                                } else {
                                    model.setTurnCount(Integer.parseInt(jsonReader.nextString()));
                                }
                                break;
                            case "board":
                                jsonReader.beginObject();  // Start reading the outer object
                                jsonReader.nextName();  // Move to the inner "board" field
                                jsonReader.beginArray();                            //jsonReader.nextString();
                                int row = 0;
                                while (jsonReader.hasNext()) {
                                    jsonReader.beginArray();
                                    int col = 0;
                                    while (jsonReader.hasNext()) {
                                        ChessPositionImplmentation place = new ChessPositionImplmentation(col, row);
                                        JsonToken token = jsonReader.peek();
                                        if (token == JsonToken.NULL) {
                                            jsonReader.nextNull();  // Consume the null value
                                            boardImplmentation.addPiece(place, null);
                                        } else {
                                            jsonReader.beginObject();
                                            String poop = jsonReader.nextName();
                                            switch (poop) {
                                                case "pieceType":
                                                    String piece = jsonReader.nextString();
                                                    jsonReader.nextName();
                                                    String color = jsonReader.nextString();
                                                    ChessGame.TeamColor teamColor = (color.equals("WHITE")) ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
                                                    switch (piece) {
                                                        case "ROOK":
                                                            RookImplmentation Rookimplmentation = new RookImplmentation(teamColor);
                                                            boardImplmentation.addPiece(place, Rookimplmentation);
                                                            jsonReader.endObject();
                                                            break;
                                                        case "PAWN":
                                                            PawnImplmentation implmentation = new PawnImplmentation(teamColor);
                                                            boardImplmentation.addPiece(place, implmentation);
                                                            jsonReader.endObject();
                                                            break;
                                                        case "KNIGHT":
                                                            KnightImplmentation knightImplmentation = new KnightImplmentation(teamColor);
                                                            boardImplmentation.addPiece(place, knightImplmentation);
                                                            jsonReader.endObject();
                                                            break;
                                                        case "QUEEN":
                                                            QueenImplmentation queenImplmentation = new QueenImplmentation(teamColor);
                                                            boardImplmentation.addPiece(place, queenImplmentation);
                                                            jsonReader.endObject();
                                                            break;
                                                        case "KING":
                                                            KingImplmentation kingImplmentation = new KingImplmentation(teamColor);
                                                            boardImplmentation.addPiece(place, kingImplmentation);
                                                            jsonReader.endObject();
                                                            break;
                                                        case "BISHOP":
                                                            BishopImplmentation bishopImplmentation = new BishopImplmentation(teamColor);
                                                            boardImplmentation.addPiece(place, bishopImplmentation);
                                                            jsonReader.endObject();
                                                            break;
                                                    }
                                            }
                                        }

                                        col++;

                                    }

                                    jsonReader.endArray();
                                    row++;
                                }

                                jsonReader.endArray();
                                jsonReader.endObject();
                                model.setGameBoard(boardImplmentation);
                        }
                    }
                    jsonReader.endObject();

                }
                else if (temp.equals("serverMessageType")) {
                    // Skip the value of "serverMessageType"
                    jsonReader.skipValue();}
            }

            jsonReader.endObject();

            return model;
        }

    }

    private ListGames_Resp deserializeGame(String gameString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
        gsonBuilder.registerTypeAdapter(ChessGame.class, new ChessGameAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(gameString, ListGames_Resp.class);
    }

    public static class ChessGameAdapter implements JsonDeserializer<ChessGame> {
        public ChessGame deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ChessBoard.class, new ServerFacade.ChessBoardAdapter());
            Gson gson = gsonBuilder.create();
            return gson.fromJson(el, Game.class);
        }
    }

    public static class ChessBoardAdapter implements JsonDeserializer<ChessBoard> {
        public ChessBoard deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ChessPiece.class, new ServerFacade.ChessPieceAdapter());
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
