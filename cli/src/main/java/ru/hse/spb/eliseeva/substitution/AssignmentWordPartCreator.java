package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.lexer.Token;

/**
 * When creating a value to a new variable we should ignore the assignment and do all other substitutions.
 * Example: a=b=3 gives the variable a with the value b=3.
 */
public class AssignmentWordPartCreator implements WordPartCreator {
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
                return new StringWordPart(" ");
            case NEW_VARIABLE:
                return new StringWordPart(token.getValue());
        }
        return null;
    }
}
