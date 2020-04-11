package fr.novalya.core.utils.commands;

import com.google.common.collect.Maps;
import fr.novalya.core.utils.exceptions.NovaException;
import fr.novalya.core.utils.exceptions.cooldown.CooldownNotPassedException;
import fr.novalya.core.utils.other.BiValMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

public abstract class CooldownedCustomCommand extends CustomCommand{

    private Map<UUID, Long> cooldowns = Maps.newHashMap();
    private long cooldownInSeconds;
    private String permission;

    public CooldownedCustomCommand(String name, String cmd, BiValMap<String, Boolean, Integer> args, String permissions, long cooldownInSeconds) {
        super(name, cmd, args, permissions);
        this.cooldownInSeconds = cooldownInSeconds + 1;
        this.permission = permissions;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(!(commandSender.hasPermission(permission))){
            sendNoPerm(commandSender);
            return false;
        }

        boolean b = false;

        if(!(commandSender instanceof Player)) return false;

        if(commandSender.hasPermission("moderateur.use")) {
            try {
                b = run(commandSender, strings);
                if(commandSender instanceof Player) ((Player) commandSender).sendTitle("", "Vous avez bypass le cooldown", 10, 20, 10);
            } catch (NovaException e) {
            }
            return false;
        }

        Player player = (Player) commandSender;
        if(cooldowns.get(player.getUniqueId()) == null) {
            setCooldown(player);
            try {
                b = run(commandSender, strings);
            } catch (NovaException ignored) {}
            if(b){
                sendBadCommandUsage(commandSender);
            }
            return false;
        }

        if(ZonedDateTime.now().toInstant().getEpochSecond() < cooldowns.get(player.getUniqueId())){
            try {
                throw new CooldownNotPassedException(this, commandSender, getCooldown(player));
            } catch (CooldownNotPassedException ignored) {}
        }
        else {
            setCooldown(player);
            try {
                b = run(commandSender, strings);
            } catch (NovaException e) {
            }
        }

        if(b){
            sendBadCommandUsage(commandSender);
        }

        return false;
    }

    protected void setCooldown(Player player){
        long zoneDateTime = ZonedDateTime.now().toInstant().getEpochSecond();
        cooldowns.put(player.getUniqueId(), (zoneDateTime + cooldownInSeconds));
    }
    protected void resetCooldown(Player player){
        cooldowns.put(player.getUniqueId(), null);
    }
    protected long getCooldown(Player player){
        return cooldowns.getOrDefault(player.getUniqueId(), 0L) - ZonedDateTime.now().toInstant().getEpochSecond() ;
    }
}
