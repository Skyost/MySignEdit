package com.skyost.mysignedit;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.skyost.mysignedit.utils.SignUtils;

public class CommandsExecutor implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender sender, Command cmd, String label, String[] args){
		Player player = null;
		Sign sign = null;
		if(sender instanceof Player) {
	    	player = (Player) sender;
			if(player.getTargetBlock(null, 1000).getState() instanceof Sign) {
				sign = (Sign)player.getTargetBlock(null, 10).getState();
			}
			else {
				sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageLookSign);
				return true;
			}
			if(args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Available commands : /se <line> <text>, /se copy [line], /se paste or /se clear !");
				return true;
			}
	 	}
		else {
			sender.sendMessage(ChatColor.RED + "[MySignEdit] " + MySignEdit.messages.MessageNoConsole);
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
							sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageTooLong);
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageLineRange);
					}
				}
				else {
					if(args[0].equalsIgnoreCase("copy")) {
						if(args.length == 1) {
							MySignEdit.clipboard.put(player, sign.getLines());
							sender.sendMessage(ChatColor.GREEN + MySignEdit.messages.MessageCopiedSuccess);
						}
						else {
							if(SignUtils.isNumeric(args[1])) {
								int l = Integer.parseInt(args[1]);
								if(l >= 1 && l <= 4) {
									l--;
									String[] line = {"", "", "", ""};
									line[l] = sign.getLine(l);
									MySignEdit.clipboard.put(player, line);
									sender.sendMessage(ChatColor.GREEN + MySignEdit.messages.MessageLineCopiedSuccess);
								}
								else {
									sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageLineRange);
								}
							}
							else {
								sender.sendMessage(ChatColor.RED + "Available commands : /se <line> <text>, /se copy [line], /se paste or /se clear !");
							}
						}
					}
					else if(args[0].equalsIgnoreCase("paste")) {
						if(MySignEdit.clipboard.get(player) != null) {
							String[] str = MySignEdit.clipboard.get(player);
							for(int i = 0; i < str.length; i++) {
								sign.setLine(i, str[i]);
							}
							sign.update(true);
						}
						else {
							sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageClipboard);
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
				sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageNoPermission);
			}
		}
		return true;
	}

}