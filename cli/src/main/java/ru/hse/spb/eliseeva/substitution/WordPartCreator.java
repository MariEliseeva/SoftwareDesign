package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.lexer.Token;

/**
 * Creates a word part by the given token.
 */
public interface WordPartCreator {
    WordPart create(Token token);
}
