import spark.Spark;
import handlers.*;

public class Server {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            // Set port and web file location
            Spark.port(port);
            Spark.externalStaticFileLocation("web/");

            // Each of the contexts
            Spark.delete("/db", ClearApplicationHand::handle);
            Spark.post("/user", RegisterHand::handle);
            Spark.post("/session", LoginHand::handle);
            Spark.delete("/session", LogoutHand::handle);
            Spark.get("/game", ListGamesHand::handle);
            Spark.post("/game", CreateGameHand::handle);
            Spark.put("/game", JoinGameHand::handle);

            Spark.awaitInitialization();
            System.out.println("Listening on port " + port);
        } catch(ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.err.println("Specify the port number as a command line parameter");
        }
    }
}
