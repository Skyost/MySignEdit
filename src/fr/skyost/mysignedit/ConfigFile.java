package fr.skyost.mysignedit;

import java.io.File;

import org.bukkit.plugin.Plugin;

import fr.skyost.mysignedit.utils.Config;

public class ConfigFile extends Config {
	
	public boolean EnableUpdater = true;
	
	public ConfigFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "MySignEdit Config";
	}
	
}
