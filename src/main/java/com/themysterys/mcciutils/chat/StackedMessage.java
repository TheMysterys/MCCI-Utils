package com.themysterys.mcciutils.chat;

import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class StackedMessage {

    private static final Style OCCURRENCES_STYLE = Style.EMPTY
            .withStrikethrough(false)
            .withColor(Formatting.GRAY)
            .withFont(Style.DEFAULT_FONT_ID);

    private final Text originalMessage;
    private int occurrences;

    public StackedMessage(Text originalMessage) {
        this.originalMessage = originalMessage;
        this.occurrences = 1;
    }

    public void incrementOccurrences() {
        occurrences++;
    }

    public void resetOccurrences() {
        occurrences = 1;
    }

    public Text getOriginalMessage() {
        return originalMessage;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public Text getModifiedMessage() {
        var appendedSpace = originalMessage.getString().endsWith(" ") ? "" : " ";
        var occurrencesText = Text.of(appendedSpace + "(" + this.getOccurrences() + ")")
                .copyContentOnly()
                .setStyle(OCCURRENCES_STYLE);

        var content = originalMessage.copy();
        if (content.getString().isEmpty()) {
            content = originalMessage.copy();
        }

        return content.append(occurrencesText);
    }

    public boolean equals(Text text) {
        return this.getOriginalMessage().getString().equals(text.getString());
    }

    public boolean equals(StackedMessage message) {
        return this.getOriginalMessage().getString().equals(message.getOriginalMessage().getString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof StackedMessage message) {
            // We are comparing to an existing compacted message
            return this.equals(message);
        } else if (obj instanceof Text text) {
            // We are comparing to a text object where we are unsure if it has been compacted or not yet
            return this.equals(text);
        }

        return false;
    }
}
