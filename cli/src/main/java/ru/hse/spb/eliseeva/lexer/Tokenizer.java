package ru.hse.spb.eliseeva.lexer;

import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to perform tokenizing actions for all types of lexers.
 */
class Tokenizer {
    static final char PIPE = '|';
    static final char SINGLE_QUOTE = '\'';
    static final char DOUBLE_QUOTE = '"';
    static final char VARIABLE_SYMBOL = '$';
    static final char VARIABLE_ASSIGNMENT = '=';
    static final char SPACE = ' ';

    private List<Token> tokenList;
    private StringBuilder currentToken;

    Tokenizer() {
        tokenList = new ArrayList<>();
        currentToken = new StringBuilder();
    }

    List<Token> getTokenList() {
        return tokenList;
    }

    int tokenizePipe(int i) {
        addCurrentToken();
        tokenList.add(new Token(Token.Type.PIPE, "|"));
        return i + 1;
    }

    void tokenizeVariableAssignment(String command, int i, Lexer lexer) throws LexerException {
        if (isValidVariableName(command.substring(0, i))
                && isValidVariableValue(command.substring(i + 1), lexer)) {
            tokenList.add(new Token(Token.Type.NEW_VARIABLE, command));
        } else {
            throw new LexerException( "Not valid assignment: " + command);
        }
    }

    int tokenizeDoubleQuoted(int i, String commands) throws LexerException {
        addCurrentToken();
        return tokenizeQuotes(i, Token.Type.DOUBLE_QUOTED, commands);
    }

    int tokenizeSingleQuoted(int i, String commands) throws LexerException {
        addCurrentToken();
        return tokenizeQuotes(i, Token.Type.SINGLE_QUOTED, commands);
    }

    int tokenizeOldVariable(int i, String commands) {
        addCurrentToken();
        i++;
        StringBuilder text = new StringBuilder();
        while (i < commands.length() && isValidVariableSymbol(commands.charAt(i))) {
            text.append(commands.charAt(i));
            i++;
        }
        tokenList.add(new Token(Token.Type.OLD_VARIABLE, text.toString()));
        return i;
    }

    void tokenizeSpace() {
        addCurrentToken();
        tokenList.add(new Token(Token.Type.SPACE, " "));
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

    void addCurrentToken() {
        if (currentToken.length() != 0) {
            tokenList.add(new Token(Token.Type.TEXT, currentToken.toString()));
            currentToken = new StringBuilder();
        }
    }


    private boolean isValidVariableValue(String value, Lexer lexer) throws LexerException {
        List<Token> oldTokens = tokenList;
        List<Token> tokens = lexer.tokenize(value);
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
        return name.matches("[a-zA-Z0-9_]+");
    }

    private boolean isValidVariableSymbol(char symbol) {
        return symbol >= 'a' && symbol <= 'z'
                || symbol >= 'A' && symbol <= 'Z'
                || symbol >= '0' && symbol <= '9'
                || symbol == '_';
    }

    void addSymbol(char symbol) {
        currentToken.append(symbol);
    }
}
