package dataAccess;
import model.AuthData;
import service.ResponseException;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import java.sql.ResultSet;

public class SQLAuthDAO implements AuthDAO {
    public SQLAuthDAO() {
        try {
            createTable();
        }
        catch (ResponseException ignored){

        }
    }

    public boolean clear(){
        var statement = "TRUNCATE authRecord";
        try {
            Connection connection = DatabaseManager.getConnection();
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate(statement);
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

    public void addToken (AuthData token) throws ResponseException {
        try {
            var sql = "insert into authRecord (authToken, username)"
                    + " values('" + token.authToken() + "','" + token.username()  + "')";
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate(sql);
            if (i > 0) {
                System.out.println("ROW INSERTED");
            } else {
                System.out.println("ROW NOT INSERTED");
            }
        } catch (Exception e) {
            throw new ResponseException(403, "{ \"message\": \"Error: Unable to Add User\" }");
        }

    }

    public AuthData getAuth(String token) {
            var statement = "SELECT authToken, username FROM authRecord WHERE authToken='" + token +"'";
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
                return new AuthData(authToken, username);
        } catch (Exception e) {
                return null;
        }
    }

    public void deleteToken(AuthData token) throws ResponseException {
    try{
        var statement = "DELETE FROM authRecord WHERE authToken='" + token.authToken() + "'";
        Connection conn = DatabaseManager.getConnection();
        Statement stmt = conn.createStatement();
        int i = stmt.executeUpdate(statement);
        if (i > 0) {
            System.out.println("ROW DELETED");
        } else {
            System.out.println("ROW NOT DELETED");
        }
    } catch (Exception e) {
        throw new ResponseException(403, "{ \"message\": \"Error: Unable to Delete User\" }");
    }
    }

    public boolean verifyAuth(String token) {
        AuthData user = getAuth(token);
        return (user.authToken() != null || user.username() != null);
    }

    private void createTable() throws ResponseException {
        try{
            DatabaseManager.createDatabase();
            String createStatements =  "CREATE TABLE IF NOT EXISTS  authRecord (authToken varchar(100) NOT NULL,username varchar(256) NOT NULL)";
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
