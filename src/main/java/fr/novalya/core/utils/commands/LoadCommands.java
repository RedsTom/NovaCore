/*
 * Copyright  - ALL RIGHT RESERVED
 * Authors:
 *   RedsTom
 *   Timkyl2203
 */

package fr.novalya.core.utils.commands;

import fr.novalya.core.Core;
import fr.novalya.core.commands.CommandRtp;
import fr.novalya.core.commands.goldp.CommandFurnace;
import fr.novalya.core.utils.other.BiValHashMap;
import fr.novalya.core.utils.other.BiValMap;
import org.bukkit.Bukkit;

public class LoadCommands {

    public static void onLoad(){
        BiValMap<String, Boolean, Integer> args = new BiValHashMap<>();

        registerCommand("randomtp", new CommandRtp("RandomTp", "rtp", args, "joueur.use"));
        registerCommand("furnace", new CommandFurnace("Four", "furnace", args, "goldp.use"));

    }

    private static void registerCommand(String commandName, CustomCommand executor){
        Core.getInstance().getCommand(commandName).setExecutor(executor);
        Bukkit.getPluginManager().registerEvents(executor, Core.getInstance());
    }

}
