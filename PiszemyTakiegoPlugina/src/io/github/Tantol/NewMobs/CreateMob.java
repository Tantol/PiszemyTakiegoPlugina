package io.github.Tantol.NewMobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;


import io.github.Tantol.NewMobs.CustomEntity.*;
import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityBat;
import net.minecraft.server.v1_11_R1.EntityBlaze;
import net.minecraft.server.v1_11_R1.EntityCaveSpider;
import net.minecraft.server.v1_11_R1.EntityChicken;
import net.minecraft.server.v1_11_R1.EntityCow;
import net.minecraft.server.v1_11_R1.EntityCreature;
import net.minecraft.server.v1_11_R1.EntityCreeper;
import net.minecraft.server.v1_11_R1.EntityEnderDragon;
import net.minecraft.server.v1_11_R1.EntityEnderman;
import net.minecraft.server.v1_11_R1.EntityEndermite;
import net.minecraft.server.v1_11_R1.EntityEvoker;
import net.minecraft.server.v1_11_R1.EntityGhast;
import net.minecraft.server.v1_11_R1.EntityGuardian;
import net.minecraft.server.v1_11_R1.EntityGuardianElder;
import net.minecraft.server.v1_11_R1.EntityHorse;
import net.minecraft.server.v1_11_R1.EntityHorseDonkey;
import net.minecraft.server.v1_11_R1.EntityHorseMule;
import net.minecraft.server.v1_11_R1.EntityHorseSkeleton;
import net.minecraft.server.v1_11_R1.EntityHorseZombie;
import net.minecraft.server.v1_11_R1.EntityIronGolem;
import net.minecraft.server.v1_11_R1.EntityLiving;
import net.minecraft.server.v1_11_R1.EntityLlama;
import net.minecraft.server.v1_11_R1.EntityMagmaCube;
import net.minecraft.server.v1_11_R1.EntityOcelot;
import net.minecraft.server.v1_11_R1.EntityPig;
import net.minecraft.server.v1_11_R1.EntityPigZombie;
import net.minecraft.server.v1_11_R1.EntityPolarBear;
import net.minecraft.server.v1_11_R1.EntityRabbit;
import net.minecraft.server.v1_11_R1.EntitySheep;
import net.minecraft.server.v1_11_R1.EntityShulker;
import net.minecraft.server.v1_11_R1.EntitySilverfish;
import net.minecraft.server.v1_11_R1.EntitySkeleton;
import net.minecraft.server.v1_11_R1.EntitySkeletonStray;
import net.minecraft.server.v1_11_R1.EntitySkeletonWither;
import net.minecraft.server.v1_11_R1.EntitySlime;
import net.minecraft.server.v1_11_R1.EntitySnowman;
import net.minecraft.server.v1_11_R1.EntitySpider;
import net.minecraft.server.v1_11_R1.EntitySquid;
import net.minecraft.server.v1_11_R1.EntityTypes;
import net.minecraft.server.v1_11_R1.EntityVex;
import net.minecraft.server.v1_11_R1.EntityVillager;
import net.minecraft.server.v1_11_R1.EntityVindicator;
import net.minecraft.server.v1_11_R1.EntityWitch;
import net.minecraft.server.v1_11_R1.EntityWither;
import net.minecraft.server.v1_11_R1.EntityWolf;
import net.minecraft.server.v1_11_R1.EntityZombie;
import net.minecraft.server.v1_11_R1.EntityZombieHusk;
import net.minecraft.server.v1_11_R1.EntityZombieVillager;
import net.minecraft.server.v1_11_R1.EnumItemSlot;
import net.minecraft.server.v1_11_R1.MinecraftKey;
import net.minecraft.server.v1_11_R1.WorldServer;

public class CreateMob extends EntityCreature {
	static String name;
	static String type;
	static EntityLiving oldMobClass = null;
	static EntityLiving newMobClass = null;
	static WorldServer world;
	static Location loc;
	static ItemStack cp;
	static net.minecraft.server.v1_11_R1.ItemStack item = null;
	static boolean visable = false;
	static int entityId;

