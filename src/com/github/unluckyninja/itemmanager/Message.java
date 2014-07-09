package com.github.unluckyninja.itemmanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Message {
    private ItemManager IM;
    private FileConfiguration IPlusStrings = null;
    private File IPlusStringsFile;
    public String language;
    
    public Message(ItemManager IM) {
	this.IM = IM;
        loadConfig();
    }

    public final void loadConfig() {
        IPlusStringsFile = new File(IM.getDataFolder(), "messages.yml");
        IPlusStrings = YamlConfiguration.loadConfiguration(IPlusStringsFile);
        if(!IPlusStringsFile.exists()){
            try{
                IM.saveResource("messages.yml",false);
            }catch(IllegalArgumentException exc){
                
            }
        }
        InputStream defConfigStream = IM.getResource("messages.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            IPlusStrings.setDefaults(defConfig);
        }
        language = getLanguage();
    }
    
    public FileConfiguration getConfig() {
        if (IPlusStrings == null) {
            loadConfig();
        }
        return IPlusStrings;
    }
    
    public void saveConfig() {
        if (IPlusStrings == null || IPlusStringsFile == null) {
            return;
        }
        try {
            IPlusStrings.save(IPlusStringsFile);
        } catch (IOException ex) {
            Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE, "Could not save config to " + IPlusStringsFile, ex);
        }
    }
    
    public String getLanguage(){
        return IM.getConfig().getString("language");
    }
}
