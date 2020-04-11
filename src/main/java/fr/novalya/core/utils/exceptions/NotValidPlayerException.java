package fr.novalya.core.utils.exceptions;

import fr.novalya.core.utils.commands.CustomCommand;
import org.bukkit.command.CommandSender;

public class NotValidPlayerException extends NovaException {
    public NotValidPlayerException(CommandSender sender, CustomCommand command) {
        super(command, sender, "Le joueur que vous avez mentionné est invalide !");
    }
}
