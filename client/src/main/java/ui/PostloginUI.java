package ui;

public class PostloginUI {
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
