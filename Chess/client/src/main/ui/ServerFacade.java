package ui;

import response.CreateGame_Resp;
import response.ListGames_Resp;
import response.RegisterLogin_Resp;
import response.Response;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ServerFacade {
    public String serverURL;
    public String authToken;

    public ServerFacade(String url) {serverURL = url;}

    public RegisterLogin_Resp register(String username, String password, String email){
        return null;
    }

    public RegisterLogin_Resp login(String username, String password){
        return null;
    }

    public Response logout(){
        return null;
    }

    public CreateGame_Resp create(String name){
        return null;
    }

    public ListGames_Resp list(){
        return null;
    }

    public Response join(String tempGameID, String color){
        Integer gameID = Integer.parseInt(tempGameID);
        return null;
    }

    public Response watch(String tempGameID){
        Integer gameID = Integer.parseInt(tempGameID);
        return null;
    }

}
