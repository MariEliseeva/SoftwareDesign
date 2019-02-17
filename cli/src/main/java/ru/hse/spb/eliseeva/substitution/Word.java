package ru.hse.spb.eliseeva.substitution;

import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.ArrayList;
import java.util.List;

/**
 * A group of word parts, which is also a word part for the bigger word.
 */
public class Word implements WordPart{
    private List<WordPart> argumentParts = new ArrayList<>();

    private Word(){}

    /**
     * Add a new part to exeisting parts of word (quoted text, some old variable, usual test etc.)
     * @param wordPart new part of word to add
     */
    public void addPart(WordPart wordPart){
        argumentParts.add(wordPart);
    }

    /**
     * Checks if the word is empty or not
     * @return true if there are no parts in the word
     */
    public boolean isNotEmpty() {
        return argumentParts.size() != 0;
    }

    /**
     * Creates word without any part.
     * @return empty word
     */
    public static Word getEmptyWord() {
        return new Word();
    }

    @Override
    public String evaluate(Environment environment) throws LexerException {
        StringBuilder result = new StringBuilder();
        for (WordPart argumentPart : argumentParts) {
            result.append(argumentPart.evaluate(environment));
        }
        return result.toString();
    }
}
