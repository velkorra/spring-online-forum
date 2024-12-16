package org.example.onlineforum.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
      super("username \"" + username + "\" is taken" );
    }
}