	// private final BiMap<MinecraftKey, EntityLiving> customEntities =
	// HashBiMap.create();
	// private final BiMap<EntityLiving, MinecraftKey> customEntityClasses =
	// this.customEntities.inverse();
	private final static Map<Integer, Class<? extends Entity>> onEggAndSummonWithOldMobClass = new HashMap<>();
	private final static List<Integer> onEggAndSummon = new ArrayList<Integer>();
	private final static List<String> worldAddEntity = new ArrayList<String>();
	private final String types[] = { "Cave Spider", "Enderman", "Polar Bear", "Spider", "Zombie Pigman", "Blaze",
			"Creeper", "Elder Guardian", "Endermite", "Evoker", "Ghast", "Guardian", "Zombie Husk", "Magma Cube",
			"Shulker", "Silverfish", "Skeleton", "Slime", "Skeleton Stray", "Vex", "Vindicator", "Witch",
			"Skeleton Wither", "Zombie", "Zombie Villager", "Horse Donkey", "Horse", "Llama", "Mule", "Ocelot", "Wolf",
			"Iron Golem", "Snowman", "Ender Dragon", "Wither", "Horse Zombie", "Bat", "Chicken", "Cow", "Pig", "Rabbit",
			"Sheep", "Horse Skeleton", "Squid", "Villager" };
	private final int typesId[] = { 59, 58, 102, 52, 57, 61, 50, 4, 67, 34, 56, 68, 23, 62, 69, 60, 51, 55, 6, 35, 36,
			66, 5, 54, 27, 31, 100, 103, 32, 98, 95, 99, 97, 63, 64, 29, 65, 93, 92, 90, 101, 91, 28, 94, 120 };
	// blaze 61
	// private final BiMap<String, Class<? extends Entity>> nowa =
	// HashBiMap.create();

