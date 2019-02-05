package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.lexer.CommandLexer;
import ru.hse.spb.eliseeva.lexer.Token;

import java.util.List;

/**
 * Removes quotes, tokenize and substitute variables values.
 */
public class DoubleQuotedWordPart implements WordPart {
    private String value;
    private WordPartCreator wordPartCreator;

    DoubleQuotedWordPart(String value) {
        this.value = value;
        this.wordPartCreator = new DoubleQuotedWordPartCreator();
    }

    @Override
    public String evaluate(Environment environment) throws LexerException {
        value = value.substring(1, value.length() - 1);
        List<Token> tokenList = new CommandLexer().tokenize(value);
        StringBuilder result = new StringBuilder();
        for (Token token : tokenList) {
            result.append(wordPartCreator.create(token).evaluate(environment));
        }
        return result.toString();
    }
}
