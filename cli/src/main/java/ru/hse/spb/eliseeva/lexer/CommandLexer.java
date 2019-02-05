package ru.hse.spb.eliseeva.lexer;

import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of lexer interface for the interpreter.
 */
public class CommandLexer implements Lexer {
    private static final char PIPE = '|';
    private static final char SINGLE_QUOTE = '\'';
    private static final char DOUBLE_QUOTE = '"';
    private static final char VARIABLE_SYMBOL = '$';
    private static final char VARIABLE_ASSIGNMENT = '=';
    private static final char SPACE = ' ';

    private List<Token> tokenList = new ArrayList<>();
    private StringBuilder currentToken;

    @Override
    public List<Token> tokenize(String commands) throws LexerException {
        tokenList = new ArrayList<>();
        currentToken = new StringBuilder();
        for (int i = 0; i < commands.length();) {
            char symbol = commands.charAt(i);
            switch (symbol) {
                case PIPE :
                    i = tokenizePipe(i);
                    break;
                case DOUBLE_QUOTE :
                    i = tokenizeDoubleQuoted(i, commands);
                    break;
                case SINGLE_QUOTE :
                    i = tokenizeSingleQuoted(i, commands);
                    break;
                case VARIABLE_SYMBOL: {
                    i = tokenizeOldVariable(i, commands);
                    break;
                }
                case VARIABLE_ASSIGNMENT: {
                    if (isValidVariableName(commands.substring(0, i))
                    && isValidVariableValue(commands.substring(i + 1))) {
                        tokenList.add(new Token(Token.Type.NEW_VARIABLE, commands));
                    } else {
                        throw new LexerException( "Not valid assignment: " + commands);
                    }
                    return tokenList;
                }
                case SPACE: {
                    i = tokenizeSpace(i, commands);
                    break;
                }
                default: {
                    currentToken.append(symbol);
                    i++;
                }
            }
        }
        addCurrentToken(currentToken);
        return tokenList;
    }

    private int tokenizePipe(int i) {
        currentToken = addCurrentToken(currentToken);
        tokenList.add(new Token(Token.Type.PIPE, "|"));
        return i + 1;
    }

    private int tokenizeDoubleQuoted(int i, String commands) throws LexerException {
        currentToken = addCurrentToken(currentToken);
        return tokenizeQuotes(i, Token.Type.DOUBLE_QUOTED, commands);
    }

    private int tokenizeSingleQuoted(int i, String commands) throws LexerException {
        currentToken = addCurrentToken(currentToken);
        return tokenizeQuotes(i, Token.Type.SINGLE_QUOTED, commands);
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

    private int tokenizeSpace(int i, String commands) {
        currentToken = addCurrentToken(currentToken);
        while (i < commands.length() && commands.charAt(i) == SPACE) {
            i++;
        }
        if (i == commands.length()) {
            return i;
        }
        if (!tokenList.isEmpty()) {
            tokenList.add(new Token(Token.Type.SPACE, " "));
        }
        return i;
    }


    private int tokenizeQuotes(int i, Token.Type type, String commands) throws LexerException {
        char symbol = commands.charAt(i);
        StringBuilder text = new StringBuilder();
        text.append(symbol);
        i++;
        while (i < commands.length() && commands.charAt(i) != symbol) {
            text.append(commands.charAt(i));
            i++;
        }
        if (i == commands.length()) {
            throw new LexerException("Bad quotes: " + commands);
        }
        text.append(commands.charAt(i));
        i++;
        tokenList.add(new Token(type, text.toString()));
        return i;
    }

    private StringBuilder addCurrentToken(StringBuilder currentToken) {
        if (!currentToken.toString().equals("")) {
            tokenList.add(new Token(Token.Type.TEXT, currentToken.toString()));
            currentToken = new StringBuilder();
        }
        return currentToken;
    }


    private boolean isValidVariableValue(String value) throws LexerException {
        List<Token> oldTokens = tokenList;
        List<Token> tokens = tokenize(value);
        tokenList = oldTokens;
        for (Token token : tokens) {
            if (token.getType() == Token.Type.SPACE
            || token.getType() == Token.Type.PIPE) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidVariableName(String name) {
        for (char c : name.toCharArray()) {
            if (!isValidVariableSymbol(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidVariableSymbol(char symbol) {
        return symbol >= 'a' && symbol <= 'z'
            || symbol >= 'A' && symbol <= 'Z'
            || symbol >= '0' && symbol <= '9'
            || symbol == '_';
    }
}
