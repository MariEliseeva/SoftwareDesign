package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.LexerException;

/**
 * Interface representing a part of a string that can be text, variable, quoted text etc.
 * We can transform in to usual string by making all needed substitutions.
 * Differs from token: we can't evaluate the token.
 */
public interface WordPart {
    String evaluate(Environment environment) throws LexerException;
}
