package com.polemistis.realms;

public enum Permissions 
{
	CMD_HELP("realms.cmd.help"),
	CMD_CLAIM("realms.cmd.claim");
	
	private final String value;
	
	private Permissions(String value)
	{
		this.value = value;
	}
	public String getValue()
	{
		return this.value;
	}
}
