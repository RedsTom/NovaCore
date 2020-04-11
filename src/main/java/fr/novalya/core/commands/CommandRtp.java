package fr.novalya.core.commands;

import fr.novalya.core.utils.commands.CooldownedCustomCommand;
import fr.novalya.core.utils.exceptions.NovaException;
import fr.novalya.core.utils.other.BiValMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.stream.IntStream;

public class CommandRtp extends CooldownedCustomCommand {
    public CommandRtp(String name, String cmd, BiValMap<String, Boolean, Integer> args, String permission) {
        super(name, cmd, args, permission, 60);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws NovaException {

        if(!(sender instanceof Player)){
            sender.sendMessage("§cVous devez être un joueur pour exécuter cette commande !");
            return false;
        }

        int x = new Random().nextInt(10000);
        int z = new Random().nextInt(10000);

        if(x > (x / 2)){
            x /= 2;
            x = -x;

        }

        if(z > (z / 2)){
            z /= 2;
            z = -z;

        }

        int y = 0;

        for (int i : IntStream.range(0, 255).toArray()){
            Block b = ((Player) sender).getWorld().getBlockAt(x, i, z);
            Block bt = ((Player) sender).getWorld().getBlockAt(x, i + 1, z);
            Block bu = ((Player) sender).getWorld().getBlockAt(x, i - 1, z);
            if(b.getType() == Material.AIR && bt.getType() == Material.AIR && bu.getType() != Material.AIR) y = b.getY() + 4;
        }

        ((Player) sender).teleport(new Location(((Player) sender).getWorld(), x, y, z));
        sendMessage(sender, "§aVous avez bien été téléporté aléatoirement !");
        return false;
    }
}
