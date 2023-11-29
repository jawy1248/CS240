package ui;

import com.google.gson.Gson;
import request.CreateGame_Req;
import request.Login_Req;
import request.Register_Req;
import response.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerFacade {
    public String serverURL;
    public String authToken;

    public ServerFacade(String url) {serverURL = url;}

    public Response clear() throws IOException{
        // Connect to server
        URL url = new URL(serverURL + "/db");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", authToken);

        // Get response code
        int code = connection.getResponseCode();
        if(code == 200)
            return new Success_Resp();
        return new Failure_Resp();

    }

    public RegisterLogin_Resp register(String username, String password, String email) throws IOException{
        // Connect to server
        URL url = new URL(serverURL + "/user");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Create request and response
        Register_Req req = new Register_Req(username, password, email);
        RegisterLogin_Resp resp = new RegisterLogin_Resp();
        String jsonReq = new Gson().toJson(req);

        try(OutputStream reqBody = connection.getOutputStream()){
            reqBody.write(jsonReq.getBytes());
        }
        int code = connection.getResponseCode();
        if(code == 200) {
            connection.connect();
            try(InputStream respBody = connection.getInputStream()){
                InputStreamReader reader = new InputStreamReader(respBody);
                resp = new Gson().fromJson(reader, RegisterLogin_Resp.class);
                authToken = resp.getAuthToken();
                return resp;
            }
        } else {
            resp.setCode(code);
            resp.setMessage(connection.getResponseMessage());
            return resp;
        }
    }

    public RegisterLogin_Resp login(String username, String password) throws IOException{
        // Connect to server
        URL url = new URL(serverURL + "/session");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Create request and response
        Login_Req req = new Login_Req(username, password);
        RegisterLogin_Resp resp = new RegisterLogin_Resp();
        String jsonReq = new Gson().toJson(req);

        try(OutputStream reqBody = connection.getOutputStream()){
            reqBody.write(jsonReq.getBytes());
        }
        int code = connection.getResponseCode();
        if(code == 200) {
            connection.connect();
            try(InputStream respBody = connection.getInputStream()){
                InputStreamReader reader = new InputStreamReader(respBody);
                resp = new Gson().fromJson(reader, RegisterLogin_Resp.class);
                authToken = resp.getAuthToken();
                return resp;
            }
        } else {
            resp.setCode(code);
            resp.setMessage(connection.getResponseMessage());
            return resp;
        }
    }

    public Response logout() throws IOException{
        // Connect to server
        URL url = new URL(serverURL + "/session");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", authToken);

        // Get response code and send response
        int code = connection.getResponseCode();
        if(code == 200)
            return new Success_Resp();
        return new Failure_Resp();
    }

    public CreateGame_Resp create(String name) throws IOException{
        // Connect to server
        URL url = new URL(serverURL + "/game");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", authToken);

        // Create request and response
        CreateGame_Resp resp = new CreateGame_Resp();
        CreateGame_Req req = new CreateGame_Req(name, authToken);
        String jsonReq = new Gson().toJson(req);

        try(OutputStream reqBody = connection.getOutputStream()){
            reqBody.write(jsonReq.getBytes());
        }

        connection.connect();

        int code = connection.getResponseCode();
        if(code == 200) {
            connection.connect();
            try(InputStream respBody = connection.getInputStream()){
                InputStreamReader reader = new InputStreamReader(respBody);
                resp = new Gson().fromJson(reader, CreateGame_Resp.class);
                return resp;
            }
        } else {
            resp.setCode(code);
            resp.setMessage(connection.getResponseMessage());
            return resp;
        }
    }

    public ListGames_Resp list() throws IOException{
        // Connect to server
        URL url = new URL(serverURL + "/game");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", authToken);

        connection.connect();

        try(InputStream respBody = connection.getInputStream()){
            InputStreamReader reader = new InputStreamReader(respBody);
            return new Gson().fromJson(reader, ListGames_Resp.class);
        }
    }

    public Response join(String tempGameID, String color) throws IOException{
        Integer gameID = Integer.parseInt(tempGameID);
        return null;
    }

    public Response watch(String tempGameID) throws IOException{
        Integer gameID = Integer.parseInt(tempGameID);
        return null;
    }

}
