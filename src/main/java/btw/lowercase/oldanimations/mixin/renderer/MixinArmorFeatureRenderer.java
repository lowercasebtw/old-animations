package btw.lowercase.oldanimations.mixin.renderer;

import btw.lowercase.oldanimations.IRenderedOverlay;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = ArmorFeatureRenderer.class, priority = Integer.MAX_VALUE)
public abstract class MixinArmorFeatureRenderer implements IRenderedOverlay {
    @Shadow
    @Final
    private EquipmentRenderer equipmentRenderer;

    // Code used from/inspired by:
    // Licensed CC0-1.0
    // https://modrinth.com/user/rizecookey
    // https://modrinth.com/mod/cookeymod
    // https://github.com/rizecookey/CookeyMod/blob/1.21/src/main/java/net/rizecookey/cookeymod/mixin/client/HumanoidArmorLayerMixin.java
    @Override
    public void oldanimations$setArmorOverlayCoords(int coords) {
        ((IRenderedOverlay) this.equipmentRenderer).oldanimations$setArmorOverlayCoords(coords);
    }
}
