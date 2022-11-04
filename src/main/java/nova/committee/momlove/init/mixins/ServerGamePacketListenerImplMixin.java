package nova.committee.momlove.init.mixins;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import nova.committee.momlove.init.callbacks.ServerLevelEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/18 10:30
 * Version: 1.0
 */
@Mixin(value = ServerGamePacketListenerImpl.class, priority = 1001)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;

    @Inject(method = "broadcastChatMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V"))
    public void SGPLI_handleChat(PlayerChatMessage playerChatMessage, CallbackInfo ci) {
        String s1 = playerChatMessage.serverContent().getString();
        Component component2 = Component.translatable("chat.type.text", this.player.getDisplayName(), s1);
        ServerLevelEvents.Server_Chat.invoker().onChat(this.player, s1, component2);
    }
}
