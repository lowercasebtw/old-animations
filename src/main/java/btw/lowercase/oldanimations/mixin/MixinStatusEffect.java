package btw.lowercase.oldanimations.mixin;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.entity.effect.StatusEffect;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(value = StatusEffect.class, priority = Integer.MAX_VALUE)
public abstract class MixinStatusEffect {
    @Unique
    private static Map<String, Integer> oldPotionColors = new HashMap<>();
    static {
        // Old Potion Colors (https://github.com/sp614x/optifine/blob/master/OptiFineDoc/doc/color.properties) + Other sources
        oldPotionColors.put("effect.minecraft.absorption", 0x2552A5);
        oldPotionColors.put("effect.minecraft.bad_omen", 0xB6138);
        oldPotionColors.put("effect.minecraft.blindness", 0x1F1F23);
        oldPotionColors.put("effect.minecraft.conduit_power", 0x1DC2D1);
        oldPotionColors.put("effect.minecraft.darkness", 16750848); // TODO
        oldPotionColors.put("effect.minecraft.dolphins_grace", 0x88A3BE);
        oldPotionColors.put("effect.minecraft.fire_resistance", 0xE49A3A);
        oldPotionColors.put("effect.minecraft.glowing", 0x94A061);
        oldPotionColors.put("effect.minecraft.haste", 0xD9C043);
        oldPotionColors.put("effect.minecraft.health_boost", 0xF87D23);
        oldPotionColors.put("effect.minecraft.hero_of_the_village", 0x44FF44);
        oldPotionColors.put("effect.minecraft.hunger", 0x587653);
        oldPotionColors.put("effect.minecraft.instant_damage", 0x430A09);
        oldPotionColors.put("effect.minecraft.instant_health", 0xF82423);
        oldPotionColors.put("effect.minecraft.invisibility", 0x7F8392);
        oldPotionColors.put("effect.minecraft.jump_boost", 0x22FF4C);
        oldPotionColors.put("effect.minecraft.levitation", 0xCEFFFF);
        oldPotionColors.put("effect.minecraft.luck", 0x339900);
        oldPotionColors.put("effect.minecraft.mining_fatigue", 0x4A4217);
        oldPotionColors.put("effect.minecraft.nausea", 0x551D4A);
        oldPotionColors.put("effect.minecraft.night_vision", 0x1F1FA1);
        oldPotionColors.put("effect.minecraft.poison", 0x4E9331);
        oldPotionColors.put("effect.minecraft.regeneration", 0xCD5CAB);
        oldPotionColors.put("effect.minecraft.resistance", 0x99453A);
        oldPotionColors.put("effect.minecraft.saturation", 0xF82423);
        oldPotionColors.put("effect.minecraft.slow_falling", 0xFFEFD1);
        oldPotionColors.put("effect.minecraft.slowness", 0x5A6C81);
        oldPotionColors.put("effect.minecraft.speed", 0x7CAFC6);
        oldPotionColors.put("effect.minecraft.strength", 0x932423);
        oldPotionColors.put("effect.minecraft.unluck", 0xC0A44D); // Bad Luck
        oldPotionColors.put("effect.minecraft.water_breathing", 0x2E5299);
        oldPotionColors.put("effect.minecraft.weakness", 0x484D48);
        oldPotionColors.put("effect.minecraft.wither", 0x352A27);
    }

    // 0x292721 // ???

    @Shadow @Nullable private String translationKey;

    @Inject(method = "getColor", at = @At("RETURN"), cancellable = true)
    public void getColor$old$oldPotionColors(CallbackInfoReturnable<Integer> cir) {
        if (!OldAnimations.CONFIG.legacySettings.OLD_POTION_COLORS)
            return;
        if (translationKey == null || !oldPotionColors.containsKey(translationKey))
            return;
        cir.setReturnValue(oldPotionColors.get(translationKey));
    }
}
