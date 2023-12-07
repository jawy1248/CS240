package ws;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public String username;
    public String authToken;
    public String gameId;
    public Session session;
    public Connection(String username, Session session){
        this.session = session;
        this.username = username;
    }
    public void send(String message) throws IOException {
        session.getRemote().sendString(message);
    }
    public void sendError(RemoteEndpoint endpoint, String error) throws IOException {
        endpoint.sendString(error);
    }
}