package fr.novalya.core.utils.listeners;

import fr.novalya.core.Core;
import fr.novalya.core.listeners.FoodLevelChangeListener;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class LoadListeners {
    public static void onLoad() {

        registerListener(FoodLevelChangeEvent.class, new FoodLevelChangeListener());

    }

    private static void registerGListener(Listener event) {
        Core core = Core.getInstance();
        core.getServer().getPluginManager().registerEvents(event, core);
    }

    @SuppressWarnings("unchecked")
    private static<T> void registerListener(Class<? extends Event> eventType, CustomListener<T> executor){
        Core core = Core.getInstance();
        core.getServer().getPluginManager().registerEvent(eventType, executor, EventPriority.NORMAL, executor, core);
    }

}
