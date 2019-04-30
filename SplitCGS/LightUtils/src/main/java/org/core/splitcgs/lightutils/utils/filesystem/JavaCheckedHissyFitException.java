package org.core.splitcgs.lightutils.utils.filesystem;

public class JavaCheckedHissyFitException extends RuntimeException {
    public JavaCheckedHissyFitException(Exception culprit) {
        this.addSuppressed(culprit);
    }
}
