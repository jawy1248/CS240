import spark.Spark;
import Handlers.*;

public class Server {
    public static void main(String[] args) {
        try {
            int port = Integer.parseInt(args[0]);
            // Set port and web file location
            Spark.port(port);
            Spark.externalStaticFileLocation("web/");

            // each of the methods
            Spark.delete("/db", (request, response) -> ClearApplicationHand.handle(request, response));
            Spark.post("/user", (request, response) -> RegisterHand.handle(request, response));
//            Spark.post("/session", (request, response) -> LoginHand.handle(request, response));
//            Spark.delete("/session", (request, response) -> LogoutHand.handle(request, response));
//            Spark.get("/game", (request, response) -> ListGamesHand.handle(request, response));
//            Spark.post("/game", (request, response) -> CreateGameHand.handle(request, response));
//            Spark.put("/game", (request, response) -> JoinGameHand.handle(request, response));

            Spark.awaitInitialization();
            System.out.println("Listening on port " + port);
        } catch(ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.err.println("Specify the port number as a command line parameter");
        }
    }
}
