package fr.skyost.mysignedit.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skyost.mysignedit.MySignEdit;
import fr.skyost.mysignedit.utils.SignUtils;

public class CommandsExecutor implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args){
		Player player = null;
		Sign sign = null;
		if(sender instanceof Player) {
	    	player = (Player)sender;
	    	final BlockState block = player.getTargetBlock(null, 1000).getState();
			if(block instanceof Sign) {
				sign = (Sign)block;
			}
			else {
				sender.sendMessage(ChatColor.RED + MySignEdit.getMessagesFile().MessageLookSign);
				return true;
			}
			if(args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Available commands : /se <line> <text>, /se copy [line], /se paste or /se clear !");
				return true;
			}
	 	}
		else {
			sender.sendMessage(ChatColor.RED + "[MySignEdit] " + MySignEdit.getMessagesFile().MessageNoConsole);
			return false;
		}
		if(cmd.getName().equalsIgnoreCase("signedit")) {
			if(player.hasPermission("se.edit")) {
				if(SignUtils.isNumeric(args[0])) {
					int l = Integer.parseInt(args[0]);
					if(l >= 1 && l <= 4) {
						String str = "";
						for(int i = 1; i < args.length; i++) {
							if(str.equals("")) {
								str = args[i];
							}
							else {
								str = str + " " + args[i];
							}
						}
						if(str.length() <= 14) {
							SignUtils.decolourize(str);
							sign.setLine(l - 1, SignUtils.colourize(str));
							sign.update(true);
						}
						else {
							sender.sendMessage(ChatColor.RED + MySignEdit.getMessagesFile().MessageTooLong);
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + MySignEdit.getMessagesFile().MessageLineRange);
					}
				}
				else {
					if(args[0].equalsIgnoreCase("copy")) {
						if(args.length == 1) {
							MySignEdit.setClipboard(player, sign.getLines());
							sender.sendMessage(ChatColor.GREEN + MySignEdit.getMessagesFile().MessageCopiedSuccess);
						}
						else {
							if(SignUtils.isNumeric(args[1])) {
								int l = Integer.parseInt(args[1]);
								if(l >= 1 && l <= 4) {
									l--;
									String[] line = {"", "", "", ""};
									line[l] = sign.getLine(l);
									MySignEdit.setClipboard(player, line);
									sender.sendMessage(ChatColor.GREEN + MySignEdit.getMessagesFile().MessageLineCopiedSuccess);
								}
								else {
									sender.sendMessage(ChatColor.RED + MySignEdit.getMessagesFile().MessageLineRange);
								}
							}
							else {
								sender.sendMessage(ChatColor.RED + "Available commands : /se <line> <text>, /se copy [line], /se paste or /se clear !");
							}
						}
					}
					else if(args[0].equalsIgnoreCase("paste")) {
						final String[] contents = MySignEdit.getClipboard(player);
						if(contents != null) {
							for(int i = 0; i < contents.length; i++) {
								sign.setLine(i, contents[i]);
							}
							sign.update(true);
						}
						else {
							sender.sendMessage(ChatColor.RED + MySignEdit.getMessagesFile().MessageClipboard);
						}
					}
					else if(args[0].equalsIgnoreCase("clear")) {
						sign.setLine(0, "");
						sign.setLine(1, "");
						sign.setLine(2, "");
						sign.setLine(3, "");
						sign.update(true);
					}
					else {
						sender.sendMessage(ChatColor.RED + "Available commands : /se <line> <text>, /se copy [line], /se paste or /se clear !");
					}
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + MySignEdit.getMessagesFile().MessageNoPermission);
			}
		}
		return true;
	}

}