package ru.hse.spb.eliseeva.parser;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.commands.CommandEcho;
import ru.hse.spb.eliseeva.exceptions.EnvironmentException;
import ru.hse.spb.eliseeva.lexer.Token;
import ru.hse.spb.eliseeva.substitution.Word;
import ru.hse.spb.eliseeva.substitution.WordPartCreator;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RawCommandTest {

    @Test
    public void constructCommandTest() throws EnvironmentException {
        Word commandName = Word.getEmptyWord();
        commandName.addPart(WordPartCreator.create(new Token(Token.Type.TEXT, "echo")));
        Word commandArgument = Word.getEmptyWord();
        commandArgument.addPart(WordPartCreator.create(new Token(Token.Type.DOUBLE_QUOTED, "\"35\"")));
        commandArgument.addPart(WordPartCreator.create(new Token(Token.Type.TEXT, "4")));
        RawCommand rawCommand = new RawCommand(Arrays.asList(commandName, commandArgument));
        System.out.println(rawCommand.getName().evaluate(new Environment()));
        assertEquals(CommandEcho.class, rawCommand.constructCommand(new Environment()).getClass());
    }
}