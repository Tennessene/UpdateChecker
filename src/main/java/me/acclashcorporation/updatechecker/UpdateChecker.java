package me.acclashcorporation.updatechecker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public final class UpdateChecker extends JavaPlugin {

    public void onEnable() {
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
            String out = new Scanner(new URL("https://papermc.io/api/v2/projects/paper/versions/1.18.2/").openStream(), "UTF-8").useDelimiter("\\A").next();
            String lstring = out.substring(out.lastIndexOf(",") + 1);
            lversion = lstring.replaceAll("[^0-9]", "");
        } catch (IOException e) {
            this.getLogger().info(ChatColor.RED + "No internet: Unable to retrieve Paper versions.");
        }

        String version = Bukkit.getVersion();
        String numberOnly = version.replaceAll("[^0-9]", "");
        String bversion = numberOnly.substring(0, numberOnly.length() - 4);
        if (bversion.equals(lversion)) {
            this.getLogger().info(ChatColor.GREEN + "Your Paper software is up to date.");
        } else {
            this.getLogger().info(ChatColor.YELLOW + "Your Paper software isn't up to date(revision: " + bversion + " latest: " + lversion + "). Updating now...");

            try {
                URL website = new URL("https://papermc.io/api/v2/projects/paper/versions/1.18.2/builds/" + lversion + "/downloads/paper-1.18.2-" + lversion + ".jar");
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream("paper-1.18.2-" + lversion + ".jar");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                Path path = Paths.get(System.getProperty("user.dir") + "\\ServerStart1gb.bat");
                Charset charset = StandardCharsets.UTF_8;
                String content = new String(Files.readAllBytes(path), charset);
                content = content.replaceAll("paper-1.18.2-" + bversion + ".jar", "paper-1.18.2-" + lversion + ".jar");
                Files.write(path, content.getBytes(charset));
                this.getLogger().info(ChatColor.GREEN + "Paper software updated to revision " + lversion + ". It will take effect next server launch.");
            } catch (IOException e) {
                this.getLogger().info(ChatColor.RED + "No internet: Unable to download Paper software.");
            }
        }

    }

    public void onDisable() {
    }
}