import response.*;
import ui.ServerFacade;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServerTests {
    ServerFacade server;
    String serverURL;

    final String username = "jawy1248";
    final String password = "guessAgain";
    final String email = "whyME@abc.edu";
    final String gameName = "gaymez";

    @BeforeEach
    public void start() throws IOException{
        server = new ServerFacade("http://localhost:8080");
        serverURL = server.serverURL;
        server.clear();
    }

    @Test
    @DisplayName("Clear +")
    public void clearPos() throws IOException{
        Response resp = server.clear();
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 200, "Did not clear");
    }

    @Test
    @DisplayName("Register +")
    public void regPos() throws IOException{
        // Make registration, check it worked
        Response resp = server.register(username, password, email);
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 200, "Did not register");
    }

    @Test
    @DisplayName("Register -")
    public void regNeg() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Make identical registration
        Response resp = server.register(username, password, email);
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 403, "Incorrectly allowed new registration");
    }

    @Test
    @DisplayName("Login +")
    public void loginPos() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Logout
        server.logout();
        // Try to login
        Response resp = server.login(username, password);
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 200, "Could not login");
    }

    @Test
    @DisplayName("Login -")
    public void loginNeg() throws IOException{
        // Try to login with no registration
        Response resp = server.login(username, password);
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 401, "Incorrectly allowed login");

    }

    @Test
    @DisplayName("Logout +")
    public void logoutPos() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Logout
        Response resp = server.logout();
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 200, "Could not logout");
    }

    @Test
    @DisplayName("Logout -")
    public void logoutNeg() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Logout
        server.logout();
        // Logout again, with no user
        Response resp = server.logout();
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 500, "Incorrectly allowed logout");
    }

    @Test
    @DisplayName("Create Game +")
    public void createPos() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Create game
        Response resp = server.create(gameName);
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 200, "Could not create game");
    }

    @Test
    @DisplayName("Create Game -")
    public void createNeg() throws IOException{
        // Create game without logging in
        Response resp = server.create(gameName);
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 401, "Incorrectly allowed game creation");
    }

    @Test
    @DisplayName("List Games +")
    public void listPos() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Create games
        server.create(gameName);
        server.create(gameName);
        server.create(gameName);
        // List game
        Response resp = server.list();
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 200, "Could not list games");

    }

    @Test
    @DisplayName("List Games -")
    public void listNeg() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Create games
        server.create(gameName);
        server.create(gameName);
        server.create(gameName);
        // Logout
        server.logout();
        // List game
        Assertions.assertThrows(IOException.class,() -> server.list(),"Did not throw an error");
    }

    @Test
    @DisplayName("Join Game +")
    public void joinPos() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Create games
        server.create(gameName);
        server.create(gameName);
        CreateGame_Resp respGAME = server.create(gameName);
        // Try to join game
        Response resp = server.join(respGAME.getGameID().toString(), "WHITE");
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 200, "Could not join game");
    }

    @Test
    @DisplayName("Join Game -")
    public void joinNeg() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Create games
        server.create(gameName);
        server.create(gameName);
        CreateGame_Resp respGAME = server.create(gameName);
        // Logout
        server.logout();
        // Try to join game
        Response resp = server.join(respGAME.getGameID().toString(), "WHITE");
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 500, "Incorrectly allowed join game");
    }

    @Test
    @DisplayName("Watch Game +")
    public void watchPos() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Create games
        server.create(gameName);
        server.create(gameName);
        CreateGame_Resp respGAME = server.create(gameName);
        // Try to join game
        Response resp = server.watch(respGAME.getGameID().toString());
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 200, "Could not watch game");
    }

    @Test
    @DisplayName("Watch Game -")
    public void watchNeg() throws IOException{
        // Make first registration
        server.register(username, password, email);
        // Create games
        server.create(gameName);
        server.create(gameName);
        CreateGame_Resp respGAME = server.create(gameName);
        // Logout
        server.logout();
        // Try to join game
        Response resp = server.watch(respGAME.getGameID().toString());
        Assertions.assertNotNull(resp, "Response was null");
        Assertions.assertEquals(resp.getCode(), 500, "Incorrectly allowed watch game");
    }
}
