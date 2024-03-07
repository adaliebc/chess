package dataAccess;

import model.AuthData;
import service.ResponseException;

public interface AuthDAO {
    public boolean clear() throws ResponseException;
    public void addToken(AuthData token) throws ResponseException;
    public AuthData getAuth(String token);
    public void deleteToken(AuthData token) throws ResponseException;
    public boolean verifyAuth(String token);
}
