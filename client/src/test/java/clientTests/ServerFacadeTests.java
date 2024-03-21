package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import ui.PreloginUI;
import ui.ServerFacade;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerUserPositiveTest() {
        String[] list = new String[] {"register", "username", "password", "email"};
        Assertions.assertTrue(facade.register(list));
    }

    @Test
    public void registerUserNegativeTest() {
        String[] list = new String[] {"register", "username", "password"};
        Assertions.assertFalse(facade.register(list));
    }

    @Test
    public void loginUserPositiveTest() {
        String[] list = new String[] {"login", "username", "password"};
        Assertions.assertTrue(facade.login(list));
    }

    @Test
    public void loginUserNegativeTest() {
        String[] list = new String[] {"login", "username"};
        Assertions.assertFalse(facade.login(list));
    }

    @Test
    public void createGamePositiveTest() {
        String[] list = new String[] {"login", "username", "password"};
        Assertions.assertTrue(facade.login(list));
        list = new String[] {"create", "name"};
        Assertions.assertTrue(facade.createGame(list) != 0);
    }

    @Test
    public void createGameNegativeTest() {
        String[] list = new String[] {"create", "username", "password"};
        Assertions.assertEquals(0, facade.createGame(list));
    }

    @Test
    public void joinGamePositiveTest() {
        String[] list = new String[] {"login", "username", "password"};
        Assertions.assertTrue(facade.login(list));
        list = new String[] {"create", "name"};
        int gameIDint = facade.createGame(list);
        String gameID = Integer.toString(gameIDint);
        list = new String[] {"observe", gameID};
        Assertions.assertNotNull(facade.joinGame(list));
    }

    @Test
    public void joinGameNegativeTest() {
        String[] list = new String[] {"login", "username", "password"};
        Assertions.assertTrue(facade.login(list));
        list = new String[] {"create", "name"};
        int gameIDint = facade.createGame(list);
        String gameID = Integer.toString(gameIDint);
        list = new String[] {"observe", gameID, "pink"};
        Assertions.assertNull(facade.joinGame(list));
    }

}