package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;

/**
 * Variable that needs to be substituted ($abc etc.).
 */
public class VariableWordPart implements WordPart {
    private String value;

    VariableWordPart(String value) {
        this.value = value;
    }

    @Override
    public String evaluate(Environment environment) {
        return environment.getVariableValue(value);
    }
}
