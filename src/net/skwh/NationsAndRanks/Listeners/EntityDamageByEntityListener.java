package net.skwh.NationsAndRanks.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.skwh.NationsAndRanks.Core;
import net.skwh.NationsAndRanks.Utilites.JamesBond;

public class EntityDamageByEntityListener extends Core implements Listener {
	private static final JamesBond DoubleOhSeven = new JamesBond();
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		Entity damaged = e.getEntity();
		Entity damager = e.getDamager();
		if (damaged.getType() == EntityType.PLAYER && damager.getType() == EntityType.PLAYER) { 
			Player dd = (Player) damaged;
			Player dr = (Player) damager;
			getBaseCore().log("Player " + dr.getDisplayName() + " damaged " + dd.getDisplayName());
			if (DoubleOhSeven.getNationForPlayer(dd.getName()) == DoubleOhSeven.getNationForPlayer(dr.getName())) {
				e.setCancelled(true);
			}
		}
	}
}
