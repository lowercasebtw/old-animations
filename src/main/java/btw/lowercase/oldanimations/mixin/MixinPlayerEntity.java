package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = PlayerEntity.class, priority = Integer.MAX_VALUE)
public abstract class MixinPlayerEntity {
    @ModifyConstant(method = "attack", constant = @Constant(floatValue = 0.0f, ordinal = 4))
    public float attack$old(float value) {
        return OldAnimations.CONFIG.combatSettings.ALWAYS_SHARP_PARTICLES ? -1 : value;
    }

    // TODO: Particle Multiplier
}
