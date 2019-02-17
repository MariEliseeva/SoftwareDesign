package ru.hse.spb.eliseeva.lexer;

import java.util.ArrayList;
import java.util.List;

public class VariableSubstitutionLexer implements Lexer {
    private static final char VARIABLE_SYMBOL = '$';

    private List<Token> tokenList = new ArrayList<>();
    private StringBuilder currentToken;

    @Override
    public List<Token> tokenize(String commands) {
        tokenList = new ArrayList<>();
        currentToken = new StringBuilder();
        for (int i = 0; i < commands.length();) {
            char symbol = commands.charAt(i);
            if (symbol == VARIABLE_SYMBOL) {
                i = tokenizeOldVariable(i, commands);
            } else {
                currentToken.append(symbol);
                i++;
            }
        }
        addCurrentToken(currentToken);
        return tokenList;
    }

   private int tokenizeOldVariable(int i, String commands) {
        currentToken = addCurrentToken(currentToken);
        i++;
        StringBuilder text = new StringBuilder();
        while (i < commands.length() && isValidVariableSymbol(commands.charAt(i))) {
            text.append(commands.charAt(i));
            i++;
        }
        tokenList.add(new Token(Token.Type.OLD_VARIABLE, text.toString()));
        return i;
    }

    private StringBuilder addCurrentToken(StringBuilder currentToken) {
        if (!currentToken.toString().equals("")) {
            tokenList.add(new Token(Token.Type.TEXT, currentToken.toString()));
            currentToken = new StringBuilder();
        }
        return currentToken;
    }

    private boolean isValidVariableSymbol(char symbol) {
        return symbol >= 'a' && symbol <= 'z'
                || symbol >= 'A' && symbol <= 'Z'
                || symbol >= '0' && symbol <= '9'
                || symbol == '_';
    }
}
