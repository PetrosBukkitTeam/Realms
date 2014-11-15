package com.polemistis.realms;

import java.io.File;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.polemistis.realms.commands.Cmd_Realms;

public class Realms extends JavaPlugin
{
	static Realms plugin;	
	PluginConfiguration config;
	
	private PluginManager pm;
	private ServicesManager sm;
	
	static final String DEFAULT_PROPERTIES_RESOURCE_FILE = "config.yml";
	static final Logger LOG = Logger.getLogger("Minecraft");
	
	/*
	 * essential service-providers
	 */
	private Economy econ_provider;
	
	public Economy getEconomyProvider()
	{
		return this.econ_provider;
	}
	
	public static Realms getPluginInstance()
	{
		return Realms.plugin;
	}
	public PluginConfiguration getPluginConfig()
	{
		return this.config;
	}
	@Override
	public void onLoad()
	{
		Realms.plugin = this;
		this.config = new PluginConfiguration(new File(this.getDataFolder(), Realms.DEFAULT_PROPERTIES_RESOURCE_FILE));
		this.pm = Bukkit.getPluginManager();
		this.sm = Bukkit.getServicesManager();
	}
	@Override
	public void onEnable()
	{
		this.initServiceProviderVars();
		this.registerCommandExecutives();
	}
	@Override
	public void onDisable()
	{
		this.config.updateWithStream();
	}
	private void registerCommandExecutives()
	{
		this.getCommand(Cmd_Realms.getLabel()).setExecutor(new com.polemistis.realms.commands.Cmd_Realms());
	}
	private void initServiceProviderVars()
	{
		boolean status_list[] =
		{
			initEconomyProviderVar("Vault")
		};
		for(int i = 0; i < status_list.length; ++i)
		{
			if(!status_list[i])
			{
				LOG.severe(format_message("Disabling plugin..."));
				this.pm.disablePlugin(this);
				
				return;
			}
		}
	}
	private boolean initEconomyProviderVar(String plugin_name)
	{
		if(this.pm.getPlugin(plugin_name) != null)
		{
			this.econ_provider = this.sm.load(Economy.class);
			LOG.info(format_message("Hooked into " + plugin_name + " !"));
			return true;
		}
		else
		{
			LOG.severe(format_message("Could not find " + plugin_name + " !"));
			return false;
		}
	}
	
	//standard plug-in message formatter
	public String format_message(String s)
	{
		String name = this.getDescription().getName();
		return "["+name+"] " + s;
	}
	//easy message colorizing utility
	public String colorize(String s, char prefix)
	{
		char array[] = s.toCharArray();
		for(int i = 0; i < (array.length-1); ++i)
		{
			if(array[i] == prefix)
			{
				char c = array[i+1];
				ChatColor color;
				if((color = ChatColor.getByChar(c)) != null)
				{
					s = s.replaceAll(""+(prefix+c), ""+(color));
				}
			}
		}
		return s;
	}
}