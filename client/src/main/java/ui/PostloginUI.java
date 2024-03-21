package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import model.*;

import spark.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class PostloginUI {
    public PostloginUI () {
    }
    public String getHelp() {
        return """
                'create' <name> = create a chess game
                'join' <game ID> <white|black> = join a chess game
                'observe' <gameID> = observe a chess game
                'list' = list all chess games
                'help' = show options
                """;
    }
}
