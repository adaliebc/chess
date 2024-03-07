package dataAccess;

import model.AuthData;
import model.GameInfo;
import service.ResponseException;

import java.sql.Connection;
import java.sql.ResultSet;
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


    public boolean createGame(GameInfo game) {try {
        var sql = "insert into gameRecord (gameID, whiteUsername, blackUsername, gameName)"
                + " values('" + game.gameName() + "','" + game.whiteUsername() + "','" + game.blackUsername() + "','" + game.gameName() +"')";
        Connection conn = DatabaseManager.getConnection();
        Statement stmt = conn.createStatement();
        int i = stmt.executeUpdate(sql);
        if (i > 0) {
            System.out.println("ROW INSERTED");
        } else {
            System.out.println("ROW NOT INSERTED");
        }
    } catch (Exception e) {
        return false;
    }
    return true;

    }


    public GameInfo getGame(int gameID) {
        var statement = "SELECT * FROM gameRecord WHERE gameID='" + gameID +"'";
        try{
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet user = stmt.executeQuery(statement);
            String authToken = null;
            String username = null;
            while (user.next()) {
                authToken = user.getString(1);
                username = user.getString(2);
            }
            user.close();
            return new GameInfo(3, username);
        } catch (Exception e) {
            return null;
        }
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
