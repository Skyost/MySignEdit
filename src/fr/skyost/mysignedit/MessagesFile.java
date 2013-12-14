package fr.skyost.mysignedit;

import java.io.File;

import org.bukkit.plugin.Plugin;

import fr.skyost.mysignedit.utils.Config;

public class MessagesFile extends Config {
	
	public String MessageLineRange = "Line must be in the range 1 to 4.";
	public String MessageLookSign = "You must be looking at a Sign !";
	public String MessageTooLong = "Lign is too long, maximum allowed is 14 characters !";
	public String MessageCopiedSuccess = "Sign was copied with success ! Paste it with /signedit paste !";
	public String MessageLineCopiedSuccess = "This line was copied with success !";
	public String MessageNoConsole = "You can't do this from the console !";
	public String MessageNoPermission = "You don't have permission to do this !";
	public String MessageClipboard = "Please copy a Sign with /signedit copy before !";
	
	public MessagesFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "messages.yml");
		CONFIG_HEADER = "MySignEdit Messages";
	}
	
}
