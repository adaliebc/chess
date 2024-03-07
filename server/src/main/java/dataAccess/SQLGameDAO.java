package dataAccess;

import model.GameInfo;
import service.ResponseException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        var sql = "insert into gameRecord (gameID, gameName)"
                + " values('" + game.gameID() + "','" + game.gameName() +"')";
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
            int gotGameID = 0;
            String whiteUsername = null;
            String blackUsername = null;
            String gameName = null;
            while (user.next()) {
                gotGameID = user.getInt(1);
                whiteUsername = user.getString(2);
                blackUsername = user.getString(3);
                gameName = user.getString(4);
            }
            user.close();
            return new GameInfo(gotGameID, whiteUsername, blackUsername, gameName);
        } catch (Exception e) {
            return null;
        }
    }


    public void addPlayer(int gameID, String playerColor, String username) throws ResponseException {
        try{
        var sql = "update gameRecord set " + playerColor + "=" + "'" + username + "'" + "where gameID = " + gameID;
        Connection conn = DatabaseManager.getConnection();
        Statement stmt = conn.createStatement();
        int i = stmt.executeUpdate(sql);
        if (i > 0) {
            System.out.println("ROW UPDATED");
        } else {
            System.out.println("ROW NOT UPDATED");
        }
    } catch (Exception e) {
            throw new ResponseException(403, "{ \"message\": \"Error: Unable to Add User\" }");
    }


}


    public Collection<GameInfo> getGameRecord() {
        int gotGameID;
        String whiteUsername;
        String blackUsername;
        String gameName;
        var result = new ArrayList<GameInfo>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM gameRecord";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        gotGameID = rs.getInt(1);
                        whiteUsername = rs.getString(2);
                        blackUsername = rs.getString(3);
                        gameName = rs.getString(4);
                       GameInfo gameInfo = new GameInfo(gotGameID, whiteUsername, blackUsername, gameName);
                        result.add(gameInfo);
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    private void createTable() throws ResponseException {
        try{
            DatabaseManager.createDatabase();
            String createStatements =  "CREATE TABLE IF NOT EXISTS  gameRecord (gameID int NOT NULL,whiteUsername varchar(256), blackUsername varchar(100), gameName varchar(100) NOT NULL)";
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate(createStatements);
            if (i > 0) {
                System.out.println("TABLE CREATED");
            } else {
                System.out.println("TABLE NOT CREATED");
            }
        } catch (Exception e) {
            throw new ResponseException(500, "{ \"message\": \"Error: Unable to Create Table\" }");
        }
    }
}
