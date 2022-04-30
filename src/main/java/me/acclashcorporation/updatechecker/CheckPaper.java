package me.acclashcorporation.updatechecker;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class CheckPaper {

    private static UpdateChecker plugin;

    public CheckPaper(UpdateChecker plugin) {
        this.plugin = plugin;
    }

    // HAS ERROR
    // Cannot invoke "me.acclashcorporation.updatechecker.UpdateChecker.getConfig()" because "me.acclashcorporation.updatechecker.CheckPaper.plugin" is null

    public static void checkPaper(String mcversion) {


        ConsoleCommandSender console = Bukkit.getConsoleSender();

        File folder = new File(System.getProperty("user.dir"));
        File fList[] = folder.listFiles();
        for (int i = 0; i < fList.length; i++) {
            String oldjar = String.valueOf(fList[i]);
            if (oldjar.endsWith(".jar")) {
                boolean success = (new File(String.valueOf(fList[i])).delete());
            }
        }
        String lversion = null;
        try {
            String out = new Scanner(new URL("https://papermc.io/api/v2/projects/paper/versions/1." + mcversion + "/").openStream(), "UTF-8").useDelimiter("\\A").next();
            String lstring = out.substring(out.lastIndexOf(",") + 1);
            lversion = lstring.replaceAll("[^0-9]", "");
        } catch (IOException e) {
            console.sendMessage("[UpdateChecker] " + ChatColor.RED + "No internet: Unable to retrieve Paper versions.");
        }
        String version = Bukkit.getVersion();
        String numberOnly = version.replaceAll("[^0-9]", "");
        String bversion = numberOnly.substring(0, numberOnly.length() - 4);
        if (bversion.equals(lversion)) {
            console.sendMessage("[UpdateChecker] " + ChatColor.GREEN + "Your Paper software is up to date.");
        }
        else {
            console.sendMessage("[UpdateChecker] " + ChatColor.YELLOW + "Your Paper software isn't up to date(revision: " + bversion + " latest: " + lversion + "). Updating now...");
            try {
                URL website = new URL("https://papermc.io/api/v2/projects/paper/versions/1." + mcversion + "/builds/" + lversion + "/downloads/paper-1." + mcversion + "-" + lversion + ".jar");
                FileUtils.copyURLToFile(website, new File("paper-1." + mcversion + "-" + lversion + ".jar"));
                Path path = Paths.get(plugin.getConfig().getString("Start script"));
                Charset charset = StandardCharsets.UTF_8;
                String content = new String(Files.readAllBytes(path), charset);
                content = content.replaceAll("paper-1." + mcversion + "-" + bversion + ".jar", "paper-1." + mcversion + "-" + lversion + ".jar");
                Files.write(path, content.getBytes(charset));
                console.sendMessage("[UpdateChecker] " + ChatColor.GREEN + "Paper software updated to revision " + lversion + ". It will take effect next server launch.");
            } catch (IOException e) {
                console.sendMessage("[UpdateChecker] " + ChatColor.RED + "No internet: Unable to download Paper software.");
            }
        }
    }

    public static void checkPaper(String mcversion, CommandSender sender) {

        File folder = new File(System.getProperty("user.dir"));
        File fList[] = folder.listFiles();
        for (int i = 0; i < fList.length; i++) {
            String oldjar = String.valueOf(fList[i]);
            if (oldjar.endsWith(".jar")) {
                boolean success = (new File(String.valueOf(fList[i])).delete());
            }
        }
        String lversion = null;
        try {
            String out = new Scanner(new URL("https://papermc.io/api/v2/projects/paper/versions/1." + mcversion + "/").openStream(), "UTF-8").useDelimiter("\\A").next();
            String lstring = out.substring(out.lastIndexOf(",") + 1);
            lversion = lstring.replaceAll("[^0-9]", "");
        } catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "No internet: Unable to retrieve Paper versions.");
        }
        String version = Bukkit.getVersion();
        String numberOnly = version.replaceAll("[^0-9]", "");
        String bversion = numberOnly.substring(0, numberOnly.length() - 4);
        if (bversion.equals(lversion)) {
            sender.sendMessage(ChatColor.GREEN + "Your Paper software is up to date.");
        }
        else {
            sender.sendMessage(ChatColor.YELLOW + "Your Paper software isn't up to date(revision: " + bversion + " latest: " + lversion + "). Updating now...");
            try {
                URL website = new URL("https://papermc.io/api/v2/projects/paper/versions/1." + mcversion + "/builds/" + lversion + "/downloads/paper-1." + mcversion + "-" + lversion + ".jar");
                FileUtils.copyURLToFile(website, new File("paper-1." + mcversion + "-" + lversion + ".jar"));
                Path path = Paths.get(plugin.getConfig().getString("Start script"));
                Charset charset = StandardCharsets.UTF_8;
                String content = new String(Files.readAllBytes(path), charset);
                content = content.replaceAll("paper-1." + mcversion + "-" + bversion + ".jar", "paper-1." + mcversion + "-" + lversion + ".jar");
                Files.write(path, content.getBytes(charset));
                sender.sendMessage(ChatColor.GREEN + "Paper software updated to revision " + lversion + ". It will take effect next server launch.");
            } catch (IOException e) {
                sender.sendMessage(ChatColor.RED + "No internet: Unable to download Paper software.");
            }
        }
    }
}
