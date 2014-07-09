package com.github.unluckyninja.itemmanager;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemManager extends JavaPlugin {
    private Logger log;
    public static Configuration config;
    protected static boolean mode = false;
    protected static String language;
    protected static FileConfiguration message;
    @Override
    public void onEnable(){
        config = this.getConfig();
        config.options().copyDefaults(true);
        this.saveConfig();
        mode = config.getBoolean("mode");
        language = config.getString("language");
        message = new Message(this).getConfig();
        log = this.getLogger();
        getServer().getPluginManager().registerEvents(new ItemSpy(this),this);
        log.info(StrCha(message.getString(language+".onEnable"),null));
    }
    @Override
    public void onDisable() {
        log.info(StrCha(message.getString(language+".onDisable"),null));
    }
    public String StrCha(String string,ItemStack item){
        string = ChatColor.translateAlternateColorCodes('&', string);
        if(item != null){
            string = string.replace("{ITEMNAME}", item.getType().toString().toLowerCase());
            string = string.replace("{ITEMID}",""+item.getTypeId());
        }
        string = string.replace("{PLUGINNAME}", this.getDescription().getName());
        string = string.replace("{VERSION}", this.getDescription().getVersion());
        return string;
    }
}
