/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors:
 *   RedsTom
 *   Timkyl2203
 */

package fr.novalya.core.utils.commands;

import fr.novalya.core.Core;
import fr.novalya.core.utils.exceptions.NovaException;
import fr.novalya.core.utils.other.BiValMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class CustomCommand
        implements CommandExecutor, Listener
{

    CommandPlaceholders placeholders = new CommandPlaceholders();

    private String name;
    private String cmd;
    private BiValMap<String, Boolean, Integer> args;
    private String permission;

    public CustomCommand(String name, String cmd, BiValMap<String, Boolean, Integer> args, String permission) {
        this.name = name;
        this.cmd = cmd;
        this.args = args;
        this.permission = permission;
        placeholders.init(name, cmd, args);
    }

    public abstract boolean run(CommandSender sender, String[] args) throws NovaException;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender.hasPermission(permission))) {
            sendNoPerm(commandSender);
            return false;
        }
        boolean return_ = false;
        try {
            return_ = run(commandSender, strings);
        } catch (NovaException ignored) {}
        if(return_){
            sendBadCommandUsage(commandSender);
        }
        return false;
    }

    public void sendMessage(CommandSender player, String... messages) {

        YamlConfiguration config = YamlConfiguration.loadConfiguration(Core.getMessages());

        String prefix = config.getString("commands.prefix");
        String symbol = config.getString("commands.symbol");

        prefix = autoMessageBuilder(prefix);
        symbol = autoMessageBuilder(symbol);

        for (String s : messages) {
            s = autoMessageBuilder(s);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + ChatColor.RESET + symbol + " " + ChatColor.RESET + s));
        }
    }

    public void sendBadCommandUsage(CommandSender player) {

        YamlConfiguration config = YamlConfiguration.loadConfiguration(Core.getMessages());
        String badUsage = config.getString("commands.messages.bad_usage");

        for (String placeholder : placeholders.getPlaceholders()) {
            if (badUsage.contains(placeholder)) badUsage.replace(placeholder, placeholders.getValueFor(placeholder));
        }
        sendMessage(player, badUsage);
    }

    public void sendNoPerm(CommandSender player) {

        YamlConfiguration config = YamlConfiguration.loadConfiguration(Core.getMessages());
        String noPerm = config.getString("commands.messages.noperm");

        for (String placeholder : placeholders.getPlaceholders()) {
            if (noPerm.contains(placeholder)) noPerm.replace(placeholder, placeholders.getValueFor(placeholder));
        }
        sendMessage(player, noPerm);
    }

    private String autoMessageBuilder(String var) {
        StringBuilder sentence = new StringBuilder();
        int i = 0;
        for (String s : var.split(" ")) {
            i++;
            for (String placeholder : placeholders.getPlaceholders()) {
                if (s.contains(placeholder)) {
                    s = s.replaceAll(placeholder, placeholders.getValueFor(placeholder));
                }
            }
            sentence.append(s);
            if(!(i == var.split(" ").length)){
                sentence.append(" ");
            }
        }
        return sentence.toString();
    }

    public CustomCommand setArgs(BiValMap<String, Boolean, Integer> args) {
        this.args = args;
        return this;
    }

    public CustomCommand setCmd(String cmd) {
        this.cmd = cmd;
        return this;
    }

    public CustomCommand setName(String name) {
        this.name = name;
        return this;
    }

    private void updatePlaceholders(String name, String cmd, BiValMap<String, Boolean, Integer> args){
        placeholders.init(name, cmd, args);
    }

    public void broadcast(String message) {

        YamlConfiguration config = YamlConfiguration.loadConfiguration(Core.getMessages());

        String prefix = config.getString("commands.prefix");
        String symbol = config.getString("commands.symbol");

        prefix = autoMessageBuilder(prefix);
        symbol = autoMessageBuilder(symbol);
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + ChatColor.RESET + symbol + " " + ChatColor.RESET + message));

    }

    public void kickPlayer(Player player, String reason){

        YamlConfiguration config = YamlConfiguration.loadConfiguration(Core.getMessages());

        String prefix = config.getString("commands.prefix");
        String symbol = config.getString("commands.symbol");

        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', prefix + "Vous avez été expulsé : " + reason));
    }

    public String getCommandName() {
        return cmd;
    }
}
