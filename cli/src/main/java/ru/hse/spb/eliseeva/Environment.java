package ru.hse.spb.eliseeva;

import java.util.HashMap;
import java.util.Map;

/**
 * Class containing all the variables, pipe input-output and state of the program (end or continue).
 */
public class Environment {
    private String pipeValue;
    private Map<String, String> variableValues = new HashMap<>();
    private boolean isEnd = false;

    public String getVariableValue(String variable) {
        return variableValues.get(variable);
    }

    public void setVariableValue(String variable, String value) {
        variableValues.put(variable, value);
    }

    public String getOutput() {
        String answer = pipeValue;
        pipeValue = null;
        return answer;
    }

    boolean hasOutPut() {
        return pipeValue != null;
    }

    public void writeToPipe(String value) {
        this.pipeValue = value;
    }

    public void end() {
        isEnd = true;
    }

    public boolean isEnd() {
        return isEnd;
    }
}
