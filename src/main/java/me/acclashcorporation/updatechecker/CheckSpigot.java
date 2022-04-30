package me.acclashcorporation.updatechecker;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CheckSpigot {

    private UpdateChecker plugin;

    public CheckSpigot(UpdateChecker plugin) {
        this.plugin = plugin;
    }

    public static void checkSpigot(String mcversion) {

        ConsoleCommandSender console = Bukkit.getConsoleSender();

        File update = new File("Update.bat");
        if (update.exists()) {
            update.delete();
        }
        File updatel = new File("Update.sh");
        if (updatel.exists()) {
            updatel.delete();
        }
        File build = new File("Build.bat");
        if (build.exists()) {
            build.delete();
        }
        File buildl = new File("Build.sh");
        if (buildl.exists()) {
            buildl.delete();
        }
        String lversion = null;
        try {
            String out = new Scanner(new URL("https://hub.spigotmc.org/versions/1." + mcversion + ".json").openStream(), "UTF-8").useDelimiter("\\A").next();
            String lstring = out.substring(out.lastIndexOf("\"name\": \"") + 1);
            lversion = lstring.substring(8, lstring.indexOf("\"") + 8);
        } catch (IOException e) {
            console.sendMessage(ChatColor.RED + "[UpdateChecker] No internet: Unable to retrieve Paper versions.");
        }
        String bversion = Bukkit.getVersion();
        bversion = bversion.substring(bversion.indexOf("n ") + 1);
        bversion = bversion.substring(0, bversion.indexOf("-"));
        if (bversion.equals(lversion)) {
            console.sendMessage("[UpdateChecker] " + ChatColor.GREEN + "Your Spigot software is up to date.");
        }
        else {
            console.sendMessage("[UpdateChecker] " + ChatColor.YELLOW + "Your Spigot software isn't up to date(revision: " + bversion + " latest: " + lversion + "). Updating now...");
            try {
                File buildFolder = new File("BuildTools");
                File buildJar = new File("BuildTools.jar");
                if (!buildFolder.exists()) {
                    buildFolder.mkdirs();
                }
                console.sendMessage("[UpdateChecker] " + ChatColor.YELLOW + "Downloading Spigot takes a long time because it is downloaded using Build Tools. UpdateChecker will launch a separate window to complete the update. Make sure to NOT CLOSE it.");
                URL website = new URL("https://hub.spigotmc.org/jenkins/job/BuildTools/lastBuild/artifact/target/BuildTools.jar");
                FileUtils.copyURLToFile(website, buildJar);
                Files.move(buildJar, new File("BuildTools" + File.separator + "BuildTools.jar"));
                String OS = System.getProperty("os.name").toLowerCase();
                if (OS.contains("win")) {
                    PrintWriter writer = new PrintWriter("Build.bat", StandardCharsets.UTF_8);
                    writer.println("@echo off");
                    writer.println("cd BuildTools");
                    writer.println("java -jar BuildTools.jar --rev 1." + mcversion);
                    writer.println("echo Spigot software updated to revision " + lversion + ". It will take effect next server launch.");
                    writer.close();
                    Runtime.getRuntime().exec("cmd /c start /min \"\" Build.bat");
                } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
                    PrintWriter writer = new PrintWriter("Build.sh", StandardCharsets.UTF_8);
                    writer.println("#!/bin/bash");
                    writer.println("cd BuildTools");
                    writer.println("java -jar BuildTools.jar --rev 1." + mcversion);
                    writer.println("echo Spigot software updated to revision " + lversion + ". It will take effect next server launch.");
                    writer.close();
                    Runtime.getRuntime().exec("bash Build.sh");
                }
            } catch (IOException e) {
                console.sendMessage("[UpdateChecker] " + ChatColor.RED + "No internet: Unable to download Spigot software.");
            }
        }
    }

    public static void checkSpigot(String mcversion, CommandSender sender) {

        File update = new File("Update.bat");
        if (update.exists()) {
            update.delete();
        }
        File updatel = new File("Update.sh");
        if (updatel.exists()) {
            updatel.delete();
        }
        File build = new File("Build.bat");
        if (build.exists()) {
            build.delete();
        }
        File buildl = new File("Build.sh");
        if (buildl.exists()) {
            buildl.delete();
        }
        String lversion = null;
        try {
            String out = new Scanner(new URL("https://hub.spigotmc.org/versions/1." + mcversion + ".json").openStream(), "UTF-8").useDelimiter("\\A").next();
            String lstring = out.substring(out.lastIndexOf("\"name\": \"") + 1);
            lversion = lstring.substring(8, lstring.indexOf("\"") + 8);
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "No internet: Unable to retrieve Paper versions.");
        }
        String bversion = Bukkit.getVersion();
        bversion = bversion.substring(bversion.indexOf("n ") + 1);
        bversion = bversion.substring(0, bversion.indexOf("-"));
        if (bversion.equals(lversion)) {
            sender.sendMessage(ChatColor.GREEN + "Your Spigot software is up to date.");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Your Spigot software isn't up to date(revision: " + bversion + " latest: " + lversion + "). Updating now...");
            try {
                File buildFolder = new File("BuildTools");
                File buildJar = new File("BuildTools.jar");
                if (!buildFolder.exists()) {
                    buildFolder.mkdirs();
                }
                sender.sendMessage(ChatColor.YELLOW + "Downloading Spigot takes a long time because it is downloaded using Build Tools. UpdateChecker will launch a separate window to complete the update. Make sure to NOT CLOSE it.");
                URL website = new URL("https://hub.spigotmc.org/jenkins/job/BuildTools/lastBuild/artifact/target/BuildTools.jar");
                FileUtils.copyURLToFile(website, buildJar);
                Files.move(buildJar, new File("BuildTools" + File.separator + "BuildTools.jar"));
                String OS = System.getProperty("os.name").toLowerCase();
                if (OS.contains("win")) {
                    PrintWriter writer = new PrintWriter("Build.bat", StandardCharsets.UTF_8);
                    writer.println("@echo off");
                    writer.println("cd BuildTools");
                    writer.println("java -jar BuildTools.jar --rev 1." + mcversion);
                    writer.println("echo Spigot software updated to revision " + lversion + ". It will take effect next server launch.");
                    writer.close();
                    Runtime.getRuntime().exec("cmd /c start /min \"\" Build.bat");
                } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
                    PrintWriter writer = new PrintWriter("Build.sh", StandardCharsets.UTF_8);
                    writer.println("#!/bin/bash");
                    writer.println("cd BuildTools");
                    writer.println("java -jar BuildTools.jar --rev 1." + mcversion);
                    writer.println("echo Spigot software updated to revision " + lversion + ". It will take effect next server launch.");
                    writer.close();
                    Runtime.getRuntime().exec("bash Build.sh");
                    // MAC SUPPORT HERE?
                }
            } catch (IOException e) {
                sender.sendMessage(ChatColor.RED + "No internet: Unable to download Spigot software.");
            }
        }
    }
}