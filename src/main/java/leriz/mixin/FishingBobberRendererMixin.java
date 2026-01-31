package leriz.mixin;

import leriz.Nohook;
import leriz.config.NohookConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntityRenderer.class)
public abstract class FishingBobberRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void nohook$onRenderHead(FishingBobberEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        NohookConfig config = Nohook.getConfig();
        if (config != null && config.enabled && config.hideLine && nohook$shouldHide(entity)) {
            ci.cancel();
        }
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V"))
    private void nohook$hideBobber(MatrixStack instance, float x, float y, float z, FishingBobberEntity entity) {
        NohookConfig config = Nohook.getConfig();
        if (config != null && config.enabled && nohook$shouldHide(entity)) {
            instance.scale(0.0F, 0.0F, 0.0F);
        } else {
            instance.scale(x, y, z);
        }
    }

    private boolean nohook$shouldHide(FishingBobberEntity entity) {
        NohookConfig config = Nohook.getConfig();
        if (config == null) return false;
        if (config.onlyPlayerHooks && !(entity.getOwner() instanceof PlayerEntity)) return false;
        if (config.onlyWhenHooked && !(entity.getHookedEntity() instanceof PlayerEntity)) return false;
        if (!config.thirdPersonSupport && !MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) return false;
        return true;
    }
}
