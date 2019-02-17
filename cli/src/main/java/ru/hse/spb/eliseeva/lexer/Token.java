package ru.hse.spb.eliseeva.lexer;

/**
 * Class representing a piece of the given command.
 */
public class Token {
    /**
     * Token types.
     */
    public enum Type {
        /**
         * One or multiple spaces.
         */
        SPACE,

        /**
         * A string representing a command or an argument.
         */
        TEXT,

        /**
         * Pipe sign, connects two parts of commands.
         */
        PIPE,

        /**
         * A string which has single quotes on the left and on the right border.
         */
        SINGLE_QUOTED,

        /**
         * A string which has double quotes on the left and on the right border.
         */
        DOUBLE_QUOTED,

        /**
         * Variable assignment.
         */
        NEW_VARIABLE,

        /**
         * Place to substitute existing variable.
         */
        OLD_VARIABLE
    }

    private Type type;
    private String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object token) {
        if (token.getClass().equals(this.getClass())) {
            return this.getType() == ((Token)token).getType() && this.getValue().equals(((Token)token).getValue());
        }
        return false;
    }

}
