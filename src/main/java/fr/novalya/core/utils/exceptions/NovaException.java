package fr.novalya.core.utils.exceptions;

import fr.novalya.core.utils.commands.CustomCommand;
import org.bukkit.command.CommandSender;

public class NovaException extends Throwable {
    public NovaException(CustomCommand command, CommandSender sender, String s) {
        if(!s.isEmpty()) command.sendMessage(sender, "&c" + s);
    }
}
