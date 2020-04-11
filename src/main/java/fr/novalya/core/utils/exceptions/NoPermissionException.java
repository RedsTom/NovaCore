package fr.novalya.core.utils.exceptions;


import fr.novalya.core.utils.commands.CustomCommand;
import org.bukkit.command.CommandSender;

public class NoPermissionException extends NovaException {

    public NoPermissionException(CustomCommand command, CommandSender sender) {
        super(command, sender, "");
        command.sendNoPerm(sender);
    }
}
