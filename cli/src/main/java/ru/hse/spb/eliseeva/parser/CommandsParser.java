package ru.hse.spb.eliseeva.parser;

import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.lexer.CommandLexer;
import ru.hse.spb.eliseeva.lexer.Token;
import ru.hse.spb.eliseeva.substitution.AssignmentWordPartCreator;
import ru.hse.spb.eliseeva.substitution.CommonWordPartCreator;
import ru.hse.spb.eliseeva.substitution.Word;
import ru.hse.spb.eliseeva.substitution.WordPartCreator;

import java.util.*;

/**
 * Parser interface implementation.
 */
public class CommandsParser implements Parser{
    private WordPartCreator wordPartCreator;

    public CommandsParser() {
        wordPartCreator = new CommonWordPartCreator();
    }

    private Word currentWord;
    private List<Word> commandParts;
    private Executable previousCommand;

    @Override
    public Executable parse(List<Token> tokens) throws ParserException {
        commandParts = new ArrayList<>();
        currentWord = Word.getEmptyWord();
        previousCommand = Executable.getEmptyCommandExecutable();

        for (Token token : tokens) {
            switch (token.getType()) {
                case SPACE:
                    parseSpace();
                    break;
                case TEXT:
                    currentWord.addPart(wordPartCreator.create(token));
                    break;
                case PIPE:
                    parsePipe();
                    break;
                case SINGLE_QUOTED:
                    currentWord.addPart(wordPartCreator.create(token));
                    break;
                case DOUBLE_QUOTED: {
                    currentWord.addPart(wordPartCreator.create(token));
                    break;
                }
                case NEW_VARIABLE: {
                    try {
                        return parseNewVariable(token);
                    } catch (LexerException e) {
                        throw new ParserException("Bad new variable value: " + token.getValue());
                    }
                }
                case OLD_VARIABLE:
                    currentWord.addPart(wordPartCreator.create(token));
                    break;
            }
        }
        if (currentWord.isNotEmpty()) {
            commandParts.add(currentWord);
        }
        return new Executable(commandParts, previousCommand);
    }

    private Executable parseNewVariable(Token token) throws LexerException {
        String assignmentString = token.getValue();
        int index = getEqualityIndex(assignmentString);

        String left = assignmentString.substring(0, index);
        String right = assignmentString.substring(index + 1);

        List<Token> leftTokens = new CommandLexer().tokenize(left);
        List<Token> rightTokens = new CommandLexer().tokenize(right);

        Word assignmentWord = Word.getEmptyWord();
        assignmentWord.addPart(wordPartCreator.create(token));

        List<Word> assignmentParts = new ArrayList<>();
        assignmentParts.add(assignmentWord);
        assignmentParts.add(tokensToWord(leftTokens));
        assignmentParts.add(tokensToWord(rightTokens));

        return new Executable(assignmentParts, Executable.getEmptyCommandExecutable());
    }

    private void parseSpace() {
        if (currentWord.isNotEmpty()) {
            commandParts.add(currentWord);
        }
        currentWord = Word.getEmptyWord();
    }

    private void parsePipe() {
        if (currentWord.isNotEmpty()) {
            commandParts.add(currentWord);
        }
        previousCommand = new Executable(commandParts, previousCommand);
        currentWord = Word.getEmptyWord();
        commandParts = new ArrayList<>();
    }

    private Word tokensToWord(List<Token> tokens) {
        WordPartCreator assignmentWordPartCreator = new AssignmentWordPartCreator();
        Word result = Word.getEmptyWord();
        for (Token token : tokens) {
            result.addPart(assignmentWordPartCreator.create(token));
        }
        return result;
    }

    private int getEqualityIndex(String assignment) {
        for (int i = 0; i < assignment.length(); i++) {
            if (assignment.charAt(i) == '=') {
                return i;
            }
        }
        return -1;
    }
}