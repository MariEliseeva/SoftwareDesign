package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;

/**
 * Usual part of word without quotes or variables.
 */
public class StringWordPart implements WordPart {
    private String value;

    StringWordPart(String value) {
        this.value = value;
    }

    @Override
    public String evaluate(Environment environment) {
        return value;
    }
}
