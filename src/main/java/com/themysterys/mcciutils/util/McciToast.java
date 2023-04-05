package com.themysterys.mcciutils.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class McciToast implements Toast {
    public static final Identifier TEXTURE = new Identifier("mcciutils", "gui/toasts.png");
    private final Text title;
    private final List<OrderedText> lines;
    private final int v;
    private final int width;

    public McciToast(Type type, Text message) {
        this(type, getTextAsList(message), Math.min(200, Math.max(MinecraftClient.getInstance().textRenderer.getWidth(type.title), MinecraftClient.getInstance().textRenderer.getWidth(message)) + 35));
    }

    private McciToast(Type type, List<OrderedText> lines, int width) {
        this.title = Text.of(type.title);
        this.lines = lines;
        this.width = width;
        this.v = type.v;
    }

    private static List<OrderedText> getTextAsList(@Nullable Text text) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        return text == null ? List.of() : textRenderer.wrapLines(text, 160);
    }

    public static void add(ToastManager manager, Type type, Text message) {
        manager.add(new McciToast(type, message));
    }

    public Toast.Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int width = this.getWidth();
        int height = this.getHeight();
        if (width == 160 && this.lines.size() <= 1) {
            DrawableHelper.drawTexture(matrices, 0, 0, 0, this.v, width, height);
        } else {

            int length = Math.min(4, height - 28);
            this.drawPart(matrices, width, 0, 0, 28);

            for (int m = 28; m < height - length; m += 10) {
                this.drawPart(matrices, width, 16, m, Math.min(16, height - m - length));
            }

            this.drawPart(matrices, width, 32 - length, height - length, length);
        }

        if (this.lines == null) {
            manager.getClient().textRenderer.draw(matrices, this.title, 26.0F, 12.0F, -256);
        } else {
            manager.getClient().textRenderer.draw(matrices, this.title, 26.0F, 7.0F, -256);

            for (int line = 0; line < this.lines.size(); ++line) {
                manager.getClient().textRenderer.draw(matrices, this.lines.get(line), 26.0F, (float) (18 + line * 12), -1);
            }
        }
        return startTime < 5000L ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;

    }

    // Stole from SystemToast (Core MC code)
    private void drawPart(MatrixStack matrices, int width, int textureV, int y, int height) {
        int i = textureV == 0 ? 28 : 5;
        int j = Math.min(60, width - i);
        DrawableHelper.drawTexture(matrices, 0, y, 0, this.v + textureV, i, height);
        for (int k = i; k < width - j; k += 32) {
            DrawableHelper.drawTexture(matrices, k, y, 32, this.v + textureV, Math.min(64, width - k - j), height);
        }

        DrawableHelper.drawTexture(matrices, width - j, y, 160 - j, this.v + textureV, j, height);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return 20 + this.lines.size() * 12;
    }

    public enum Type {
        UPDATE("New Update!", 0),
        FRIEND_JOIN("Friend Joined!", 32),
        FRIEND_LEAVE("Friend Left!", 32),
        QUEST_COMPLETE("Quest Completed!", 64),
        BADGE_ACHIEVED("Badge Achieved!", 96);

        final String title;
        final int v;

        Type(String title, int v) {
            this.title = title;
            this.v = v;
        }

    }
}
