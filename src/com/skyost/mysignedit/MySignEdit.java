package com.skyost.mysignedit;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MySignEdit extends JavaPlugin implements Listener {
	
	private MySignEditMessages messages;
	private HashMap<Player, String[]> clipboard = new HashMap<Player, String[]>();
	
	public void onEnable() {
		try {
			messages = new MySignEditMessages(this);
			messages.init();
		}
		catch(Exception ex)  {
			ex.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
		}
	}

	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args){
		Player player = null;
		if(sender instanceof Player) {
	    	player = (Player) sender;
	 	}
		else {
			sender.sendMessage(ChatColor.RED + "[MySignEdit] " + messages.MessageNoConsole);
			return false;
		}
		if(cmd.getName().equalsIgnoreCase("signedit")) {
			if(player.hasPermission("se.edit")) {
				try {
					int l = Integer.parseInt(args[0]);
					if(l >= 1 && l <= 4) {
						if(player.getTargetBlock(null, 10).getState() instanceof Sign) {
							Sign s = (Sign)player.getTargetBlock(null, 10).getState();
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
								s.setLine(l - 1, str);
								s.update(true);
							}
							else {
								sender.sendMessage(ChatColor.RED + messages.MessageTooLong);
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + messages.MessageLookSign);
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + messages.MessageLineRange);
					}
				}
				catch(Exception ex)  {
					if(args[0].equalsIgnoreCase("copy")) {
						if(player.getTargetBlock(null, 10).getState() instanceof Sign) {
							Sign s = (Sign)player.getTargetBlock(null, 10).getState();
							clipboard.put(player, s.getLines());
							sender.sendMessage(ChatColor.GREEN + messages.MessageCopiedSuccess);
						}
						else {
							sender.sendMessage(ChatColor.RED + messages.MessageLookSign);
						}
					}
					else if(args[0].equalsIgnoreCase("paste")) {
						if(player.getTargetBlock(null, 10).getState() instanceof Sign) {
							if(clipboard.get(player) != null) {
								String[] str = clipboard.get(player);
								Sign s = (Sign)player.getTargetBlock(null, 10).getState();
								for(int i = 0; i < str.length; i++) {
									s.setLine(i, str[i]);
									s.update(true);
								}
							}
							else {
								sender.sendMessage(ChatColor.RED + messages.MessageClipboard);
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + messages.MessageLookSign);
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + "Available commands : /se <text>, /se copy or / se paste !");
					}
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + messages.MessageNoPermission);
			}
		}
		return true;
	}
}
