package fr.novalya.core.commands.goldp;

import com.google.common.collect.Lists;
import fr.novalya.core.utils.commands.CustomCommand;
import fr.novalya.core.utils.exceptions.HaveToBeAPlayerException;
import fr.novalya.core.utils.exceptions.NovaException;
import fr.novalya.core.utils.other.BiValMap;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CommandFurnace extends CustomCommand {
    public CommandFurnace(String four, String furnace, BiValMap<String, Boolean, Integer> args, String s) {
        super(four, furnace, args, s);
    }

    @Override
    public boolean run(CommandSender sender, String[] args) throws NovaException {

        if(!(sender instanceof Player)) throw new HaveToBeAPlayerException(sender, this);

        Player player = (Player) sender;

        List<FurnaceRecipe> furnaceRecipes = Lists.newArrayList();
        Iterator<Recipe> recipes = Bukkit.recipeIterator();
        recipes.forEachRemaining(recipe -> {if(recipe instanceof FurnaceRecipe) furnaceRecipes.add((FurnaceRecipe) recipe);});

        AtomicBoolean isRecipe = new AtomicBoolean(false);
        AtomicReference<FurnaceRecipe> correctRecipe = new AtomicReference<>();

        player.updateInventory();

        furnaceRecipes.forEach(recipe -> {

            if(recipe.getInput() == player.getInventory().getItemInMainHand()){
                isRecipe.set(true);
                correctRecipe.set(recipe);
            }

        });

        sendMessage(sender, "Peut se cuire : " + isRecipe.get());
        return false;
    }
}
