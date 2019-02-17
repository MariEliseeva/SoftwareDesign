package ru.hse.spb.eliseeva;

import ru.hse.spb.eliseeva.commands.CommandCreator;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.parser.Executable;
import ru.hse.spb.eliseeva.lexer.CommandLexer;
import ru.hse.spb.eliseeva.lexer.Lexer;
import ru.hse.spb.eliseeva.lexer.Token;
import ru.hse.spb.eliseeva.parser.CommandsParser;

import java.util.List;
import java.util.Scanner;

/**
 * Class to interpret bash command line. Reads input line by line, calls lexer to made tokens from the given input,
 * then calls parser to create executable objects and then run this objects with evaluated arguments.
 */
public class Interpreter {
    private Scanner scanner;
    private CommandsParser parser;
    private Lexer lexer;
    private Environment environment;

    /**
     * Creates new interpreter with needed lexer, parser, environment and scanner.
     */
    public Interpreter() {
        scanner = new Scanner(System.in);
        environment = new Environment();
        parser = new CommandsParser();
        lexer = new CommandLexer();
    }

    /**
     * Runs the interpreter, prints results and errors.
     */
    public void run() {
        while (scanner.hasNext()){
            String command = scanner.nextLine();
            try {
                List<Token> tokens = lexer.tokenize(command);
                List<Executable> executables = parser.parse(tokens);
                for (Executable executable : executables) {
                    CommandCreator.create(executable.getCommandName(environment),
                            executable.getCommandArguments(environment))
                            .run(environment);
                }
            } catch (LexerException | ParserException e ) {
                System.out.println(e.getMessage());
                continue;
            }

            if (environment.hasOutPut()) {
                System.out.println(environment.getOutput());
            }

            if (environment.hasErrors()) {
                System.out.println(environment.getErrors());
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