	public CreateMob(String type, String name, boolean visable, WorldServer world, Location loc) {
		super(world);
		CreateMob.type = type;
		CreateMob.name = name;
		CreateMob.world = world;
		CreateMob.loc = loc;
		ItemStack cp = new ItemStack(Material.GOLD_CHESTPLATE);
		CreateMob.item = CraftItemStack.asNMSCopy(cp);
		CreateMob.visable = visable;
		if (types.length != typesId.length) {
			System.out.print("Length of available types is not similar with length of their id");
			return;
		}
		for (int i = 0; i < types.length; i++) {
			if (CreateMob.type.equals(types[i])) {
				CreateMob.entityId = typesId[i];
			}
		}

		///////////////////////////////////////////
		// Neutral mobs ///////////////////////////
		///////////////////////////////////////////
		if (type.equals("Cave Spider")) {
			newMobClass = new CustomCaveSpider(world);
			oldMobClass = new EntityCaveSpider(world);
		} else if (type.equals("Enderman")) {
			newMobClass = new CustomEnderman(world);
			oldMobClass = new EntityEnderman(world);
		} else if (type.equals("Polar Bear")) {
			newMobClass = new CustomPolarBear(world);
			oldMobClass = new EntityPolarBear(world);
		} else if (type.equals("Spider")) {
			newMobClass = new CustomSpider(world);
			oldMobClass = new EntitySpider(world);
		} else if (type.equals("Pig Zombie")) {
			newMobClass = new CustomPigZombie(world);
			oldMobClass = new EntityPigZombie(world);
		}
		///////////////////////////////////////////
		// Hostile oldMobClasss ///////////////////////////
		// Need Chicken Jockey;
		// Need Skeleton Horseman;
		// Need Spider Jockey;
		///////////////////////////////////////////
		else if (type.equals("Blaze")) {
			oldMobClass = new EntityBlaze(world);
		} else if (type.equals("Creeper")) {
			oldMobClass = new EntityCreeper(world);
		} else if (type.equals("Guardian Elder")) {
			oldMobClass = new EntityGuardianElder(world);
		} else if (type.equals("Endermite")) {
			oldMobClass = new EntityEndermite(world);
		} else if (type.equals("Evoker")) {
			oldMobClass = new EntityEvoker(world);
		} else if (type.equals("Ghast")) {
			oldMobClass = new EntityGhast(world);
		} else if (type.equals("Guardian")) {
			oldMobClass = new EntityGuardian(world);
		} else if (type.equals("Zombie Husk")) {
			oldMobClass = new EntityZombieHusk(world);
		} else if (type.equals("Magma Cube")) {
			oldMobClass = new EntityMagmaCube(world);
		} else if (type.equals("Shulker")) {
			oldMobClass = new EntityShulker(world);
		} else if (type.equals("Silverfish")) {
			oldMobClass = new EntitySilverfish(world);
		} else if (type.equals("Skeleton")) {
			oldMobClass = new EntitySkeleton(world);
			newMobClass = new CustomSkeleton(world);
		} else if (type.equals("Slime")) {
			oldMobClass = new EntitySlime(world);
		} else if (type.equals("Skeleton Stray")) {
			oldMobClass = new EntitySkeletonStray(world);
		} else if (type.equals("Vex")) {
			oldMobClass = new EntityVex(world);
		} else if (type.equals("Vindicator")) {
			oldMobClass = new EntityVindicator(world);
		} else if (type.equals("Witch")) {
			oldMobClass = new EntityWitch(world);
		} else if (type.equals("Skeleton Wither")) {
			oldMobClass = new EntitySkeletonWither(world);
		} else if (type.equals("Zombie")) {
			newMobClass = new CustomZombie(world);
			oldMobClass = new EntityZombie(world);
		} else if (type.equals("Zombie Villager")) {
			oldMobClass = new EntityZombieVillager(world);
		}
		///////////////////////////////////////////
		// Tameable oldMobClasss ///////////////////////////
		///////////////////////////////////////////
		else if (type.equals("Horse Donkey")) {
			oldMobClass = new EntityHorseDonkey(world);
		} else if (type.equals("Horse")) {
			oldMobClass = new EntityHorse(world);
		} else if (type.equals("Llama")) {
			oldMobClass = new EntityLlama(world);
		} else if (type.equals("Mule")) {
			oldMobClass = new EntityHorseMule(world);
		} else if (type.equals("Ocelot")) {
			oldMobClass = new EntityOcelot(world);
		} else if (type.equals("Wolf")) {
			oldMobClass = new EntityWolf(world);
		}
		///////////////////////////////////////////
		// Utility oldMobClasss ///////////////////////////
		///////////////////////////////////////////
		else if (type.equals("Iron Golem")) {
			oldMobClass = new EntityIronGolem(world);
		} else if (type.equals("Snowman")) {
			oldMobClass = new EntitySnowman(world);
		}
		///////////////////////////////////////////
		// Boss oldMobClasss ///////////////////////////
		///////////////////////////////////////////
		else if (type.equals("Ender Dragon")) {
			oldMobClass = new EntityEnderDragon(world);
		} else if (type.equals("Wither")) {
			oldMobClass = new EntityWither(world);
		}
		///////////////////////////////////////////
		// Unused oldMobClasss ///////////////////////////
		// Need Giant;
		///////////////////////////////////////////
		else if (type.equals("Horse Zombie")) {
			oldMobClass = new EntityHorseZombie(world);
		}
		///////////////////////////////////////////
		// Passive oldMobClasss ///////////////////////////
		///////////////////////////////////////////
		else if (type.equals("Bat")) {
			oldMobClass = new EntityBat(world);
		} else if (type.equals("Chicken")) {
			oldMobClass = new EntityChicken(world);
		} else if (type.equals("Cow")) {
			oldMobClass = new EntityCow(world);
		} else if (type.equals("Pig")) {
			oldMobClass = new EntityPig(world);
		} else if (type.equals("Rabbit")) {
			oldMobClass = new EntityRabbit(world);
		} else if (type.equals("Sheep")) {
			oldMobClass = new EntitySheep(world);
		} else if (type.equals("Horse Skeleton")) {
			oldMobClass = new EntityHorseSkeleton(world);
		} else if (type.equals("Squid")) {
			oldMobClass = new EntitySquid(world);
		} else if (type.equals("Villager")) {
			oldMobClass = new EntityVillager(world);
		}
		///////////////////////////////////////////
		// Construct oldMobClass ///////////////////////////
		///////////////////////////////////////////
	}
	public String toString(){
		return "EntityType-> "+type+". Name-> "+name+". World-> "+world+".";
	}
	
