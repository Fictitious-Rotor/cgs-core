package org.core.cgs.data;

public class JavaCheckedHissyFitException extends RuntimeException {
    public JavaCheckedHissyFitException(Exception culprit) {
        this.addSuppressed(culprit);
    }
}
