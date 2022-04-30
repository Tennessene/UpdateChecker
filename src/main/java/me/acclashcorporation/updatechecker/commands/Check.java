package me.acclashcorporation.updatechecker.commands;

import me.acclashcorporation.updatechecker.CheckPaper;
import me.acclashcorporation.updatechecker.CheckSpigot;
import me.acclashcorporation.updatechecker.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Check implements CommandExecutor {

    private final UpdateChecker plugin;

    public Check(UpdateChecker plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(ChatColor.YELLOW + "Checking server software version...");
        String mcversion = Bukkit.getBukkitVersion();
        mcversion = mcversion.substring(mcversion.indexOf("1.") + 2);
        mcversion = mcversion.substring(0, mcversion.indexOf(".") + 2);
        if (Bukkit.getVersion().contains("Spigot")) {
            CheckSpigot.checkSpigot(mcversion, sender);
        } else if (Bukkit.getVersion().contains("Paper")) {
            //BROKE NEEDS FIXED
            //if (plugin.getConfig().getString("Start script") == null) {
            //    sender.sendMessage(ChatColor.RED + "You must enter a start script name in the configuration file to use this plugin!");
            //    plugin.getServer().getPluginManager().disablePlugin(plugin);
            //} else {
                CheckPaper.checkPaper(mcversion, sender);
            //}
        } else {
            CheckPaper.checkPaper(mcversion, sender);
        }
        return true;
    }
}

