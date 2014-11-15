package com.polemistis.realms;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

final class PluginConfiguration
{
	private YamlConfiguration appProperties;
	private final Map<String, Object> defProperties;
	
	private File file;
	File getFile()
	{
		return this.file;
	}
	public void setProperty(String key, Object value)
	{
		try {
			this.appProperties.set(key, value);
			this.appProperties.save(this.file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	public Object getProperty(String path)
	{
		if(!appProperties.contains(path))
		{
			if(defProperties.containsKey(path))
			{
				this.setProperty(path, defProperties.get(path));
			}
			else {
				return null;
			}
		}
		return appProperties.get(path);
	}
	public Object getProperty(ConfigElement ce)
	{
		return getProperty(ce.getPath());
	}
	public void updateWithStream()
	{
		if(this.file.exists())
		{
			this.appProperties = YamlConfiguration.loadConfiguration(this.file);
		}
	}
	public Map<String, Object> getDefaults()
	{
		Map<String, Object> defcpy = new HashMap<String, Object>();
		Iterator<String> iter = this.defProperties.keySet().iterator();
		while(iter.hasNext())
		{
			String key = iter.next();
			Object obj = this.defProperties.get(key);
			defcpy.put(key, obj);
		}
		return defcpy;
	}
	@SuppressWarnings("deprecation")
	PluginConfiguration(File file)
	{
		this.file = file;
		
		//load default values
		this.defProperties = YamlConfiguration.loadConfiguration(Realms.plugin.getResource(Realms.DEFAULT_PROPERTIES_RESOURCE_FILE)).getValues(true);
		
		if(!Realms.plugin.getDataFolder().exists())
		{
			Realms.plugin.getDataFolder().mkdirs();
		}
		
		if(file.exists())
		{
			this.appProperties = YamlConfiguration.loadConfiguration(file);
		}
		else
		{
			this.appProperties = YamlConfiguration.loadConfiguration(Realms.plugin.getResource(Realms.DEFAULT_PROPERTIES_RESOURCE_FILE));
			
			try 
			{
				file.createNewFile();
				appProperties.save(file);
				
			} catch(IOException e) {
				e.printStackTrace();
			} finally {
				int status = file.exists() ? 0 : 1;
				if(status == 0) {
					Realms.LOG.info("generated " + Realms.DEFAULT_PROPERTIES_RESOURCE_FILE + " file!");
				}
				else {
					Realms.LOG.severe("failed to generate " + Realms.DEFAULT_PROPERTIES_RESOURCE_FILE + " file");
				}
			}
		}
	}
	public static enum ConfigElement
	{
		REALMSIZE_XDIAMETER("realm size.x-diameter", Integer.class),
		REALMSIZE_ZDIAMETER("realm size.z-diameter", Integer.class),
		REALMSIZE_YDIAMETER("realm size.y-diameter", Integer.class),
		REALMSIZE_YFILL("realm size.y-fill", Boolean.class);
		
		private String path;
		private Class<?> type;
		
		private ConfigElement(String path, Class<?> type)
		{
			this.path = path;
			this.type = type;
		}
		public String getPath()
		{
			return this.path;
		}
		public Class<?> getType()
		{
			return this.type;
		}
	}
}
