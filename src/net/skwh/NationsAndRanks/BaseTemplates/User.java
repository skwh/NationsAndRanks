package net.skwh.NationsAndRanks.BaseTemplates;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.skwh.NationsAndRanks.Core;

import org.bukkit.Achievement;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.InventoryView.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
/**
 * A class used to represent a player, with additional methods and properties. 
 * @author Evan Derby <somekidwithhtml@gmail.com>
 * @version 0.8
 * @since 2012-11-29
 */
public class User implements Player {
	/**
	 * Adds the given user to the user list in the given {@link Core}.
	 * @param basePlugin {@link Core}
	 * @param u {@link User}
	 */
	public static void addToUserList(Core basePlugin, User u) {
		basePlugin.getUserList().put(u.p, u);
	}
	/**
	 * Creates a new user with the attributes given and returns it.
	 * @param p {@link Player}
	 * @param n {@link Nation}
	 * @param g {@link Guild}
	 * @param r {@link Rank}
	 * @return {@link User}
	 */
	public static User makeUserWithAttributes(Player p,Nation n, Guild g, Rank r) {
		if (p == null) {
			return null;
		}
		User u = new User(p);
		if (n != null) {
			u.setNation(true, n);
			if (g != null) {
				u.setGuild(true, g);
				if (r != null) {
					u.setCurrentRank(r);
				}
			}
		}
		return u;
	}
	
	/**
	 * The Player that this user references.
	 */
	private Player p;
	/**
	 * Whether or not this user belongs to a {@link Nation}.
	 */
	private boolean belongsToNation = false;
	/**
	 * The {@link Nation} that this user might belong to.
	 */
	private Nation ownerNation = null;
	
	/**
	 * Whether or not this user belongs to a {@link Guild}.
	 */
	private boolean belongsToGuild = false;
	/**
	 * The {@link Guild} that this player might belong to.
	 */
	private Guild ownerGuild = null;
	
	/**
	 * The {@link Rank} that this player might have.
	 */
	private Rank currentRank = null;
	
	/**
	 * Returns the {@link #currentRank}.
	 * @return {@link Rank}
	 */
	public Rank getCurrentRank() {
		return currentRank;
	}
	/**
	 * Sets the current rank, and sets the player to that rank in the guild's player-rank list. ({@link Guild#getPlayerRankList()}).
	 * @param r {@link Rank}
	 */
	public void setCurrentRank(Rank r) {
		currentRank = r;
		ownerGuild.setPlayerToRank(this.p,r);
	}
	
	/**
	 * Returns if the player belongs to a nation.
	 * @return {@link Boolean}
	 */
	public boolean doesBelongToNation() {
		return belongsToNation;
	}
	/**
	 * Returns the nation object that this player might belong to.
	 * @return {@link Nation}
	 */
	public Nation getNation() {
		return ownerNation;
	}
	/**
	 * Sets the nation that the player belongs to, and changes {@link #belongsToNation} correspondingly.
	 * @param b {@link Boolean}
	 * @param n {@link Nation}
	 */
	public void setNation(Boolean b,Nation n) {
		belongsToNation = b;
		ownerNation = n;
	}
	
	/**
	 * Returns if the player belongs to a guild.
	 * @return {@link Boolean}
	 */
	public boolean doesBelongToGuild() {
		return belongsToGuild;
	}
	/**
	 * Returns the guild that the player might belong to.
	 * @return {@link Guild}
	 */
	public Guild getGuild() {
		return ownerGuild;
	}
	/**
	 * Sets the guild that the player belongs to, and changes {@link #belongsToGuild} at the same time.
	 * @param b {@link Boolean}
	 * @param g {@link Guild}
	 */
	public void setGuild(Boolean b, Guild g) {
		belongsToGuild = b;
		ownerGuild = g;
	}
	
	/**
	 * Returns the player that this user references.
	 */
	public Player getPlayer() {
		return p;
	}
	
	/**
	 * Basic constructor.
	 * @param p {@link Player}
	 */
	public User(Player p) {
		this.p = p;
	}
	
	public void closeInventory() {
		p.closeInventory();
		
	}
	public Inventory getEnderChest() {
		return p.getEnderChest();
	}
	public int getExpToLevel() {
		return p.getExpToLevel();
	}
	public GameMode getGameMode() {
		return p.getGameMode();
	}
	
	public PlayerInventory getInventory() {
		return p.getInventory();
	}
	
	public ItemStack getItemInHand() {
		return p.getItemInHand();
	}
	
	public ItemStack getItemOnCursor() {
		return p.getItemOnCursor();
	}
	
	public String getName() {
		return p.getName();
	}
	
	public InventoryView getOpenInventory() {
		return p.getOpenInventory();
	}
	
	public int getSleepTicks() {
		return p.getSleepTicks();
	}
	
	public boolean isBlocking() {
		return p.isBlocking();
	}
	
	public boolean isSleeping() {
		return p.isSleeping();
	}
	
	public InventoryView openEnchanting(Location arg0, boolean arg1) {
		return p.openEnchanting(arg0, arg1);
	}
	
	public InventoryView openInventory(Inventory arg0) {
		return p.openInventory(arg0);
	}
	
	public void openInventory(InventoryView arg0) {
		p.openInventory(arg0);
	}
	
	public InventoryView openWorkbench(Location arg0, boolean arg1) {
		return p.openWorkbench(arg0,arg1);
	}
	
