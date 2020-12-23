package com.zemeck.playerhud.commands;

import com.zemeck.playerhud.PlayerHud;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> cmdTut = dispatcher.register(
                Commands.literal(PlayerHud.MODID)
                        .then(CommandTest.register(dispatcher))
        );

        dispatcher.register(Commands.literal("tut").redirect(cmdTut));
    }

}
