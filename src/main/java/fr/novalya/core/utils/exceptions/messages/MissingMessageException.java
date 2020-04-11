package fr.novalya.core.utils.exceptions.messages;

import fr.novalya.core.utils.commands.CustomCommand;
import fr.novalya.core.utils.exceptions.NovaException;
import org.bukkit.command.CommandSender;

public class MissingMessageException extends NovaException {
    public MissingMessageException(CommandSender sender, CustomCommand clazz) {
        super(clazz, sender, "Veuillez préciser un message !");
    }
}
