package tools.dynamia.modules.saas.remote;

import tools.dynamia.modules.saas.api.AccountException;

/**
 * Throwed when remote accounts cant check authentication info
 */
public class RemoteAccountNotAuthenticatedException extends AccountException {

    public RemoteAccountNotAuthenticatedException() {
    }

    public RemoteAccountNotAuthenticatedException(String message) {
        super(message);
    }

    public RemoteAccountNotAuthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
