package ru.hse.spb.eliseeva.lexer;

import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.List;

/**
 * Implementation of lexer interface for the interpreter.
 */
public class CommandLexer implements Lexer {
    /**
     * Goes through all symbols, transform them to tokens of needed token type using package-private Tokenizer class:
     * <ul>
     *     <li>for quoted text reads until paired quote;
     *     <li>for old variable reads until symbol is appropriate for the name;
     *     <li>for new variable check that is is the only command;
     *     <li>for space, pipe or usual text just creates needed token;
     * </ul>
     * @param commands line of commands to transform to tokens
     * @return list of tokens for the given commands
     * @throws LexerException thrown if quotes or variable assignment are not correct
     */
    @Override
    public List<Token> tokenize(String commands) throws LexerException {
        Tokenizer tokenizer = new Tokenizer();
        for (int i = 0; i < commands.length();) {
            char symbol = commands.charAt(i);
            switch (symbol) {
                case Tokenizer.PIPE :
                    tokenizer.tokenizePipe();
                    i++;
                    break;
                case Tokenizer.DOUBLE_QUOTE :
                    i = tokenizer.tokenizeDoubleQuoted(i, commands);
                    break;
                case Tokenizer.SINGLE_QUOTE :
                    i = tokenizer.tokenizeSingleQuoted(i, commands);
                    break;
                case Tokenizer.VARIABLE_SYMBOL: {
                    i = tokenizer.tokenizeOldVariable(i, commands);
                    break;
                }
                case Tokenizer.VARIABLE_ASSIGNMENT: {
                    if (tokenizer.getTokenList().isEmpty()) {
                        tokenizer.tokenizeVariableAssignment(commands, this);
                        return tokenizer.getTokenList();
                    } else {
                        throw new LexerException( "Not valid assignment: " + commands);
                    }
                }
                case Tokenizer.SPACE: {
                    tokenizer.tokenizeSpace();
                    i++;
                    break;
                }
                default: {
                    tokenizer.addSymbol(symbol);
                    i++;
                }
            }
        }
        tokenizer.addTextToken();
        return tokenizer.getTokenList();
    }
}
