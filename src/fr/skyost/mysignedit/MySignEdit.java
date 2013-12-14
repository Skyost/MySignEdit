package fr.skyost.mysignedit;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skyost.mysignedit.listeners.CommandsExecutor;
import fr.skyost.mysignedit.utils.Metrics;
import fr.skyost.mysignedit.utils.Updater;
import fr.skyost.mysignedit.utils.Updater.UpdateType;

public class MySignEdit extends JavaPlugin {
	
	private static MessagesFile messages;
	private final static HashMap<String, String[]> clipboard = new HashMap<String, String[]>();
	
	public void onEnable() {
		try {
			messages = new MessagesFile(this);
			messages.init();
			ConfigFile config = new ConfigFile(this);
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
	
	public static MessagesFile getMessagesFile() {
		return messages;
	}
	
	public static String[] getClipboard(final Player player) {
		return clipboard.get(player.getName());
	}
	
	public static void setClipboard(final Player player, String[] contents) {
		clipboard.put(player.getName(), contents);
	}
	
}
