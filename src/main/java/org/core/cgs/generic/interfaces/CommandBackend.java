package org.core.cgs.generic.interfaces;

import org.bukkit.entity.Player;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.utilities.PrimedPLI;

public interface CommandBackend {
    boolean run(final Player runningPlayer,
                final PrimedPLI PPLI,
                final String[] arguments,
                final MetadataBundle metadataBundle);
}
