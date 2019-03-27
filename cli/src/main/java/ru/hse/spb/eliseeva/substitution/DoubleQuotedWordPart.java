package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.EnvironmentException;
import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.lexer.Token;
import ru.hse.spb.eliseeva.lexer.VariableSubstitutionLexer;

import java.util.List;

/**
 * Removes quotes, tokenize with lexer, which finds variables, and substitutes variables values.
 */
public class DoubleQuotedWordPart implements WordPart {
    private String value;

    DoubleQuotedWordPart(String value) {
        this.value = value;
    }

    @Override
    public String evaluate(Environment environment) throws EnvironmentException {
        value = value.substring(1, value.length() - 1);
        List<Token> tokenList = new VariableSubstitutionLexer().tokenize(value);
        StringBuilder result = new StringBuilder();
        for (Token token : tokenList) {
            result.append(WordPartCreator.create(token).evaluate(environment));
        }
        return result.toString();
    }
}
