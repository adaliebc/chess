package dataAccess;

import model.UserData;
import service.ResponseException;

import java.sql.Connection;
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
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public boolean createUser(UserData user) {
        return false;
    }

    private void createTable() throws ResponseException {
        try{
            String createStatements =  "CREATE TABLE IF NOT EXISTS  userRecord (`username` varchar(100) NOT NULL,`password` varchar(256) NOT NULL, `email` varchar(256) NOT NULL)";
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
