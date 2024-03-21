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
    public void registerUserPositiveTest() throws IOException, URISyntaxException {
        String userInput = String.format("register %susername %spassword%s email%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        String expected = "Successfully logged in as username";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        PreloginUI.start(null); // call the main method

        String[] lines = baos.toString().split(System.lineSeparator());
        String actual = lines[lines.length-1];

        // checkout output
        Assertions.assertEquals(expected,actual);
    }

}