package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.lexer.Token;
import ru.hse.spb.eliseeva.lexer.VariableSubstitutionLexer;

import java.util.List;

/**
 * Removes quotes, tokenize and substitute variables values.
 */
public class DoubleQuotedWordPart implements WordPart {
    private String value;
    private WordPartCreator wordPartCreator;

    DoubleQuotedWordPart(String value) {
        this.value = value;
        this.wordPartCreator = new CommonWordPartCreator();
    }

    @Override
    public String evaluate(Environment environment) throws LexerException {
        value = value.substring(1, value.length() - 1);
        List<Token> tokenList = new VariableSubstitutionLexer().tokenize(value);
        StringBuilder result = new StringBuilder();
        for (Token token : tokenList) {
            result.append(wordPartCreator.create(token).evaluate(environment));
        }
        return result.toString();
    }
}
