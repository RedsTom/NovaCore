package fr.novalya.core.utils.exceptions.cooldown;

import fr.novalya.core.utils.commands.CustomCommand;
import fr.novalya.core.utils.exceptions.NovaException;
import org.bukkit.command.CommandSender;

public class CooldownNotPassedException extends NovaException {
    public CooldownNotPassedException(CustomCommand command, CommandSender sender, long cooldown) {
        super(command, sender, "Veuillez patienter encore &b" + cooldown + " secondes &c pour pouvoir de nouveau exécuter cette commande");
    }
}