	public void setGameMode(GameMode arg0) {
		p.setGameMode(arg0);
	}
	
	public void setItemInHand(ItemStack arg0) {
		p.setItemInHand(arg0);
	}
	
	public void setItemOnCursor(ItemStack arg0) {
		p.setItemOnCursor(arg0);
	}
	@Override
	public boolean setWindowProperty(Property arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addPotionEffect(PotionEffect arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addPotionEffect(PotionEffect arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addPotionEffects(Collection<PotionEffect> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void damage(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void damage(int arg0, Entity arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Collection<PotionEffect> getActivePotionEffects() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double getEyeHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getEyeHeight(boolean arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Location getEyeLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Player getKiller() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getLastDamage() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Block> getLastTwoTargetBlocks(HashSet<Byte> arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Block> getLineOfSight(HashSet<Byte> arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getMaximumAir() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getMaximumNoDamageTicks() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getNoDamageTicks() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getRemainingAir() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Block getTargetBlock(HashSet<Byte> arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasLineOfSight(Entity arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasPotionEffect(PotionEffectType arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public <T extends Projectile> T launchProjectile(Class<? extends T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void removePotionEffect(PotionEffectType arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setHealth(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastDamage(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setMaximumAir(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setMaximumNoDamageTicks(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setNoDamageTicks(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setRemainingAir(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Arrow shootArrow() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Egg throwEgg() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Snowball throwSnowball() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean eject() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getEntityId() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getFallDistance() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public EntityDamageEvent getLastDamageCause() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getMaxFireTicks() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<Entity> getNearbyEntities(double arg0, double arg1, double arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Entity getPassenger() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Server getServer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getTicksLived() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public EntityType getType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UUID getUniqueId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Entity getVehicle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isInsideVehicle() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean leaveVehicle() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void playEffect(EntityEffect arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFallDistance(float arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFireTicks(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastDamageCause(EntityDamageEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean setPassenger(Entity arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setTicksLived(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setVelocity(Vector arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean teleport(Location arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean teleport(Entity arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean teleport(Location arg0, TeleportCause arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean teleport(Entity arg0, TeleportCause arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<MetadataValue> getMetadata(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasMetadata(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void removeMetadata(String arg0, Plugin arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setMetadata(String arg0, MetadataValue arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public PermissionAttachment addAttachment(Plugin arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PermissionAttachment addAttachment(Plugin arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public PermissionAttachment addAttachment(Plugin arg0, String arg1,
			boolean arg2, int arg3) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasPermission(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean hasPermission(Permission arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isPermissionSet(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isPermissionSet(Permission arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void recalculatePermissions() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeAttachment(PermissionAttachment arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isOp() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setOp(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void abandonConversation(Conversation arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void abandonConversation(Conversation arg0,
			ConversationAbandonedEvent arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void acceptConversationInput(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean beginConversation(Conversation arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isConversing() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void sendMessage(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendMessage(String[] arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public long getFirstPlayed() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long getLastPlayed() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean hasPlayedBefore() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isBanned() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isOnline() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isWhitelisted() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setBanned(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setWhitelisted(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<String> getListeningPluginChannels() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void sendPluginMessage(Plugin arg0, String arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void awardAchievement(Achievement arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean canSee(Player arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void chat(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public InetSocketAddress getAddress() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean getAllowFlight() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Location getBedSpawnLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Location getCompassTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public float getExhaustion() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getExp() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getFlySpeed() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getFoodLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getPlayerListName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getPlayerTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public long getPlayerTimeOffset() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getSaturation() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getTotalExperience() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public float getWalkSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void giveExp(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void giveExpLevels(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hidePlayer(Player arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void incrementStatistic(Statistic arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void incrementStatistic(Statistic arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void incrementStatistic(Statistic arg0, Material arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void incrementStatistic(Statistic arg0, Material arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isFlying() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isPlayerTimeRelative() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSleepingIgnored() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSneaking() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSprinting() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void kickPlayer(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void loadData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean performCommand(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void playEffect(Location arg0, Effect arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public <T> void playEffect(Location arg0, Effect arg1, T arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void playNote(Location arg0, byte arg1, byte arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void playNote(Location arg0, Instrument arg1, Note arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void playSound(Location arg0, Sound arg1, float arg2, float arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resetPlayerTime() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void saveData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendBlockChange(Location arg0, Material arg1, byte arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendBlockChange(Location arg0, int arg1, byte arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean sendChunkChange(Location arg0, int arg1, int arg2, int arg3,
			byte[] arg4) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void sendMap(MapView arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendRawMessage(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAllowFlight(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setBedSpawnLocation(Location arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setBedSpawnLocation(Location arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCompassTarget(Location arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setDisplayName(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setExhaustion(float arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setExp(float arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFlySpeed(float arg0) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFlying(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFoodLevel(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLevel(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPlayerListName(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPlayerTime(long arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSaturation(float arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSleepingIgnored(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSneaking(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSprinting(boolean arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setTotalExperience(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setWalkSpeed(float arg0) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void showPlayer(Player arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateInventory() {
		// TODO Auto-generated method stub
		
	}
}