package ru.hse.spb.eliseeva.lexer;

import java.util.List;

/**
 * Class to tokenize a string which was in double quotes before and now need a variable substitution.
 */
public class VariableSubstitutionLexer implements Lexer {

    /**
     * If meets old variable creates needed token, otherwise creates text token.
     * @param string string to tokenize into variables and texts
     * @return tokens representing the given string
     */
    @Override
    public List<Token> tokenize(String string) {
        Tokenizer tokenizer = new Tokenizer();
        for (int i = 0; i < string.length();) {
            char symbol = string.charAt(i);
            if (symbol == Tokenizer.VARIABLE_SYMBOL) {
                i = tokenizer.tokenizeOldVariable(i, string);
            } else {
                tokenizer.addSymbol(symbol);
                i++;
            }
        }
        tokenizer.addCurrentToken();
        return tokenizer.getTokenList();
    }
}
