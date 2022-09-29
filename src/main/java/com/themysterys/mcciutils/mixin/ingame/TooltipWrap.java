package com.themysterys.mcciutils.mixin.ingame;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.List;

@Mixin(Screen.class)
public class TooltipWrap<T> {

    @Shadow
    public int width;
    @Shadow
    protected TextRenderer textRenderer;

    @ModifyArg(
            method = "renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Ljava/util/List;II)V",
            at = @At(
                    value = "INVOKE",
                    target = ""
            ),
            index = 1
    )
    private List<OrderedText> wrapText(List<? extends OrderedText> lines) {
        List<OrderedText> newLines = new ArrayList<>();
        for (OrderedText line : lines) {
            newLines.addAll(textRenderer.wrapLines(StringVisitable.plain(line.toString()), this.width - 30));
        }
        return newLines;
    }
}
