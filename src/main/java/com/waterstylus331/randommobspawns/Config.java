package com.waterstylus331.randommobspawns;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = MobSpawns.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue INTERVAL = BUILDER
            .comment("Interval (in seconds) between mob spawns. (in integer only)")
            .defineInRange("interval", 45, 3, 300);

    private static final ForgeConfigSpec.IntValue INITIAL_INTERVAL = BUILDER
            .comment("Initial head start amount (in seconds) before mobs start spawning. (in integer only)")
            .defineInRange("initialDelay", 30, 10, 600);

    private static final ForgeConfigSpec.BooleanValue ENABLE_MOBS_SPAWN = BUILDER
            .comment("Enable/Disable random mobs spawning at random times.")
            .define("mobSpawnsEnabled", false);

    private static final ForgeConfigSpec.BooleanValue WARDEN = BUILDER
            .comment("")
            .comment("From top to bottom, least occurring to most occurring.")
            .comment("Enable/Disable Warden spawning.")
            .define("wardenSpawning", true);

    private static final ForgeConfigSpec.BooleanValue WITHER = BUILDER
            .comment("Enable/Disable Wither spawning.")
            .define("witherSpawning", true);

    private static final ForgeConfigSpec.BooleanValue SHULKER = BUILDER
            .comment("Enable/Disable Shulker spawning.")
            .define("shulkerSpawning", true);

    private static final ForgeConfigSpec.BooleanValue ENDERMITE = BUILDER
            .comment("Enable/Disable Endermite spawning.")
            .define("endermiteSpawning", true);

    private static final ForgeConfigSpec.BooleanValue PIGLINBRUTE = BUILDER
            .comment("Enable/Disable Piglin brute spawning.")
            .define("piglinBruteSpawning", true);

    private static final ForgeConfigSpec.BooleanValue HOGLIN = BUILDER
            .comment("Enable/Disable Hoglin spawning.")
            .define("hoglinSpawning", true);

    private static final ForgeConfigSpec.BooleanValue CHARGEDCREEPER = BUILDER
            .comment("Enable/Disable Charged creeper spawning.")
            .define("chargedCreeperSpawning", true);

    private static final ForgeConfigSpec.BooleanValue GHAST = BUILDER
            .comment("Enable/Disable Ghast spawning.")
            .define("ghastSpawning", true);

    private static final ForgeConfigSpec.BooleanValue MAGMACUBE = BUILDER
            .comment("Enable/Disable Magma Cube spawning.")
            .define("magmaCubeSpawning", true);

    private static final ForgeConfigSpec.BooleanValue WITHERSKELETON = BUILDER
            .comment("Enable/Disable Wither skeleton spawning.")
            .define("witherSkeletonSpawning", true);

    private static final ForgeConfigSpec.BooleanValue BLAZE = BUILDER
            .comment("Enable/Disable Blaze spawning.")
            .define("blazeSpawning", true);

    private static final ForgeConfigSpec.BooleanValue BABYZOMBIE = BUILDER
            .comment("Enable/Disable Baby zombie spawning.")
            .define("babyZombieSpawning", true);

    private static final ForgeConfigSpec.BooleanValue PHANTOM = BUILDER
            .comment("Enable/Disable Phantom spawning.")
            .define("phantomSpawning", true);

    private static final ForgeConfigSpec.BooleanValue WITCH = BUILDER
            .comment("Enable/Disable Witch spawning.")
            .define("witchSpawning", true);

    private static final ForgeConfigSpec.BooleanValue CREEPER = BUILDER
            .comment("Enable/Disable Creeper spawning.")
            .define("creeperSpawning", true);

    private static final ForgeConfigSpec.BooleanValue STRAY = BUILDER
            .comment("Enable/Disable Stray spawning.")
            .define("straySpawning", true);

    private static final ForgeConfigSpec.BooleanValue SKELETON = BUILDER
            .comment("Enable/Disable Skeleton spawning.")
            .define("skeletonSpawning", true);

    private static final ForgeConfigSpec.BooleanValue SLIME = BUILDER
            .comment("Enable/Disable Slime spawning.")
            .define("slimeSpawning", true);

    private static final ForgeConfigSpec.BooleanValue HUSK = BUILDER
            .comment("Enable/Disable Husk spawning.")
            .define("huskSpawning", true);

    private static final ForgeConfigSpec.BooleanValue ZOMBIE = BUILDER
            .comment("Enable/Disable Zombie spawning.")
            .define("zombieSpawning", true);

    private static final ForgeConfigSpec.BooleanValue CHICKEN = BUILDER
            .comment("Enable/Disable Chicken spawning.")
            .define("chickenSpawning", true);

    private static final ForgeConfigSpec.BooleanValue PIG = BUILDER
            .comment("Enable/Disable Pig spawning.")
            .define("pigSpawning", true);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int intervalMobsSpawn;
    public static boolean mobSpawnEnabled;
    public static int initialDelayHeadstart;

    public static boolean wardenEnabled;
    public static boolean witherEnabled;
    public static boolean shulkerEnabled;
    public static boolean endermiteEnabled;
    public static boolean bruteEnabled;
    public static boolean hoglinEnabled;
    public static boolean chargedCreeperEnabled;
    public static boolean ghastEnabled;
    public static boolean magmaCubeEnabled;
    public static boolean witherskellyEnabled;
    public static boolean blazeEnabled;
    public static boolean babyzombieEnabled;
    public static boolean phantomEnabled;
    public static boolean witchEnabled;
    public static boolean creeperEnabled;
    public static boolean strayEnabled;
    public static boolean skeletonEnabled;
    public static boolean slimeEnabled;
    public static boolean huskEnabled;
    public static boolean zombieEnabled;
    public static boolean chickenEnabled;
    public static boolean pigEnabled;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        mobSpawnEnabled = ENABLE_MOBS_SPAWN.get();
        intervalMobsSpawn = INTERVAL.get();
        initialDelayHeadstart = INITIAL_INTERVAL.get();

        wardenEnabled = WARDEN.get();
        witherEnabled = WITHER.get();
        shulkerEnabled = SHULKER.get();
        endermiteEnabled = ENDERMITE.get();
        bruteEnabled = PIGLINBRUTE.get();
        hoglinEnabled = HOGLIN.get();
        chargedCreeperEnabled = CHARGEDCREEPER.get();
        ghastEnabled = GHAST.get();
        magmaCubeEnabled = MAGMACUBE.get();
        witherskellyEnabled = WITHERSKELETON.get();
        blazeEnabled = BLAZE.get();
        babyzombieEnabled = BABYZOMBIE.get();
        phantomEnabled = PHANTOM.get();
        witchEnabled = WITCH.get();
        creeperEnabled = CREEPER.get();
        strayEnabled = STRAY.get();
        skeletonEnabled = SKELETON.get();
        slimeEnabled = SLIME.get();
        huskEnabled = HUSK.get();
        zombieEnabled = ZOMBIE.get();
        chickenEnabled = CHICKEN.get();
        pigEnabled = PIG.get();

        ModEventHandler.updatePermissions();
        ModEventHandler.updateInterval();
    }
}
