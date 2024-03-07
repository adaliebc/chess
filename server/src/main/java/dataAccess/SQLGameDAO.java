package dataAccess;

import model.GameInfo;
import service.ResponseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class SQLGameDAO implements GameDAO{
    public SQLGameDAO() {
        try {
            createTable();
        }
        catch (ResponseException ignored){

        }
    }

    public boolean clear() {
        var sql = "TRUNCATE gameRecord";
        try {
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate(sql);
            if (i > 0) {
                System.out.println("CLEAR UNSUCCESSFUL");
            } else {
                System.out.println("CLEAR SUCCESSFUL");
            }
        }
        catch (SQLException | DataAccessException r) {
            return false;
        }
        return true;
    }


    public boolean createGame(GameInfo game) {
        return false;
    }


    public GameInfo getGame(int gameID) {
        return null;
    }


    public void addPlayer(GameInfo oldGame, GameInfo newGame) {

    }


    public Collection<GameInfo> getGameRecord() {
        return null;
    }

    private void createTable() throws ResponseException {
        try{
            String createStatements =  "CREATE TABLE IF NOT EXISTS  gameRecord (`gameID` int NOT NULL,`whiteUsername` varchar(256), 'blackUsername' varchar(100), 'gameName' varchar(100) NOT NULL)";
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate(createStatements);
            if (i > 0) {
                System.out.println("TABLE CREATED");
            } else {
                System.out.println("TABLE NOT CREATED");
            }
            clear();
        } catch (Exception e) {
            throw new ResponseException(500, "{ \"message\": \"Error: Unable to Create Table\" }");
        }
    }
}
