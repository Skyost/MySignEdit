package com.skyost.mysignedit;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.skyost.mysignedit.utils.Metrics;
import com.skyost.mysignedit.utils.Updater;
import com.skyost.mysignedit.utils.Updater.UpdateType;

public class MySignEdit extends JavaPlugin {
	
	protected static MySignEditMessages messages;
	protected final static HashMap<Player, String[]> clipboard = new HashMap<Player, String[]>();
	
	public void onEnable() {
		try {
			messages = new MySignEditMessages(this);
			messages.init();
			MySignEditConfig config = new MySignEditConfig(this);
			config.init();
			if(config.EnableUpdater) {
				new Updater(this, 64350, this.getFile(), UpdateType.DEFAULT, true);
			}
			startMetrics();
			this.getCommand("signedit").setExecutor(new CommandsExecutor());
		}
		catch(Exception ex)  {
			ex.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	private void startMetrics() {
		try {
		    Metrics metrics = new Metrics(this);
		    metrics.start();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
