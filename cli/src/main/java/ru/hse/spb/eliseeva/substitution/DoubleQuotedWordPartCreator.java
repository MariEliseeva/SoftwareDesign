package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.lexer.Token;

/**
 * Does variable substitutions inside double quoted part.
 */
public class DoubleQuotedWordPartCreator implements WordPartCreator {
    @Override
    public WordPart create(Token token) {
        if (token.getType() == Token.Type.OLD_VARIABLE) {
            return new VariableWordPart(token.getValue());
        }
        return new StringWordPart(token.getValue());
    }
}
