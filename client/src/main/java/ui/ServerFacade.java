package ui;

import java.io.IOException;
import java.net.URISyntaxException;

public class ServerFacade {
    private static String serverUrl = null;
    public ServerFacade (int port){
        serverUrl = "http://localhost:" + port;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        PreloginUI p = new PreloginUI(serverUrl);
        p.start();
    }
}
