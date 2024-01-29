package CompteAr.backend.exception;

/**
 * Exception liée au problèmes d'authentification.
 *
 */
public class AuthenticationException extends Exception {
	
    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message) {
        super(message);
    }
}

