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
		if(sender instanceof Player) {
	    	player = (Player) sender;
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
								SignUtils.decolourize(str);
								s.setLine(l - 1, SignUtils.colourize(str));
								s.update(true);
							}
							else {
								sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageTooLong);
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageLookSign);
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageLineRange);
					}
				}
				else {
					if(args[0].equalsIgnoreCase("copy")) {
						if(player.getTargetBlock(null, 10).getState() instanceof Sign) {
							Sign s = (Sign)player.getTargetBlock(null, 10).getState();
							if(args.length == 1) {
								MySignEdit.clipboard.put(player, s.getLines());
								sender.sendMessage(ChatColor.GREEN + MySignEdit.messages.MessageCopiedSuccess);
							}
							else {
								if(SignUtils.isNumeric(args[1])) {
									int l = Integer.parseInt(args[1]);
									if(l >= 1 && l <= 4) {
										l--;
										String[] line = {"", "", "", ""};
										line[l] = s.getLine(l);
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
						else {
							sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageLookSign);
						}
					}
					else if(args[0].equalsIgnoreCase("paste")) {
						if(player.getTargetBlock(null, 10).getState() instanceof Sign) {
							if(MySignEdit.clipboard.get(player) != null) {
								String[] str = MySignEdit.clipboard.get(player);
								Sign s = (Sign)player.getTargetBlock(null, 10).getState();
								for(int i = 0; i < str.length; i++) {
									s.setLine(i, str[i]);
								}
								s.update(true);
							}
							else {
								sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageClipboard);
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageLookSign);
						}
					}
					else if(args[0].equalsIgnoreCase("clear")) {
						if(player.getTargetBlock(null, 10).getState() instanceof Sign) {
							Sign s = (Sign)player.getTargetBlock(null, 10).getState();
							s.setLine(0, "");
							s.setLine(1, "");
							s.setLine(2, "");
							s.setLine(3, "");
							s.update(true);
						}
						else {
							sender.sendMessage(ChatColor.RED + MySignEdit.messages.MessageLookSign);
						}
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
