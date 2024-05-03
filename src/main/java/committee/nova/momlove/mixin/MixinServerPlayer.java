package committee.nova.momlove.mixin;

import com.mojang.authlib.GameProfile;
import committee.nova.momlove.MomLove;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer extends Player {
    public MixinServerPlayer(Level l, BlockPos p, float f, GameProfile g, @Nullable ProfilePublicKey k) {
        super(l, p, f, g, k);
    }

    @Redirect(method = "die", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isSpectator()Z"))
    private boolean redirect$hybridServer(ServerPlayer instance) {
        return instance.isSpectator() || MomLove.getConfig().getUuidData().contains(getUUID());
    }

    @Redirect(method = "restoreFrom", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameRules;getBoolean(Lnet/minecraft/world/level/GameRules$Key;)Z"))
    private boolean redirect$restoreFrom(GameRules instance, GameRules.Key<GameRules.BooleanValue> v) {
        return instance.getBoolean(v) || MomLove.getConfig().getUuidData().contains(getUUID());
    }
}
