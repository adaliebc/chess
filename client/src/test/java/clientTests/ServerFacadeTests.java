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
    @Order(1)
    public void registerUserPositiveTest() {
        String[] list = new String[] {"register", "username", "password", "email"};
        Assertions.assertTrue(facade.register(list));
    }

    @Test
    @Order(2)
    public void registerUserNegativeTest() {
        String[] list = new String[] {"register", "username", "password"};
        Assertions.assertFalse(facade.register(list));
    }

    @Test
    @Order(3)
    public void loginUserPositiveTest() {
        String[] list = new String[] {"login", "username", "password"};
        Assertions.assertTrue(facade.login(list));
    }

    @Test
    @Order(4)
    public void loginUserNegativeTest() {
        String[] list = new String[] {"login", "username"};
        Assertions.assertFalse(facade.login(list));
    }

    @Test
    @Order(5)
    public void createGamePositiveTest() {
        String[] list = new String[] {"login", "username", "password"};
        facade.login(list);
        list = new String[] {"create", "name"};
        Assertions.assertTrue(facade.createGame(list) != 0);
    }

    @Test
    @Order(6)
    public void createGameNegativeTest() {
        String[] list = new String[] {"create", "username", "password"};
        Assertions.assertEquals(0, facade.createGame(list));
    }

    @Test
    @Order(7)
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
    @Order(8)
    public void joinGameNegativeTest() {
        String[] list = new String[] {"login", "username", "password"};
        Assertions.assertTrue(facade.login(list));
        list = new String[] {"create", "name"};
        int gameIDint = facade.createGame(list);
        String gameID = Integer.toString(gameIDint);
        list = new String[] {"observe", gameID, "pink"};
        Assertions.assertNull(facade.joinGame(list));
    }

    @Test
    @Order(9)
    public void listGamePositiveTest() {
        String[] list = new String[] {"login", "username", "password"};
        facade.login(list);
        list = new String[] {"create", "name"};
        facade.createGame(list);
        Assertions.assertTrue(facade.listGames());
    }

    @Test
    @Order(10)
    public void listGameNegativeTest() {
        String[] list = new String[] {"login", "username", "password"};
        Assertions.assertTrue(facade.login(list));
        list = new String[] {"create", "name"};
        int gameIDint = facade.createGame(list);
        String gameID = Integer.toString(gameIDint);
        list = new String[] {"observe", gameID};
        facade.joinGame(list);
        facade.logout();
        Assertions.assertFalse(facade.listGames());
    }

}