package io.softwarebuilding.fusionplex.error;

import java.io.Serial;

public class FusionPlexException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8279660641592221986L;

    public FusionPlexException() {
        super();
    }

    public FusionPlexException(final String message) {
        super(message);
    }
    public FusionPlexException( final Throwable cause ) {
        super( cause );
    }

    public FusionPlexException( final String message, final Throwable cause ) {
        super( message, cause );
    }
}
