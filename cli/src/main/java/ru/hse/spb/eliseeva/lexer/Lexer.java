package ru.hse.spb.eliseeva.lexer;

import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.List;

/**
 * Interface to create tokens from the given command.
 */
public interface Lexer {
    List<Token> tokenize(String commands) throws LexerException;

}
