package CalculatorProject;

import java.util.Deque;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CalculatorEngine {
    private static final Logger logger = LogManager.getLogger();
    private long currentValue;
    private Deque<Long> stack;
    private boolean isError;

    public CalculatorEngine() {
        this.stack = new LinkedList<Long>();
        clear();
    }

    public void clear() {
        currentValue = 0L;
        stack.clear();
        isError = false;
    }

    public String getDisplayContent() {
        if (isError) {
            return "ERROR";
        } else {
            return Long.toString(currentValue);
        }
    }

    private void controlKeyPressed(Key key) {
        switch (key) {
            case CLEAR:
                clear();
                break;
            case ENTER:
                stack.push(currentValue);
                currentValue = 0L;
                break;
            default:
                logger.error("unexpected control key: "+key);
        }
    }

    private void operatorKeyPressed(Key key) {
        switch (key) {
            default:
            logger.error("unexpected operator key: "+key);
        }
    }
    public void keyPressed(Key key) {
        logger.debug("Key pressed: " + key.display);
        switch (key.keyType) {
            case DIGIT:
                long nextValue = 10L * currentValue + (long) key.code;
                if (Long.signum(currentValue) != 0 && Long.signum(nextValue) != Long.signum(currentValue)) {
                    logger.info("Overflow, no more digits accepted");
                } else {
                    currentValue = nextValue;
                }
                logger.info("currentValue: "+currentValue);
                break;
            case OPERATOR:
                operatorKeyPressed(key);
                break;
            case CONTROL:
                controlKeyPressed(key);
                break;
            default:
                logger.error("Unhandled case");
        }
    }
}
