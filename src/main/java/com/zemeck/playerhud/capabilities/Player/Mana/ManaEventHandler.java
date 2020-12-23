package com.zemeck.playerhud.capabilities.Player.Mana;

import com.zemeck.playerhud.PlayerHud;
import com.zemeck.playerhud.network.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;


public class ManaEventHandler {

        public static void preventGameOverlay(RenderGameOverlayEvent.Pre event){
            if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                event.setCanceled(true);
            }
            if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
                event.setCanceled(true);

                Minecraft mc = Minecraft.getInstance();
                PlayerEntity player = mc.player;

                player.getCapability(CapabilityEntityMana.ENTITY_MANA_CAPABILITY).ifPresent(h -> {
                    String textureToUse = "player_hud";
                    //Minecraft.getInstance().getMainWindow().getScaledWidth(), Minecraft.getInstance().getMainWindow().getScaledHeight()
                    Minecraft.getInstance().textureManager.bindTexture(new ResourceLocation("playerhud", "textures/gui/" + textureToUse + ".png"));
                    AbstractGui.blit(0,0,0.F, 0.F, 180, 67, 180, 128);
                    mc.fontRenderer.drawString(player.getDisplayName().getString(), 47, 4, 0x000000);
                    mc.fontRenderer.drawString("HP: 10" +" / " + player.getMaxHealth(), 47, 14, 0x000000);
                    mc.fontRenderer.drawString("MP: " +(int) h.getMana() + " / 250", 47, 23, 0x000000);
                });
            }
        }

        public static void midGameOverlay(RenderGameOverlayEvent event){
            if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
                event.setCanceled(true);

            }
            if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                event.setCanceled(true);
                // This will be the "character menu"
                //Minecraft.getInstance().displayGuiScreen(new SpawnerScreen());
            }
        }

        public static void renderGameOverlay(RenderGameOverlayEvent.Post event) {

            if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
                event.setCanceled(true);
            }
            if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                event.setCanceled(true);
            }

//            if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD)
//            {
//                ((ForgeIngameGui)Minecraft.getInstance().ingameGUI).renderFood = false;
////                Minecraft mc = Minecraft.getInstance();
////                ClientPlayerEntity player = mc.player;
////                World world = player.getEntityWorld();
////                int held = player.inventory.currentItem;
////
////                if(held < 0 || held >= player.inventory.mainInventory.size())
////                    return;
////
////                ItemStack stack = player.inventory.mainInventory.get(held);
////
////                if(!stack.isEmpty() && stack.getItem() == Items.STICK)
////                {
////                    String textureToUse = "item_not_bound";
////                    double eyeHeight = player.getEyeHeight();
////                    Vec3d lookVec = new Vec3d((player.getPosition().getX() + (player.getLookVec().x * 5)), ((eyeHeight + player.getPosition().getY()) + (player.getLookVec().y * 5)), (player.getPosition().getZ() + (player.getLookVec().z * 5)));
////                    RayTraceResult mop = world.rayTraceBlocks(new RayTraceContext(new Vec3d(player.getPosition().getX(), player.getPosition().getY() + player.getEyeHeight(), player.getPosition().getZ()), lookVec, BlockMode.OUTLINE, FluidMode.NONE, player));
////
////                    if(mop != null && mop.getType() == Type.BLOCK)
////                    {
////                        Minecraft.getInstance().textureManager.bindTexture(new ResourceLocation("playerhud", "textures/gui/" + textureToUse + ".png"));
////                        Minecraft.getInstance().ingameGUI
////                        .blit(Minecraft.getInstance().getMainWindow().getScaledWidth() / 2 - 90 + held * 20 + 2, Minecraft.getInstance().getMainWindow().getScaledHeight() - 16 - 3, 0, 0, 16, 16, 16, 16);
////                    }
////                }
//            }
        }

    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            EntityManaProvider provider = new EntityManaProvider();
            event.addCapability(new ResourceLocation(PlayerHud.MODID, "mana"), provider);
            event.addListener(provider::invalidate);
        }
    }

    public static void onPlayerTickEvent(TickEvent.PlayerTickEvent event) {
//        if (event.player instanceof PlayerEntity) {
//            Minecraft mc = Minecraft.getInstance();
//            PlayerEntity clientPlayer = mc.player;
//            event.player.getCapability(CapabilityEntityMana.ENTITY_MANA_CAPABILITY).ifPresent(h -> {
//                float serverMana = h.getMana();
//                clientPlayer.getCapability(CapabilityEntityMana.ENTITY_MANA_CAPABILITY).ifPresent(cp -> {
//                    cp.set(serverMana);
//                });
//            });
//        }
    }

    public static void onDeathEvent(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        entity.getCapability(CapabilityEntityMana.ENTITY_MANA_CAPABILITY).ifPresent(h -> {
            float charge = h.getMana();
            if (charge > 0) {
                entity.getEntityWorld().createExplosion(entity, entity.getPosX(), entity.getPosY(), entity.getPosZ(), charge * .3f + 1.0f, Explosion.Mode.DESTROY);
            }
        });
    }

    public static void playerClone(final PlayerEvent.Clone event) {
//        final IPlayerBaseCap oldBaseCap = event.getOriginal().getCapability(PlayerBaseCapProvider.PLAYER_BASE_CAP).orElseThrow(() -> new RuntimeException("No player capability found!"));
//        final IPlayerBaseCap newBaseCap = event.getPlayer().getCapability(PlayerBaseCapProvider.PLAYER_BASE_CAP).orElseThrow(() -> new RuntimeException("No player capability found!"));
//        if (oldBaseCap != null && newBaseCap != null) {
//            newBaseCap.setFirstSpawn(oldBaseCap.getFirstSpawn());
//            newBaseCap.setVillage(oldBaseCap.getVillage());
//            newBaseCap.setClan(oldBaseCap.getClan());
//            newBaseCap.setRank(oldBaseCap.getRank());
//            newBaseCap.setExp(oldBaseCap.getExp());
//            newBaseCap.setLevel(oldBaseCap.getLevel());
//            newBaseCap.setChakra(oldBaseCap.getChakra());
//            newBaseCap.setMaxChakra(oldBaseCap.getMaxChakra());
//        }
    }

    public static void onPlayerTracking(PlayerEvent.StartTracking event){
//        if(!(event.getTarget() instanceof PlayerEntity)) return;
//        PlayerEntity player = (PlayerEntity) event.getTarget();
//        ServerPlayerEntity target = (ServerPlayerEntity) event.getPlayer();
//        if (!player.world.isRemote()) {
//            player.getCapability(CapabilityEntityMana.ENTITY_MANA_CAPABILITY, null)
//                    .ifPresent(state -> {
//                        CompoundNBT nbt = new CompoundNBT();
//                        Capability<IMana> cap = CapabilityEntityMana.ENTITY_MANA_CAPABILITY;
//                        Capability.IStorage<IMana> storage = cap.getStorage();
//                        nbt.put(cap.getName(), storage.writeNBT(cap, state, null));
//                        ManaStateSyncMesage message = new ManaStateSyncMesage(nbt);
//
//                        NetworkLoader.channel.send(PacketDistributor.PLAYER.with(() -> target ), message);
//
//                    });
//        }

    }

