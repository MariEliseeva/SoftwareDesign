package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.lexer.Token;

/**
 * Creates a word part by the given token.
 */
public class WordPartCreator {
    public static WordPart create(Token token){
        switch (token.getType()) {
            case TEXT:
                return new StringWordPart(token.getValue());
            case SINGLE_QUOTED:
                return new SingleQuotedWordPart(token.getValue());
            case DOUBLE_QUOTED:
                return new DoubleQuotedWordPart(token.getValue());
            case OLD_VARIABLE:
                return new VariableWordPart(token.getValue());
            case SPACE:
                return new StringWordPart(" ");
            case NEW_VARIABLE:
                return new StringWordPart("=");
            default:
                return null;
        }
    }
}
