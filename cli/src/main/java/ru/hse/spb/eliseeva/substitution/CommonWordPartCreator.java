package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.lexer.Token;

/**
 * Does the substitutions and quotes replacement for a non-assignment token.
 */
public class CommonWordPartCreator implements WordPartCreator {

    @Override
    public WordPart create(Token token) {
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
                return new SpaceWordPart();
            case NEW_VARIABLE:
                return new AssignmentPart();
        }
        return null;
    }
}
