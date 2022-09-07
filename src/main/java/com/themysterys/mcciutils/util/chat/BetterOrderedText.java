package com.themysterys.mcciutils.util.chat;

import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;

public class BetterOrderedText {
    public static String getString(OrderedText text) {
        var visitor = new OrderedTextStringVisitor();
        text.accept(visitor);

        return visitor.getString();
    }

    private static class OrderedTextStringVisitor implements CharacterVisitor {
        private final StringBuilder builder = new StringBuilder();

        @Override
        public boolean accept(int index, Style style, int codePoint) {
            builder.appendCodePoint(codePoint);
            return true;
        }

        public String getString() {
            return builder.toString();
        }
    }
}
