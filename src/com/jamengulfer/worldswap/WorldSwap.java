package com.jamengulfer.worldswap;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldSwap extends JavaPlugin{
	
	World world2;
	private SwapUtils swaputils;
	
	public void log(String s){
		this.getLogger().log(Level.INFO, s);
	}
	
	public void onEnable(){
		
		saveDefaultConfig();
		WorldCreator otherworld = new WorldCreator("otherworld").environment(Environment.NORMAL).generateStructures(true).seed(0);
		world2 = Bukkit.createWorld(otherworld);
		swaputils  = new SwapUtils(this);
		
		log("WorldSwap has been enabled");
	}
	
	public void onDisable(){
		log("WorldSwap has been disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("tpme")){
			
			Location loc = player.getLocation();
			loc.setWorld(getUtils().getAlternateWorld(player));
			player.teleport(loc);
			
			return true;
		}
		
		if(cmd.getName().equalsIgnoreCase("worldswap")){
			if(args.length == 0){
				
				sender.sendMessage(ChatColor.YELLOW + "/worldswap start - Starts swapping");
				sender.sendMessage(ChatColor.YELLOW + "/worldswap stop - Stops swapping");
				return true;
			}
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("start")){
					sender.sendMessage(ChatColor.RED + "Commencing Swapping");
					getUtils().start();
					return true;
				}
				
				if(args[0].equalsIgnoreCase("stop")){
					sender.sendMessage(ChatColor.RED + "Ceasing Swapping");
					getUtils().stop();
					return true;
				}
			}
			
			if(args.length > 1){
				return false;
			}
		}
		
		return false;
	}
	
	public SwapUtils getUtils(){
		return swaputils;
	}
	
	public World getWorld(){
		return getServer().getWorlds().get(0);
	}
	
	public World getOtherWorld(){
		return world2;
	}
	
}
