package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.EnvironmentException;

/**
 * Interface representing a part of a string that can be text, variable, quoted text etc.
 * We can transform in to usual string by making all needed substitutions.
 * Differs from token: we can't evaluate the token.
 */
public interface WordPart {
    /**
     * Does variable substitution, quotes replacing and so on
     * @param environment environment to take variables' values if needed
     * @return string without quotes and variables
     * @throws EnvironmentException if no variable found
     */
    String evaluate(Environment environment) throws EnvironmentException;
}
