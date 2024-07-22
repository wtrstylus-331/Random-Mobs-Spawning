package com.waterstylus331.randommobspawns;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.breeze.Breeze;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = MobSpawns.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventHandler {
    private static final int TICKS_PER_SECOND = 20;
    private static int regularDelay = 20 * Config.intervalMobsSpawn;
    private static int initialDelayConfig = Config.initialDelayHeadstart;
    private static int initialDelay = TICKS_PER_SECOND * initialDelayConfig;
    private static int initialCloserAlert = initialDelay - (TICKS_PER_SECOND * 5);
    private static int tickCounter = 0;

    private static boolean initialDelayApplied = false;
    private static boolean sentInitialMessage = false;
    private static boolean sentCloserAlert = false;
    private static boolean ismodEnabled;
    private static boolean sendAlertConfig = false;

    private static boolean PLAYER_IN_SERVER = false;

    private static boolean wardenEnabled;
    private static boolean witherEnabled;
    private static boolean shulkerEnabled;
    private static boolean endermiteEnabled;
    private static boolean bruteEnabled;
    private static boolean hoglinEnabled;
    private static boolean breezeEnabled;
    private static boolean chargedCreeperEnabled;
    private static boolean ghastEnabled;
    private static boolean magmaCubeEnabled;
    private static boolean witherskellyEnabled;
    private static boolean blazeEnabled;
    private static boolean boggedEnabled;
    private static boolean babyzombieEnabled;
    private static boolean phantomEnabled;
    private static boolean witchEnabled;
    private static boolean creeperEnabled;
    private static boolean strayEnabled;
    private static boolean skeletonEnabled;
    private static boolean slimeEnabled;
    private static boolean huskEnabled;
    private static boolean zombieEnabled;
    private static boolean chickenEnabled;
    private static boolean pigEnabled;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (ismodEnabled) {
                if (PLAYER_IN_SERVER) {
                    tickCounter++;

                    if (!initialDelayApplied) {
                        if (tickCounter >= initialDelay) {
                            tickCounter = 0;
                            initialDelayApplied = true;
                        }
                    } else {
                        if (!sentCloserAlert) {
                            if (tickCounter >= initialCloserAlert) {
                                tickCounter = 0;
                                sentCloserAlert = true;
                                closerAlert();
                            }
                        } else {
                            if (tickCounter >= regularDelay) {
                                tickCounter = 0;

                                if (Config.mobSpawnEnabled) {
                                    spawnMobs();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void left(PlayerEvent.PlayerLoggedOutEvent event) {
        if (ismodEnabled) {
            List<ServerPlayer> players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();

            if (players.isEmpty()) {
                sentInitialMessage = false;
                sentCloserAlert = false;
                PLAYER_IN_SERVER = false;
            }
        }
    }

    @SubscribeEvent
    public static void joined(PlayerEvent.PlayerLoggedInEvent event) {
        if (ismodEnabled) {
            if (!event.getEntity().isDeadOrDying()) {
                if (!PLAYER_IN_SERVER) {
                    PLAYER_IN_SERVER = true;

                    if (!sentInitialMessage) {
                        List<ServerPlayer> players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();

                        for (int i = 0; i < players.size(); i++) {
                            ServerPlayer plr = players.get(i);
                            plr.sendSystemMessage(Component.literal("Mobs will spawn in " + initialDelayConfig + " seconds!"));
                        }
                    }
                }

                //event.getEntity().sendSystemMessage(Component.literal("Mobs will spawn in " + initialDelayConfig + " seconds!"));
            }
        } else {
            if (!sendAlertConfig) {
                sendModAlert();
                sendAlertConfig = true;
            }
        }

        /*
        if (ismodEnabled) {
            if (!sentInitialMessage) {
                if (!event.getEntity().isDeadOrDying()) {
                    List<ServerPlayer> players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();

                    for (int i = 0; i < players.size(); i++) {
                        ServerPlayer plr = players.get(i);
                        plr.sendSystemMessage(Component.literal(event.getEntity().getGameProfile().getName() +
                                " joined! Mobs will continue to spawn in " + initialDelayConfig + " seconds!"));
                    }

                    event.getEntity().sendSystemMessage(Component.literal("Mobs will spawn in " + initialDelayConfig + " seconds!"));
                    sentInitialMessage = true;
                }
            }
        } else {
            if (!sendAlertConfig) {
                sendModAlert();
                sendAlertConfig = true;
            }
        }

         */
    }

    private static void closerAlert() {
        List<ServerPlayer> players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();

        for (int i = 0; i < players.size(); i++) {
            ServerPlayer plr = players.get(i);

            plr.sendSystemMessage(Component.literal("Mobs will start to spawn soon!"));
        }
    }

    private static void sendModAlert() {
        List<ServerPlayer> players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();

        for (int i = 0; i < players.size(); i++) {
            ServerPlayer plr = players.get(i);

            plr.sendSystemMessage(Component.literal("[Random Mobs Spawning]"));
            plr.sendSystemMessage(Component.literal("Mobs Spawning is currently disabled! Change this setting to \"true\" in the server config to enable the mod."));
        }
    }

    public static void updateInterval() {
        regularDelay = 20 * Config.intervalMobsSpawn;
        initialDelayConfig = Config.initialDelayHeadstart;
        initialDelay = TICKS_PER_SECOND * initialDelayConfig;
    }

    public static void updatePermissions() {
        wardenEnabled = Config.wardenEnabled;
        witherEnabled = Config.witherEnabled;
        shulkerEnabled = Config.shulkerEnabled;
        endermiteEnabled = Config.endermiteEnabled;
        bruteEnabled = Config.bruteEnabled;
        hoglinEnabled = Config.hoglinEnabled;
        breezeEnabled = Config.breezeEnabled;
        chargedCreeperEnabled = Config.chargedCreeperEnabled;
        ghastEnabled = Config.ghastEnabled;
        magmaCubeEnabled = Config.magmaCubeEnabled;
        witherskellyEnabled = Config.witherskellyEnabled;
        blazeEnabled = Config.blazeEnabled;
        boggedEnabled = Config.boggedEnabled;
        babyzombieEnabled = Config.babyzombieEnabled;
        phantomEnabled = Config.phantomEnabled;
        witchEnabled = Config.witchEnabled;
        creeperEnabled = Config.creeperEnabled;
        strayEnabled = Config.strayEnabled;
        skeletonEnabled = Config.skeletonEnabled;
        slimeEnabled = Config.slimeEnabled;
        huskEnabled = Config.huskEnabled;
        zombieEnabled = Config.zombieEnabled;
        chickenEnabled = Config.chickenEnabled;
        pigEnabled = Config.pigEnabled;

        boolean modEnabled = Config.mobSpawnEnabled;

        if (modEnabled) {
            ismodEnabled = true;
        } else {
            ismodEnabled = false;
        }
    }

    private static void spawnMobs() {
        List<ServerPlayer> players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();
        if (players.isEmpty()) {
            return;
        }

        int mobChance = new Random().nextInt(9000) + 1;
        Random random = new Random();
        ServerPlayer randomPlayer = players.get(random.nextInt(players.size()));

         if (mobChance <= 35) {
             if (wardenEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(2) + 1;

                     for (int i = 0; i < amount; i++) {
                         Warden warden = EntityType.WARDEN.create(randomPlayer.level());

                         if (warden != null) {
                             warden.moveTo(randomPlayer.getX() + new Random().nextInt(10) + 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) + 5);
                             randomPlayer.serverLevel().addFreshEntity(warden);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Warden spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 100) {
             if (witherEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(3) + 1;

                     for (int i = 0; i < amount; i++) {
                         WitherBoss witherBoss = EntityType.WITHER.create(randomPlayer.level());

                         if (witherBoss != null) {
                             witherBoss.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 15,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(witherBoss);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Withers spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 270) {
             if (shulkerEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(3) + 2;

                     for (int i = 0; i < amount; i++) {
                         Shulker shulker = EntityType.SHULKER.create(randomPlayer.level());

                         if (shulker != null) {
                             shulker.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY(),
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(shulker);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Shulkers spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 355) {
             if (endermiteEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(15) + 3;

                     for (int i = 0; i < amount; i++) {
                         Endermite mite = EntityType.ENDERMITE.create(randomPlayer.level());

                         if (mite != null) {
                             mite.moveTo(randomPlayer.getX() + new Random().nextInt(8) + 3, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(8) + 3);
                             randomPlayer.serverLevel().addFreshEntity(mite);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Endermites spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 400) {
             if (bruteEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(3) + 2;

                     for (int i = 0; i < amount; i++) {
                         PiglinBrute piglin = EntityType.PIGLIN_BRUTE.create(randomPlayer.level());

                         if (piglin != null) {
                             piglin.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(piglin);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Piglin brutes spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 500) {
             if (hoglinEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(3) + 1;

                     for (int i = 0; i < amount; i++) {
                         Hoglin hoglin = EntityType.HOGLIN.create(randomPlayer.level());

                         if (hoglin != null) {
                             hoglin.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(hoglin);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Hoglin spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
         } else if (mobChance <= 600) {
             if (breezeEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(5) + 1;

                     for (int i = 0; i < amount; i++) {
                         Breeze breeze = EntityType.BREEZE.create(randomPlayer.level());

                         if (breeze != null) {
                             breeze.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(breeze);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Breeze spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
         } else if (mobChance <= 800) {
             if (chargedCreeperEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(6) + 2;

                     for (int i = 0; i < amount; i++) {
                         Creeper creeper = EntityType.CREEPER.create(randomPlayer.level());
                         LightningBolt bolt = null;

                         if (creeper != null) {
                             bolt = EntityType.LIGHTNING_BOLT.create(creeper.level());

                             creeper.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(creeper);
                         }

                         if (bolt != null) {
                             bolt.setDamage(0f);
                             bolt.moveTo(creeper.getX(), creeper.getY(), creeper.getZ());
                             randomPlayer.serverLevel().addFreshEntity(bolt);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Charged creepers spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        }  else if (mobChance <= 875) {
             if (ghastEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(6) + 1;

                     for (int i = 0; i < amount; i++) {
                         Ghast ghast = EntityType.GHAST.create(randomPlayer.level());

                         if (ghast != null) {
                             ghast.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 5,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(ghast);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Ghast spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 920) {
             if (magmaCubeEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(4) + 2;

                     for (int i = 0; i < amount; i++) {
                         MagmaCube cube = EntityType.MAGMA_CUBE.create(randomPlayer.level());

                         if (cube != null) {
                             cube.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 5,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(cube);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Magma Cubes spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
         } else if (mobChance <= 1000) {
             if (witherskellyEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(7) + 3;

                     for (int i = 0; i < amount; i++) {
                         WitherSkeleton wskele = EntityType.WITHER_SKELETON.create(randomPlayer.level());

                         if (wskele != null) {
                             wskele.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             wskele.equipItemIfPossible(new ItemStack(Items.STONE_SWORD));
                             randomPlayer.serverLevel().addFreshEntity(wskele);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Wither skeletons spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 1050) {
             if (blazeEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(4) + 1;

                     for (int i = 0; i < amount; i++) {
                         Blaze blaze = EntityType.BLAZE.create(randomPlayer.level());

                         if (blaze != null) {
                             blaze.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 5,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(blaze);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Blaze spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
         } else if (mobChance <= 1150) {
             if (boggedEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(8) + 2;

                     for (int i = 0; i < amount; i++) {
                         Bogged bogged = EntityType.BOGGED.create(randomPlayer.level());

                         if (bogged != null) {
                             bogged.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             bogged.equipItemIfPossible(new ItemStack(Items.BOW));
                             bogged.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.TIPPED_ARROW, 64));
                             randomPlayer.serverLevel().addFreshEntity(bogged);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Bogged spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
         } else if (mobChance <= 1250) {
             if (babyzombieEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(10) + 3;

                     for (int i = 0; i < amount; i++) {
                         int chance = new Random().nextInt(100);
                         int helmet = new Random().nextInt(4);

                         Zombie zombie;

                         if (chance <= 50) {
                             zombie = EntityType.ZOMBIE_VILLAGER.create(randomPlayer.level());
                         } else {
                             zombie = EntityType.ZOMBIE.create(randomPlayer.level());
                         }

                         if (zombie != null) {
                             zombie.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             zombie.setBaby(true);

                             if (helmet == 3) {
                                 zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
                             } else if (helmet == 2) {
                                 zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
                             } else if (helmet == 1) {
                                 zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
                             }

                             randomPlayer.serverLevel().addFreshEntity(zombie);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Baby zombies spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
         } else if (mobChance <= 1500) {
             if (phantomEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(12) + 2;

                     for (int i = 0; i < amount; i++) {
                         Phantom phantom = EntityType.PHANTOM.create(randomPlayer.level());

                         if (phantom != null) {
                             phantom.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(phantom);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Phantoms spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 2000) {
             if (witchEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(5) + 2;

                     for (int i = 0; i < amount; i++) {
                         Witch witch = EntityType.WITCH.create(randomPlayer.level());

                         if (witch != null) {
                             witch.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(witch);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Witches spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
         } else if (mobChance <= 2500) {
             if (creeperEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(10) + 3;

                     for (int i = 0; i < amount; i++) {
                         Creeper creeper = EntityType.CREEPER.create(randomPlayer.level());

                         if (creeper != null) {
                             creeper.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(creeper);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Creepers spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 3000) {
             if (strayEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(10) + 3;

                     for (int i = 0; i < amount; i++) {
                         Stray stray = EntityType.STRAY.create(randomPlayer.level());

                         if (stray != null) {
                             stray.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             stray.equipItemIfPossible(new ItemStack(Items.BOW));
                             stray.setArrowCount(64);
                             randomPlayer.serverLevel().addFreshEntity(stray);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Strays spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 3500) {
             if (skeletonEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(10) + 5;

                     for (int i = 0; i < amount; i++) {
                         int helmet = new Random().nextInt(4);
                         Skeleton skeleton = EntityType.SKELETON.create(randomPlayer.level());

                         if (skeleton != null) {
                             skeleton.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             skeleton.equipItemIfPossible(new ItemStack(Items.BOW));
                             skeleton.setArrowCount(64);

                             if (helmet == 3) {
                                 skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
                             } else if (helmet == 2) {
                                 skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
                             } else if (helmet == 1) {
                                 skeleton.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
                             }

                             randomPlayer.serverLevel().addFreshEntity(skeleton);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Skeletons spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 4000) {
             if (slimeEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(15) + 3;

                     for (int i = 0; i < amount; i++) {
                         Slime slime = EntityType.SLIME.create(randomPlayer.level());

                         if (slime != null) {
                             slime.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             slime.setSize(new Random().nextInt(3), true);
                             randomPlayer.serverLevel().addFreshEntity(slime);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Slimes spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 4500) {
             if (huskEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(10) + 3;

                     for (int i = 0; i < amount; i++) {
                         Husk husk = EntityType.HUSK.create(randomPlayer.level());;

                         if (husk != null) {
                             husk.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(husk);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Husks spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 5000) {
             if (zombieEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(12) + 3;

                     for (int i = 0; i < amount; i++) {
                         int chance = new Random().nextInt(100);
                         int helmet = new Random().nextInt(4);

                         Zombie zombie;

                         if (chance <= 50) {
                             zombie = EntityType.ZOMBIE_VILLAGER.create(randomPlayer.level());
                         } else {
                             zombie = EntityType.ZOMBIE.create(randomPlayer.level());
                         }

                         if (zombie != null) {
                             zombie.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);

                             if (helmet == 3) {
                                 zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
                             } else if (helmet == 2) {
                                 zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.CHAINMAIL_HELMET));
                             } else if (helmet == 1) {
                                 zombie.setItemSlot(EquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
                             }

                             randomPlayer.serverLevel().addFreshEntity(zombie);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Zombies spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else if (mobChance <= 6000) {
             if (chickenEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(6) + 4;

                     for (int i = 0; i < 15; i++) {
                         Chicken chicken = EntityType.CHICKEN.create(randomPlayer.level());

                         if (chicken != null) {
                             chicken.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(chicken);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Chickens spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        } else {
             if (pigEnabled) {
                 if (!randomPlayer.isDeadOrDying()) {
                     int amount = new Random().nextInt(6) + 4;

                     for (int i = 0; i < amount; i++) {
                         Pig pig = EntityType.PIG.create(randomPlayer.level());

                         if (pig != null) {
                             pig.moveTo(randomPlayer.getX() + new Random().nextInt(10) - 5, randomPlayer.getY() + 2,
                                     randomPlayer.getZ() + new Random().nextInt(10) - 5);
                             randomPlayer.serverLevel().addFreshEntity(pig);
                         }
                     }

                     randomPlayer.sendSystemMessage(Component.literal(amount + " Pigs spawned @ " + randomPlayer.getGameProfile().getName() + "!"));
                 }
             }
        }
    }
}
