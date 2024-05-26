package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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

    @Redirect(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/item/HeldItemRenderer$HandRenderType;renderOffHand:Z", opcode = Opcodes.GETFIELD))
    public boolean renderItem$old$renderOffHand(HeldItemRenderer.HandRenderType instance) {
        return OldAnimations.CONFIG.legacySettings.RENDER_OFFHAND;
    }

    @Inject(at = @At(value = "HEAD"), method = "updateHeldItems", cancellable = true)
    private void updateHeldItems$old(CallbackInfo ci) {
        if (!OldAnimations.CONFIG.visualSettings.OLD_SWING_ANIMATION)
            return;
        ci.cancel();
        PlayerEntity playerEntity = this.client.player;
        assert playerEntity != null;
        update(playerEntity.getMainHandStack(), mainHand, false);
        if (OldAnimations.CONFIG.legacySettings.RENDER_OFFHAND) {
            update(playerEntity.getOffHandStack(), offHand, true);
        }
    }
}
