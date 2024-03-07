package dataAccess;

import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.ResponseException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUserDAO implements UserDAO{
    public SQLUserDAO() {
        try {
            createTable();
        }
        catch (ResponseException ignored){

        }
    }

    public boolean clear(){
        var sql = "TRUNCATE userRecord";
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

    @Override
    public UserData getUser(String username) {var statement = "SELECT username, password, email FROM userRecord WHERE username='" + username +"'";
        try{
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet user = stmt.executeQuery(statement);
            String gotUsername = null;
            String password = null;
            String email = null;
            while (user.next()) {
                gotUsername = user.getString(1);
                password = user.getString(2);
                email = user.getString(3);
            }
            user.close();
            return new UserData(gotUsername, password, email);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void createUser(UserData user) throws ResponseException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(user.password());
        try {
            var sql = "insert into userRecord (username, password, email)"
                    + " values('" + user.username() + "','" + hashedPassword + "','" + user.email() +"')";
            Connection conn = DatabaseManager.getConnection();
            Statement stmt = conn.createStatement();
            int i = stmt.executeUpdate(sql);
            if (i > 0) {
                System.out.println("ROW INSERTED");
            } else {
                System.out.println("ROW NOT INSERTED");
            }
        } catch (Exception e) {
            throw new ResponseException(403, "{ \"message\": \"Error: Unable to Create User\" }");
        }
    }

    private void createTable() throws ResponseException {
        try{
            String createStatements =  "CREATE TABLE IF NOT EXISTS  userRecord (username varchar(100) NOT NULL,password varchar(256) NOT NULL, email varchar(256) NOT NULL)";
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
