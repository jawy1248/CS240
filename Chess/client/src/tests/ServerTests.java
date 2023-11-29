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

    @BeforeEach
    public void start() throws IOException{
        server = new ServerFacade("http://localhost:8080");
        serverURL = server.serverURL;
        server.clear();
    }

    @Test
    @DisplayName("Clear +")
    public void clearPos() throws IOException{

    }

    @Test
    @DisplayName("Register +")
    public void regPos() throws IOException{

    }

    @Test
    @DisplayName("Register -")
    public void regNeg() throws IOException{

    }

    @Test
    @DisplayName("Login +")
    public void loginPos() throws IOException{

    }

    @Test
    @DisplayName("Login -")
    public void loginNeg() throws IOException{

    }

    @Test
    @DisplayName("Logout +")
    public void logoutPos() throws IOException{

    }

    @Test
    @DisplayName("Logout -")
    public void logoutNeg() throws IOException{

    }

    @Test
    @DisplayName("Create Game +")
    public void createPos() throws IOException{

    }

    @Test
    @DisplayName("Create Game -")
    public void createNeg() throws IOException{

    }

    @Test
    @DisplayName("List Games +")
    public void listPos() throws IOException{

    }

    @Test
    @DisplayName("List Games -")
    public void listNeg() throws IOException{

    }

    @Test
    @DisplayName("Join Game +")
    public void joinPos() throws IOException{

    }

    @Test
    @DisplayName("Join Game -")
    public void joinNeg() throws IOException{

    }

    @Test
    @DisplayName("Watch Game +")
    public void watchPos() throws IOException{

    }

    @Test
    @DisplayName("Watch Game -")
    public void watchNeg() throws IOException{

    }
}
