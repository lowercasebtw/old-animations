package btw.lowercase.oldanimations.mixin.renderer;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EquipmentRenderer.class)
public class MixinEquipmentRenderer {
    // TODO: Fix armor damage color
    @ModifyArg(method = "render(Lnet/minecraft/item/equipment/EquipmentModel$LayerType;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;II)V"), index = 3)
    private int modifyOverlayCoords(int original) {
        if (OldAnimations.CONFIG.qolSettings.RENDER_HIT_COLOR_ARMOR) {
            return OldAnimations.armorOverlayCoords;
        } else {
            return original;
        }
    }
}
