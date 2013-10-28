package com.skyost.mysignedit;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MySignEdit extends JavaPlugin {
	
	protected static MySignEditMessages messages;
	protected final static HashMap<Player, String[]> clipboard = new HashMap<Player, String[]>();
	
	public void onEnable() {
		try {
			messages = new MySignEditMessages(this);
			messages.init();
			this.getCommand("signedit").setExecutor(new CommandsExecutor());
		}
		catch(Exception ex)  {
			ex.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
		}
	}
}
