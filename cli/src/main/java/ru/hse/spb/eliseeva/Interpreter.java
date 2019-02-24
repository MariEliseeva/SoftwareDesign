package ru.hse.spb.eliseeva;

import ru.hse.spb.eliseeva.exceptions.EnvironmentException;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.lexer.CommandLexer;
import ru.hse.spb.eliseeva.lexer.Lexer;
import ru.hse.spb.eliseeva.lexer.Token;
import ru.hse.spb.eliseeva.parser.CommandsParser;
import ru.hse.spb.eliseeva.parser.RawCommand;

import java.util.List;
import java.util.Scanner;

/**
 * Class to interpret bash command line. Reads input line by line, calls lexer to made tokens from the given input,
 * then calls parser to create executable objects and then runs this objects with evaluated arguments.
 */
public class Interpreter {
    private Scanner scanner;
    private CommandsParser parser;
    private Lexer lexer;
    private Environment environment;

    /**
     * Creates new interpreter with needed lexer, parser, environment and scanner.
     */
    Interpreter() {
        scanner = new Scanner(System.in);
        environment = new Environment();
        parser = new CommandsParser();
        lexer = new CommandLexer();
    }

    /**
     * Runs the interpreter, prints results and errors.
     */
    void run() {
        while (scanner.hasNext()){
            String command = scanner.nextLine();
            if (command.isEmpty()) {
                continue;
            }
            try {
                List<Token> tokens = lexer.tokenize(command);
                List<RawCommand> rawCommands = parser.parse(tokens);
                for (RawCommand rawCommand : rawCommands) {
                    rawCommand.constructCommand(environment).run(environment);
                }
            } catch (LexerException | ParserException | EnvironmentException e ) {
                System.out.println(e.getMessage());
                continue;
            }

            if (environment.hasOutPut()) {
                System.out.print(environment.getOutput());
            }

            if (environment.hasErrors()) {
                System.out.print(environment.getErrors());
            }

            if (environment.isEnd()) {
                System.out.println("Finished");
                return;
            }
        }
    }

    public static void main(String[] args) {
        new Interpreter().run();
    }
}
