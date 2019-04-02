package org.core.cgs.generic.utilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;

public class PlayerInterface {
    private final String subPluginName;

    public PlayerInterface(final String subPluginName) {
        this.subPluginName = subPluginName;
    }

    public void sendToPlayer(final HumanEntity receiver, final String... messages) {
        StringBuilder toSend = new StringBuilder();

        toSend.append(ChatColor.BOLD).append(ChatColor.GOLD)
              .append(subPluginName).append(": ")
              .append(ChatColor.ITALIC).append(ChatColor.DARK_AQUA);

        for (String message : messages) {
            toSend.append(message)
                  .append('\n');
        }

        toSend.append(ChatColor.RESET);

        receiver.sendMessage(toSend.toString());
    }

    public PrimedPLI prime(final HumanEntity receiver) {
        return new PrimedPLI(subPluginName, receiver);
    }
}
