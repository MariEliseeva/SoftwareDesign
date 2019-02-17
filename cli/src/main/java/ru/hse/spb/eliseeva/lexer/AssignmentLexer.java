package ru.hse.spb.eliseeva.lexer;

import ru.hse.spb.eliseeva.exceptions.LexerException;
import java.util.List;

public class AssignmentLexer extends CommandLexer {

    /**
     * Goes through all symbols, transform them to tokens of needed token type using package-private Tokenizer class:
     * <ul>
     *     <li>for quoted text reads until paired quote;
     *     <li>for old variable reads until symbol is appropriate for the name;
     *     <li>for other symbols creates text token;
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
                default: {
                    tokenizer.addSymbol(symbol);
                    i++;
                }
            }
        }
        tokenizer.addCurrentToken();
        return tokenizer.getTokenList();
    }
}
