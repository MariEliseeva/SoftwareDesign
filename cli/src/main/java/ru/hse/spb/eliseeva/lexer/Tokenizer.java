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

    /**
     * Creates list for collecting tokens and a string representing partly-parsed token.
     */
    Tokenizer() {
        tokenList = new ArrayList<>();
        currentToken = new StringBuilder();
    }

    /**
     * Returns list of all parsed tokens
     * @return parsed tokens
     */
    List<Token> getTokenList() {
        return tokenList;
    }

    /**
     * Add token representing the text before pipe and token representing pipe.
     */
    void tokenizePipe() {
        addTextToken();
        tokenList.add(new Token(Token.Type.PIPE, "|"));
    }

    /**
     * Checks correctness of assignment and adds assignment token
     * @param command command to separate variable name from value
     * @param lexer lexer to check if value contains space or pipe tokens
     * (can't only check if the string contains ' ' or '|' symbols, because if they are inside quotes they are correct)
     * @throws LexerException if assignment is not valid
     */
    void tokenizeVariableAssignment(String command, Lexer lexer) throws LexerException {
        int equalityIndex = command.indexOf('=');
        if (isValidVariableName(command.substring(0, equalityIndex))
                && isValidVariableValue(command.substring(equalityIndex + 1), lexer)) {
            tokenList.add(new Token(Token.Type.NEW_VARIABLE, command));
        } else {
            throw new LexerException( "Not valid assignment: " + command);
        }
    }

    /**
     * Goes through the string until finds the closing quote
     * @param i index to start
     * @param commands command to tokenize
     * @return index position after finding closing quote
     * @throws LexerException if no paired quote found
     */
    int tokenizeDoubleQuoted(int i, String commands) throws LexerException {
        addTextToken();
        return tokenizeQuotes(i, Token.Type.DOUBLE_QUOTED, commands);
    }

    /**
     * Goes through the string until finds the closing quote
     * @param i index to start
     * @param commands command to tokenize
     * @return return index position after finding closing quote
     * @throws LexerException if no paired quote found
     */
    int tokenizeSingleQuoted(int i, String commands) throws LexerException {
        addTextToken();
        return tokenizeQuotes(i, Token.Type.SINGLE_QUOTED, commands);
    }

    /**
     * Goes through the string until symbol is valid for variable name
     * @param i index to start
     * @param commands command to tokenize
     * @return index position after finding the end of variable name
     */
    int tokenizeOldVariable(int i, String commands) {
        addTextToken();
        i++;
        StringBuilder text = new StringBuilder();
        while (i < commands.length() && isValidVariableSymbol(commands.charAt(i))) {
            text.append(commands.charAt(i));
            i++;
        }
        tokenList.add(new Token(Token.Type.OLD_VARIABLE, text.toString()));
        return i;
    }

    /**
     * Adds space token
     */
    void tokenizeSpace() {
        addTextToken();
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

    /**
     * Adds text token
     */
    void addTextToken() {
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
        return Character.isLetter(symbol) || Character.isDigit(symbol);
    }

    /**
     * Adds one symbol to the currently parsed text token
     * @param symbol symbol to add
     */
    void addSymbol(char symbol) {
        currentToken.append(symbol);
    }
}
