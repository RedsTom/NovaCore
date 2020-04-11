package fr.novalya.core;

import fr.novalya.core.commands.CommandRtp;
import fr.novalya.core.utils.commands.LoadCommands;
import fr.novalya.core.utils.listeners.LoadListeners;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Core extends JavaPlugin {

    private static Core instance;
    private static File messages;
    private static Economy econ;

    @Override
    public void onEnable() {

        instance = this;

        initMessages();
        setupEconomy();

        LoadListeners.onLoad();
        LoadCommands.onLoad();

    }

    @Override
    public void onDisable() {

    }

    private void initMessages() {

        messages = new File(getDataFolder() + File.separator + "messages.yml");

        if (!messages.exists()) {
            try {
                messages.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            YamlConfiguration configuration = YamlConfiguration.loadConfiguration(messages);

            configuration.set("commands.prefix", "&b%name%");
            configuration.set("commands.symbol", "&8»");

            configuration.set("commands.messages.bad_usage", "&cMauvaise syntaxe ! %command% %args%");
            configuration.set("commands.messages.noperm", "&cVous n'avez pas les permissions nécessaires");

            configuration.set("commands.private.format", "&7[&b%first% &6%symbol% &b%second%&7] &7%message%");
            configuration.set("commands.private.symbol", "►");

            try {
                configuration.save(messages);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Core getInstance() {
        return instance;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static File getMessages(){
        return messages;
    }
}
