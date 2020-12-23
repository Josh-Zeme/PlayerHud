package com.zemeck.playerhud.capabilities.Player.Mana;

    import net.minecraft.network.PacketBuffer;
    import net.minecraftforge.fml.network.NetworkEvent;
    import java.util.function.Supplier;

    import net.minecraft.nbt.CompoundNBT;

public class ManaStateSyncMessage {
    private IMana mana;

    public ManaStateSyncMessage() {
        new Mana();
    }

    public ManaStateSyncMessage(PacketBuffer buf) {
        CompoundNBT tag = buf.readCompoundTag();
        this.mana.setMana(tag.getFloat("mana"));
        this.mana.setMana(tag.getFloat("maxMana"));
    }

    public ManaStateSyncMessage(IMana mana){
        this.mana = mana;
    }

    public void encode(PacketBuffer buf) {
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("mana", mana.getMana());
        tag.putFloat("maxmana", mana.getMaxMana());
        buf.writeCompoundTag(tag);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
//        NetworkEvent.Context ctx = context.get();
//        ctx.enqueueWork(() -> {
//            if (ctx.getDirection().getReceptionSide().isClient() && ctx.getDirection().getOriginationSide().isServer()) {
//                PlayerEntity player = Minecraft.getInstance().player;
//                player.getCapability(CapabilityEntityMana.ENTITY_MANA_CAPABILITY, null)
//                        .ifPresent(state -> {
//                            Capability.IStorage<IMana> storage = CapabilityEntityMana.ENTITY_MANA_CAPABILITY.getStorage();
//
//                        });
//            }
//        });
//        ctx.setPacketHandled(true);
    }
}