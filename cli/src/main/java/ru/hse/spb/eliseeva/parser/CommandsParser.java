package ru.hse.spb.eliseeva.parser;

import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.lexer.AssignmentLexer;
import ru.hse.spb.eliseeva.lexer.Token;
import ru.hse.spb.eliseeva.substitution.Word;
import ru.hse.spb.eliseeva.substitution.WordPartCreator;

import java.util.*;

/**
 * Parser interface implementation.
 */
public class CommandsParser implements Parser{
    private Word currentWord;
    private List<Word> commandParts;
    private List<Executable> commands;

    /**
     * Goes through all tokens, transform them ro either a word part or a whole word,
     * creates an Executable object when tokens corresponding to the parsed command are finished:
     * <ul>
     *     <li>when meets text, quoted text or old variable creates new WordPart from it
     *        and adds it to the currently parsed word<li/>
     *     <li>when meets space finishes the word which is parsed and starts a new word</li>
     *     <li>when meets pipe create new Executable using parsed words (command name and args), starts new command</li>
     *     <li>for new variable creates an Executable representing assignment command using lexer to tokenize
     *     new variable value and special WordPartCreator for this case.</li>
     * <ul/>
     *
     * @param tokens tokens to parse
     * @return list of executable objects, containing the information about commands that van be executed
     * @throws ParserException when pipe used incorrect (like lines "command |" or "| command" or "|")
     * or new variable assignment has incorrect value.
     */
    @Override
    public List<Executable> parse(List<Token> tokens) throws ParserException {
        commandParts = new ArrayList<>();
        currentWord = Word.getEmptyWord();
        commands = new ArrayList<>();

        for (Token token : tokens) {
            switch (token.getType()) {
                case SPACE:
                    parseSpace();
                    break;
                case TEXT:
                    currentWord.addPart(WordPartCreator.create(token));
                    break;
                case PIPE:
                    parsePipe();
                    break;
                case SINGLE_QUOTED:
                    currentWord.addPart(WordPartCreator.create(token));
                    break;
                case DOUBLE_QUOTED: {
                    currentWord.addPart(WordPartCreator.create(token));
                    break;
                }
                case NEW_VARIABLE: {
                    try {
                        commands.add(parseNewVariable(token));
                        return commands;
                    } catch (LexerException e) {
                        throw new ParserException("Bad new variable value: " + token.getValue());
                    }
                }
                case OLD_VARIABLE:
                    currentWord.addPart(WordPartCreator.create(token));
                    break;
            }
        }
        parsePipe();
        return commands;
    }

    private Executable parseNewVariable(Token token) throws LexerException {
        String assignmentString = token.getValue();
        int index = assignmentString.indexOf('=');

        String left = assignmentString.substring(0, index);
        String right = assignmentString.substring(index + 1);

        List<Token> leftTokens = new AssignmentLexer().tokenize(left);
        List<Token> rightTokens = new AssignmentLexer().tokenize(right);

        Word assignmentWord = Word.getEmptyWord();
        assignmentWord.addPart(WordPartCreator.create(token));

        List<Word> assignmentParts = new ArrayList<>();
        assignmentParts.add(assignmentWord);
        assignmentParts.add(tokensToWord(leftTokens));
        assignmentParts.add(tokensToWord(rightTokens));

        return new Executable(assignmentParts);
    }

    private void parseSpace() {
        if (currentWord.isNotEmpty()) {
            commandParts.add(currentWord);
        }
        currentWord = Word.getEmptyWord();
    }

    private void parsePipe() throws ParserException {
        if (currentWord.isNotEmpty()) {
            commandParts.add(currentWord);
        }
        if (commandParts.isEmpty()) {
            throw new ParserException("Wrong pipe usage.");
        }
        commands.add(new Executable(commandParts));
        currentWord = Word.getEmptyWord();
        commandParts = new ArrayList<>();
    }

    private Word tokensToWord(List<Token> tokens) {
        Word result = Word.getEmptyWord();
        for (Token token : tokens) {
            result.addPart(WordPartCreator.create(token));
        }
        return result;
    }
}