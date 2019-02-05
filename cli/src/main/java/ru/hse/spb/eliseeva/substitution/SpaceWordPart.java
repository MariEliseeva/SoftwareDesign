package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;

/**
 * Returns space.
 */
public class SpaceWordPart implements WordPart {
    SpaceWordPart(){}

    @Override
    public String evaluate(Environment environment) {
        return " ";
    }
}
