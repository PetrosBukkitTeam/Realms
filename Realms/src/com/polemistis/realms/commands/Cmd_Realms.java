package com.polemistis.realms.commands;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.polemistis.realms.Permissions;

public class Cmd_Realms implements CommandExecutor
{
	private static final String LABEL = "realms";
	private static final Map<String, com.polemistis.realms.Permissions> permissionMap = new HashMap<String, com.polemistis.realms.Permissions>();
	static {
		permissionMap.put("help", Permissions.CMD_HELP);
		permissionMap.put("claim", Permissions.CMD_CLAIM);
	}
	
	public static String getLabel()
	{
		return Cmd_Realms.LABEL;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player player = null;
		String branch = (args.length > 0 ? args[0] : "help").toLowerCase();
		Permissions perm = permissionMap.containsKey(branch) ? permissionMap.get(branch) : null;
		
		if(sender instanceof Player)
		{
			player = (Player) sender;
		}
		if(branch.equalsIgnoreCase("help"))
		{
			if(sender.hasPermission(perm.getValue()))
			{
				sender.sendMessage(ChatColor.GOLD + "Welcome to Realms!");
			}
		}
		else if(branch.equalsIgnoreCase("claim"))
		{
			
		}
		return false;
	}
	
}
