package com.polemistis.realms.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RealmClaimEvent extends Event implements Cancellable
{
	private boolean cancelled;
	
	@Override
	public boolean isCancelled() 
	{
		return false;
	}
	@Override
	public void setCancelled(boolean arg0)
	{
		
	}
	@Override
	public HandlerList getHandlers() 
	{
		return null;
	}

}
