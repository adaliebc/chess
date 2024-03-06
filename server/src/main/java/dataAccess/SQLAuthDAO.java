package dataAccess;
import model.AuthData;
import model.UserData;
import service.ResponseException;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLAuthDAO implements AuthDAO {
    public void clear() throws ResponseException {
        var statement = "TRUNCATE authRecord";
        executeUpdate(statement);
    }

    public void adduser(User s) {
        try {
            sql = "insert into users ('u_fname', 'u_lname', 'u_uname', 'u_pass', 'u_age', 'u_adderess')"
                    + "values('" + s.getFirstname() + "','" + s.getLastname()
                    + "','" + s.getUsername() + "','" + s.getPassword() + "','" + s.getAge() + "','" + s.getAdderss() + "')";
            stmt = conn.createStatement();
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

    private int executeUpdate(String statement, Object... params) throws ResponseException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (var i = 0; i < params.length; i++) {
                    var param = params[i];
                    if (param instanceof String p) ps.setString(i + 1, p);
                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
                    else if (param instanceof PetType p) ps.setString(i + 1, p.toString());
                    else if (param == null) ps.setNull(i + 1, NULL);
                }
                ps.executeUpdate();

                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
        }
    }
}
