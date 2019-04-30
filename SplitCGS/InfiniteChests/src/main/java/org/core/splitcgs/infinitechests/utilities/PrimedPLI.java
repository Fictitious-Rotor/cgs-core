package org.core.splitcgs.infinitechests.utilities;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public final class PrimedPLI extends PlayerInterface {
    private final HumanEntity receiver;

    public PrimedPLI(final String subPluginName, final HumanEntity receiver) {
        super(subPluginName);
        this.receiver = receiver;
    }

    public HumanEntity getReceiver() {
        return receiver;
    }

    public Player getPlayer() {
        return (Player)receiver;
    }

    public void sendToPlayer(final String... messages) {
        sendToPlayer(receiver, messages);
    }
}