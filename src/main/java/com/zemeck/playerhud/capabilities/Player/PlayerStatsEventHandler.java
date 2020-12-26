package com.zemeck.playerhud.capabilities.Player;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.zemeck.playerhud.PlayerHud;
import com.zemeck.playerhud.network.Networking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;


public class PlayerStatsEventHandler {

    // Not sure which of these does the cancelling, so i did all 3.
    // will confirm and clean up later
        public static void preventGameOverlay(RenderGameOverlayEvent.Pre event){
            if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                event.setCanceled(true);
            }
            if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
                event.setCanceled(true);

                Minecraft mc = Minecraft.getInstance();
                PlayerEntity player = mc.player;
                MatrixStack matrix = event.getMatrixStack();
                player.getCapability(CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY).ifPresent(h -> {
                    String textureToUse = "player_hud";
                    Minecraft.getInstance().textureManager.bindTexture(new ResourceLocation("playerhud", "textures/gui/" + textureToUse + ".png"));
                    AbstractGui.blit(matrix,0,0,0.F, 0.F, 180, 67, 180, 128);
                    mc.fontRenderer.drawString(matrix,h.getProfession() + ": " + player.getDisplayName().getString(), 47, 4, 0x000000);
                    mc.fontRenderer.drawString(matrix,"HP: " + Math.round(player.getHealth()) +" / " +  Math.round(player.getMaxHealth()), 47, 14, 0x000000);
                    mc.fontRenderer.drawString(matrix,"MP: " +(int) h.getMana() + " / " +  Math.round(h.getMaxMana()), 47, 23, 0x000000);
                });
            }
        }

        public static void midGameOverlay(RenderGameOverlayEvent event){
            if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
                event.setCanceled(true);

            }
            if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                event.setCanceled(true);
            }
        }

        public static void renderGameOverlay(RenderGameOverlayEvent.Post event) {

            if(event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
                event.setCanceled(true);
            }
            if(event.getType() == RenderGameOverlayEvent.ElementType.FOOD) {
                event.setCanceled(true);
            }
        }

    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            EntityPlayerStatsProvider provider = new EntityPlayerStatsProvider();
            event.addCapability(new ResourceLocation(PlayerHud.MODID, "mana"), provider);
            event.addListener(provider::invalidate);
        }
    }

    // Copies over stats on player death
    public static void playerClone(final PlayerEvent.Clone event) {
        final IPlayerStats oldBaseCap = event.getOriginal().getCapability(CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY).orElseThrow(() -> new RuntimeException("No player capability found!"));
        final IPlayerStats newBaseCap = event.getPlayer().getCapability(CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY).orElseThrow(() -> new RuntimeException("No player capability found!"));
        if (oldBaseCap != null && newBaseCap != null) {
            newBaseCap.setDefence(oldBaseCap.getDefence());
            newBaseCap.setMana(oldBaseCap.getMana());
            newBaseCap.setMaxMana(oldBaseCap.getMaxMana());
            newBaseCap.setPower(oldBaseCap.getStamina());
            newBaseCap.setStamina(oldBaseCap.getStamina());
            newBaseCap.setProfession(oldBaseCap.getProfession());
        }
    }

    // When the player logs in, get their stats and update the client
    public static void serverLoginEvent(final PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        ServerPlayerEntity target = (ServerPlayerEntity) event.getPlayer();
        IPlayerStats playerStats = player.getCapability(CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY).orElseThrow(() -> new RuntimeException("No player capability found!"));
        if (playerStats != null) {
            if (!player.world.isRemote()){
                Networking.sendToClient(new PlayerStatsStateSyncMessage(playerStats), target);
                player.sendStatusMessage(new TranslationTextComponent("message.updated_mana", Float.toString(playerStats.getMana())), true);
            }
        }
    }

    // when player attacks, if diamond -50 mana. If stick refill mana
    public static void onAttackEvent(AttackEntityEvent event) {
        Entity attacker = event.getEntity();

        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;
            if(!player.world.isRemote) {
                ItemStack stack = player.getHeldItemMainhand();
                if (stack.getItem() == Items.DIAMOND) {
                    event.setCanceled(true);
                    player.getCapability(CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY).ifPresent(h -> {
                        h.consumeMana(50);
                        Networking.sendToClient(new PlayerStatsStateSyncMessage(h), (ServerPlayerEntity) player);
                        player.sendStatusMessage(new TranslationTextComponent("message.updated_mana", Float.toString(h.getMana())), true);
                    });
                }
                if (stack.getItem() == Items.STICK) {
                    player.getCapability(CapabilityEntityPlayerStats.ENTITY_PLAYER_STATS_CAPABILITY).ifPresent(h -> {
                        event.setCanceled(true);
                        h.fillMana(h.getMaxMana());
                        Networking.sendToClient(new PlayerStatsStateSyncMessage(h), (ServerPlayerEntity) player);
                        player.sendStatusMessage(new TranslationTextComponent("message.updated_mana", Float.toString(h.getMana())), true);
                    });
                }
            }
        }
    }
}