	public void spawnMob(Location location){
		try {
			if (!location.getChunk().isLoaded()) location.getChunk().load();
			newMobClass.setPosition(location.getX(), location.getY(), location.getZ());
			(((CraftWorld) location.getWorld()).getHandle()).addEntity(newMobClass, SpawnReason.CUSTOM);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////
	/// Add all atribiuts ////////////////////////////
	////////////////////////////////////////////
	public static void addAtributess(EntityLiving customEntity) {
		try{
		arrmor(customEntity, item);}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Cant add arrmor to Entity"+type);
		}
		try{
		name(customEntity, visable);}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Cant add name to Entity"+type);
		}
	}

	////////////////////////////////////////////
	/// Editing Mob ////////////////////////////
	////////////////////////////////////////////
	public static void arrmor(EntityLiving customEntity, net.minecraft.server.v1_11_R1.ItemStack item) {
		customEntity.setSlot(EnumItemSlot.CHEST, item);
	}

	public static void name(EntityLiving customEntity, boolean visable) {
		customEntity.setCustomName(name);
		customEntity.setCustomNameVisible(visable);
	}

	public static void setName(String name) {
		CreateMob.name = name;
	}

	////////////////////////////////////////////
	/// Add / remove mob on eggSummon and to world////////////////////////////
	////////////////////////////////////////////
	//add on EggSummon
	public void addCustomEntity() {
		MinecraftKey minecraftKey = new MinecraftKey(type);
		EntityTypes.b.a(entityId, minecraftKey, newMobClass.getClass());
	}
	//add to world (can be used from command)
	public void worldAddEntity() {
		world.addEntity(newMobClass,SpawnReason.CUSTOM);
	}
	public void removeWorldAddEntity(){
		world.removeEntity(newMobClass);
	}
	public void removeCustomEntity(){
		MinecraftKey minecraftKey = new MinecraftKey(type);
		EntityTypes.b.a(entityId, minecraftKey, oldMobClass.getClass());
	}
	
	////////////////////////////////////////////
	/// Get / set from basic info////////////////////////////
	////////////////////////////////////////////

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public int getId() {
		return entityId;
	}

	public WorldServer getWorld() {
		return world;
	}

	public EntityLiving getOldMobClass() {
		return oldMobClass;
	}

	public EntityLiving getNewMobClass() {
		return newMobClass;
	}
}
// this.nowa.put("nazwa",EntitySkeleton.class);
// MinecraftKey minecraftKey = new MinecraftKey(type + name);
// this.customEntities.put(minecraftKey, mob);
// this.customEntityIds.put(mob, mob.getId());
// addCustomEntity(51, type.toLowerCase(), mob.getClass());
// world.addEntity(mob);
// mob.setLocation(loc.getX(),loc.getY() , loc.getZ(), loc.getPitch(),
// loc.getYaw());
// world.addEntity(mob, SpawnReason.SPAWNER_EGG);
// EntityRegistry.overrideEntity(CustomClass.class);
// addCustomEntity(51,type.toLowerCase(),CustomSkeleton.class);