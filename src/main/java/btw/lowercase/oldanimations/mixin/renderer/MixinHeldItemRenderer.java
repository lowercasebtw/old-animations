package btw.lowercase.oldanimations.mixin.renderer;

import btw.lowercase.oldanimations.OldAnimations;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HeldItemRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinHeldItemRenderer {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private float equipProgressMainHand;

    @Shadow
    private float prevEquipProgressMainHand;

    @Shadow
    private float equipProgressOffHand;

    @Shadow
    private float prevEquipProgressOffHand;

    @Shadow
    private ItemStack mainHand;

    @Shadow
    private ItemStack offHand;

    @Unique
    private void update(ItemStack itemStack, ItemStack hand, boolean isOffHand) {
        float g = ((hand != null && itemStack != null &&
                !ItemStack.areItemsEqual(hand, itemStack))
                || (hand == null && itemStack != null)) ? 0.0f : 1.0f;
        if (!isOffHand) {
            this.prevEquipProgressMainHand = this.equipProgressMainHand;
            this.equipProgressMainHand += MathHelper.clamp(g - this.equipProgressMainHand, -0.4f, 0.4f);
            if (this.equipProgressMainHand < 0.1f)
                this.mainHand = itemStack;
        } else {
            this.prevEquipProgressOffHand = this.equipProgressOffHand;
            this.equipProgressOffHand += MathHelper.clamp(g - this.equipProgressOffHand, -0.4f, 0.4f);
            if (this.equipProgressOffHand < 0.1f)
                this.offHand = itemStack;
        }
    }

    @WrapWithCondition(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", ordinal = 1))
    private boolean renderItem$old$renderOffHand(HeldItemRenderer instance, AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        return OldAnimations.CONFIG.legacySettings.RENDER_OFFHAND;
    }

    @Inject(method = "updateHeldItems", at = @At(value = "HEAD"), cancellable = true)
    private void updateHeldItems$old(CallbackInfo ci) {
        if (OldAnimations.CONFIG.visualSettings.OLD_SWING_ANIMATION) {
            ci.cancel();
            PlayerEntity playerEntity = this.client.player;
            assert playerEntity != null;
            update(playerEntity.getMainHandStack(), mainHand, false);
            if (OldAnimations.CONFIG.legacySettings.RENDER_OFFHAND) {
                update(playerEntity.getOffHandStack(), offHand, true);
            }
        }
    }
}
