package fr.novalya.core.utils.exceptions;

import fr.novalya.core.utils.commands.CustomCommand;
import org.bukkit.command.CommandSender;

public class HaveToBeAPlayerException extends NovaException {
    public HaveToBeAPlayerException(CommandSender sender, CustomCommand command) {
        super(command, sender, "L'exécuteur de la commande doit être un joueur !");
    }
}
