package com.jamengulfer.worldswap;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SwapUtils {
	
	WorldSwap plugin;
	public boolean isRunning = false;
	
	public SwapUtils(Plugin worldswap){
		plugin = (WorldSwap) worldswap;
	}
	
	public void start(){
		
		long delay = plugin.getConfig().getLong("swapwait");
		long period = plugin.getConfig().getLong("swapwait");
		this.isRunning = true;
		
		new SwapTask(this.plugin, 400).runTaskTimer(plugin, delay, period);
	}
	
	public void stop(){
		this.isRunning = false;
	}
	
	public boolean getIfRunning(){
		return isRunning;
	}
	
	public World getAlternateWorld(Player player){
		
		World current = player.getWorld();
		World def = plugin.getWorld();
		if(current.equals(def)){
			return plugin.getOtherWorld();
		} else {
			return plugin.getWorld();
		}
	}
	
}