//    public static void serverLoginEvent(final PlayerEvent.PlayerLoggedInEvent event) {
//        PlayerEntity player = event.getPlayer();
//        IMana basecap = player.getCapability(CapabilityEntityMana.ENTITY_MANA_CAPABILITY).orElseThrow(() -> new RuntimeException("No mana capability found!"));
//        if (basecap != null) {
////            Main.logger.info("Clan data sent from Storage: " + basecap.getClan());
//
//            PacketDispatcher.INSTANCE.sendTo(new PacketFirstSpawn(basecap.getFirstSpawn()), ((ServerPlayerEntity)player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
//            PacketDispatcher.INSTANCE.sendTo(new PacketVillageC(basecap.getVillage()), ((ServerPlayerEntity)player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
//            PacketDispatcher.INSTANCE.sendTo(new PacketClanC(basecap.getClan()), ((ServerPlayerEntity)player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
//            if (basecap.getFirstSpawn() == true) {
//                basecap.setFirstSpawn(false);
//                PacketDispatcher.INSTANCE.sendTo(new PacketFirstSpawn(basecap.getFirstSpawn()), ((ServerPlayerEntity)player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
//                player.sendMessage(new TranslationTextComponent("msgs.firstjoin", ""));
//                ItemStack stack = new ItemStack(ItemList.character_creation, 1);
//                player.addItemStackToInventory(stack);
//            }
//        }
//    }
    // This needs to be used when "attacking?"

    public static void onAttackEvent(AttackEntityEvent event) {
        Entity attacker = event.getEntity();

        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            if(player.world.isRemote) {
                ItemStack stack = player.getHeldItemMainhand();
                EntityManaProvider entityManaProvider = new EntityManaProvider();
                if (stack.getItem() == Items.DIAMOND) {
                    //Entity target = event.getTarget();
                    player.getCapability(CapabilityEntityMana.ENTITY_MANA_CAPABILITY).ifPresent(h -> {
                        h.consume(50);
//                  onPlayerTracking(new PlayerEvent.StartTracking(player, event.getTarget()));
                        Networking.sendToServer(new ManaStateSyncMessage(h));
                        player.sendStatusMessage(new TranslationTextComponent("message.updated_mana", Float.toString(h.getMana())), true);
                        event.setCanceled(true);
                        // target.getEntityWorld().addParticle(ParticleTypes.FIREWORK, target.getPosX(), target.getPosY()+1, target.getPosZ(), 0.0, 0.0, 0.0);
                    });
                }
                if (stack.getItem() == Items.STICK) {
                    //Entity target = event.getTarget();
                    player.getCapability(CapabilityEntityMana.ENTITY_MANA_CAPABILITY).ifPresent(h -> {
                        h.fill(h.getMaxMana());
//                    onPlayerTracking(new PlayerEvent.StartTracking(player, event.getTarget()));
                        event.setCanceled(true);
                    });
                }
            }
        }
    }
}
