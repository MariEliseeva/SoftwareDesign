package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;

/**
 * Part of a word representing the equality sign.
 */
public class AssignmentPart implements WordPart {
    @Override
    public String evaluate(Environment environment) {
        return "=";
    }
}
