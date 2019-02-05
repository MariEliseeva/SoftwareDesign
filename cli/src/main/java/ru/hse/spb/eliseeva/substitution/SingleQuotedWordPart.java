package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;

/**
 * To evaluate single quoted text just removes quotes.
 */
public class SingleQuotedWordPart implements WordPart {
    private String value;

    SingleQuotedWordPart(String value) {
        this.value = value;
    }

    @Override
    public String evaluate(Environment environment) {
        return value.substring(1, value.length() - 1);
    }
}
