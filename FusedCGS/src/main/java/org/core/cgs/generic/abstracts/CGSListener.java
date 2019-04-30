package org.core.cgs.generic.abstracts;

import org.bukkit.event.Listener;
import org.core.cgs.generic.classes.MetadataBundle;
import org.core.cgs.generic.utilities.PlayerInterface;

public abstract class CGSListener implements Listener {
    final protected MetadataBundle metadataBundle;
    final protected PlayerInterface playerInterface;

    public CGSListener(final MetadataBundle metadataBundle, final PlayerInterface playerInterface) {
        this.metadataBundle = metadataBundle;
        this.playerInterface = playerInterface;
    }
}
