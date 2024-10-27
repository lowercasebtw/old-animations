package btw.lowercase.oldanimations.mixin.screen.widget;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SliderWidget.class)
public abstract class MixinSliderWidget extends ClickableWidget {
    public MixinSliderWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @ModifyConstant(method = "renderWidget", constant = @Constant(intValue = 0xffffff))
    public int renderWidget$old$textColor(int constant) {
        return OldAnimations.CONFIG.legacySettings.OLD_BUTTON_TEXT_COLOR ? (!active ? 0xe0e0e0 : (hovered ? 0xffffa0 : 0xe0e0e0)) : constant;
    }
}
