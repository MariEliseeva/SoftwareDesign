package ru.hse.spb.eliseeva;

import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.parser.Executable;
import ru.hse.spb.eliseeva.lexer.CommandLexer;
import ru.hse.spb.eliseeva.lexer.Lexer;
import ru.hse.spb.eliseeva.lexer.Token;
import ru.hse.spb.eliseeva.parser.CommandsParser;

import java.util.List;
import java.util.Scanner;

public class Interpreter {
    private Scanner scanner;
    private CommandsParser parser;
    private Lexer lexer;
    private Environment environment;


    public Interpreter() {
        scanner = new Scanner(System.in);
        environment = new Environment();
        parser = new CommandsParser();
        lexer = new CommandLexer();
    }

    public void run() {
        while (scanner.hasNext()){
            String command = scanner.nextLine();
            try {
                List<Token> tokens = lexer.tokenize(command);
                Executable executable = parser.parse(tokens);
                executable.execute(environment);
            } catch (LexerException| ParserException e ) {
                System.out.println(e.getMessage());
                continue;
            }

            if (environment.isEnd()) {
                System.out.println("Finished");
                return;
            }

            if (environment.hasOutPut()) {
                System.out.println(environment.getOutput());
            }
        }
    }
    public static void main(String[] args) {
        new Interpreter().run();
    }
}
