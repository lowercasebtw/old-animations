package btw.lowercase.oldanimations.mixin.screen.widget;

import btw.lowercase.oldanimations.OldAnimations;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PressableWidget.class)
public abstract class MixinPressableWidget extends ClickableWidget {
    public MixinPressableWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @ModifyConstant(method = "renderWidget", constant = @Constant(intValue = 0xffffff))
    private int renderWidget$old$textColor(int constant) {
        if (OldAnimations.CONFIG.legacySettings.OLD_BUTTON_TEXT_COLOR) {
            return !active ? 0xe0e0e0 : (hovered ? 0xffffa0 : 0xe0e0e0);
        } else {
            return constant;
        }
    }
}
