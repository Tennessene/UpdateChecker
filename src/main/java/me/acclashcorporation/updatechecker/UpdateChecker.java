package me.acclashcorporation.updatechecker;

import me.acclashcorporation.updatechecker.commands.Check;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public final class UpdateChecker extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("check").setExecutor(new Check(this));
        String mcversion = Bukkit.getBukkitVersion();
        mcversion = mcversion.substring(mcversion.indexOf("1.") + 2);
        mcversion = mcversion.substring(0, mcversion.indexOf(".") + 2);
        if (Bukkit.getVersion().contains("Spigot")) {
            CheckSpigot.checkSpigot(mcversion);
        } else if (Bukkit.getVersion().contains("Paper")) {
            //BROKE NEEDS FIXED
            //if (getConfig().getString("Start script") == null) {
            //    getLogger().severe("You must enter a valid start script name in the configuration file to use this plugin!");
            //    getServer().getPluginManager().disablePlugin(this);
            //} else {
                CheckPaper.checkPaper(mcversion);
            //}
        } else {
            CheckPaper.checkPaper(mcversion);
        }
    }

    @Override
    public void onDisable() {
        if (Bukkit.getVersion().contains("Spigot")) {
            String mcversion = Bukkit.getBukkitVersion();
            mcversion = mcversion.substring(mcversion.indexOf("1.") + 2);
            mcversion = mcversion.substring(0, mcversion.indexOf(".") + 2);
            File JAR = new File("BuildTools" + File.separator + "spigot-1." + mcversion + ".jar");
            File finalJAR = new File("spigot-1." + mcversion + " (new).jar");
            try {
                String OS = System.getProperty("os.name").toLowerCase();
                if (OS.contains("win")) {
                    if (finalJAR.exists()) {
                        PrintWriter writer = new PrintWriter("Update.bat", StandardCharsets.UTF_8);
                        writer.println("@echo off");
                        writer.println("ren \"" + JAR + "\" \"" + finalJAR + "\"");
                        writer.close();
                        Runtime.getRuntime().exec("cmd /c start /min \"\" Update.bat");
                    }
                } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
                    PrintWriter writer = new PrintWriter("Update.sh", StandardCharsets.UTF_8);
                    writer.println("#!/bin/bash");
                    writer.println("mv \"" + JAR + "\" \"" + finalJAR + "\"");
                    writer.close();
                    Runtime.getRuntime().exec("bash Update.sh");
                }
                // MAC SUPPORT HERE?
            } catch (IOException ignored) {
            }
        }
    }
}