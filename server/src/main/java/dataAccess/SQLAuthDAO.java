package dataAccess;
import model.AuthData;
import service.ResponseException;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO implements AuthDAO {

    public boolean clear(){
        var sql = "TRUNCATE authRecord";
        try {
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate(sql);
            if (i > 0) {
                System.out.println("ROW INSERTED");
            } else {
                System.out.println("ROW NOT INSERTED");
            }
        }
        catch (SQLException | DataAccessException r) {
            return false;
        }
        return true;
    }

    public void addToken (AuthData token) {
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
            System.out.println(e);
        }

    }

    @Override
    public AuthData getAuth(String token) {
        return null;
    }

    @Override
    public void deleteToken(AuthData token) {
    try{
        var statement = "DELETE FROM authRecord WHERE authToken=" + token;
        Connection conn = DatabaseManager.getConnection();
        Statement stmt = conn.createStatement();
        int i = stmt.executeUpdate(statement);
        if (i > 0) {
            System.out.println("ROW DELETED");
        } else {
            System.out.println("ROW NOT DELETED");
        }
    } catch (Exception e) {
        System.out.println(e);
    }
    }

    @Override
    public boolean verifyAuth(String token) {
        return false;
    }

}
