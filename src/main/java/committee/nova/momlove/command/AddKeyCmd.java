package committee.nova.momlove.command;

import com.mojang.brigadier.context.CommandContext;
import committee.nova.momlove.MomLove;
import committee.nova.momlove.handler.ConfigHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.TranslatableComponent;

/**
 * Project: MomLove-Forge
 * Author: cnlimiter
 * Date: 2022/11/3 2:20
 * Description:
 */
public class AddKeyCmd {
    public static int execute(CommandContext<CommandSourceStack> context, String keyWord) {
        try {
            final var notIn = MomLove.addKey(keyWord);
            context.getSource().sendSuccess(new TranslatableComponent(notIn ? "momlove.keys.add.success" : "momlove.keys.add.duplicate"), true);
        } catch (Exception e) {
            e.printStackTrace();
            context.getSource().sendSuccess(new TranslatableComponent("momlove.keys.add.failure"), true);
        }
        ConfigHandler.onChange();
        return 0;
    }
}
