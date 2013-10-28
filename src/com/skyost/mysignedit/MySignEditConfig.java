package com.skyost.mysignedit;

import java.io.File;

import org.bukkit.plugin.Plugin;

import com.skyost.mysignedit.utils.Config;

public class MySignEditConfig extends Config {
	public MySignEditConfig(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "MySignEdit Config";
	}
	
	public boolean EnableUpdater = true;
}
