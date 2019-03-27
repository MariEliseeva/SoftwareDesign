package ru.hse.spb.eliseeva.lexer;

import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.List;

/**
 * Interface to create tokens from the given command.
 */
public interface Lexer {
    /**
     * Creates tokens for the given string
     * @param commands string to tokenize
     * @return  list of tokens created for the given string
     * @throws LexerException tokenizing problems, like bad quotes.
     */
    List<Token> tokenize(String commands) throws LexerException;
}
