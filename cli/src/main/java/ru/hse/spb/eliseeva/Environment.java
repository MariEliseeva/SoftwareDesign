package ru.hse.spb.eliseeva;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containing all the variables, pipe input-output and state of the program (end or continue).
 */
public class Environment {
    private String pipeValue;
    private String errors;
    private Map<String, String> variableValues = new HashMap<>();
    private boolean isEnd = false;

    /**
     * Returns variable value by its name or an empty string if no value found.
     * @param variable name of the variable
     * @return value of the given variable
     */
    public String getVariableValue(String variable) {
        if (variableValues.containsKey(variable)) {
            return variableValues.get(variable);
        }
        return "";
    }

    /**
     * Sets variable value to its name
     * @param variable name of the variable
     * @param value value of the given variable
     */
    public void setVariableValue(String variable, String value) {
        variableValues.put(variable, value);
    }

    /**
     * Returns output and clears pipe.
     * @return string written by executed commands
     */
    public String getOutput() {
        String answer = pipeValue;
        pipeValue = null;
        return answer;
    }

    /**
     * Checks if output exists.
     * @return true if output exists, false if not
     */
    public boolean hasOutPut() {
        return pipeValue != null;
    }

    /**
     * Writes to pipe the given string
     * @param value result of the executed command
     */
    public void writeToPipe(String value) {
        this.pipeValue = value;
    }

    /**
     * Remembers errors made
     * @param error new error tp remember
     */
    public void writeToErrors(String error) {
        if (errors == null) {
            errors = "";
        }
        this.errors += error;
    }

    /**
     * Mark the current work as finished.
     */
    public void end() {
        isEnd = true;
    }

    /**
     * Checks if some command finished all the process of not
     * @return true if the process in finished, false if not
     */
    public boolean isEnd() {
        return isEnd;
    }

    /**
     * Checks if errors exist.
     * @return true if output exists, false if not
     */
    boolean hasErrors() {
        return errors != null;
    }

    /**
     * Returns errors.
     * @return errors made
     */
    String getErrors() {
        String answer = errors;
        errors = null;
        return answer;
    }
}
