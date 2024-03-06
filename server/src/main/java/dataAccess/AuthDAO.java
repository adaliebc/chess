package dataAccess;

import model.AuthData;
import service.ResponseException;

public interface AuthDAO {
    public void clear() throws ResponseException;
    public void addToken(AuthData token);
    public AuthData getAuth(String token);
    public void deleteToken(AuthData token);
    public boolean verifyAuth(String token);
}
