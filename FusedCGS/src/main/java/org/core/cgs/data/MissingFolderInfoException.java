package org.core.cgs.data;

import org.core.cgs.generic.utilities.ExceptionUtils;

public class MissingFolderInfoException extends RuntimeException {
    public MissingFolderInfoException(final String subPluginName) {
        super(ExceptionUtils.getNullSafeMessage("Could not find a matching SubPluginFolderInfo class for the sub plugin name of '%s'", subPluginName));
    }
}
