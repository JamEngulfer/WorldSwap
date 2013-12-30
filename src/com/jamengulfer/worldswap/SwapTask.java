package com.jamengulfer.worldswap;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SwapTask extends BukkitRunnable{

	WorldSwap plugin;

	public SwapTask(JavaPlugin plugin, int time){
		this.plugin = (WorldSwap) plugin;
	}

	@Override
	public void run() {

		if(!plugin.getUtils().getIfRunning()){this.cancel(); return;}

		for(Player player: plugin.getServer().getOnlinePlayers()) {

			if(!player.hasPermission("worldswap.exclude")){

				Location ploc = player.getLocation();

				if(plugin.getConfig().getBoolean("preload")){

					int loadRadius = plugin.getConfig().getInt("chunkradius");


					Location loading = ploc;
					loading.setWorld(plugin.getUtils().getAlternateWorld(player));
					int lX = loading.getBlockX();
					int lZ = loading.getBlockZ();

					int cX = lX>>4;
					int cZ = lZ>>4;

					for(int i = (cX - loadRadius); i < (cX + loadRadius); i++){
						for(int j = (cZ - loadRadius); j < (cZ + loadRadius); j++){
							Chunk chunk = loading.getWorld().getChunkAt(cX, cZ);
							chunk.load();

						}
					}
				}

				Location loc = new Location(plugin.getUtils().getAlternateWorld(player), ploc.getX(), ploc.getY(), ploc.getZ());


				loc.setYaw(ploc.getYaw());
				loc.setPitch(ploc.getPitch());
				player.teleport(loc);

			}

		}

	}



}
