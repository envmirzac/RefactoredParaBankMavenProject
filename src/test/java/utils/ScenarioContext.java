package utils;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static ScenarioContext instance;
    private final Map<String, Object> contextMap;

    private ScenarioContext() {
        contextMap = new HashMap<>();
    }

    public static ScenarioContext getInstance() {
        if (instance == null) {
            instance = new ScenarioContext();
        }
        return instance;
    }

    public void setContext(String key, Object value) {
        contextMap.put(key, value);
    }

    public <T> T getContext(String key) {
        return (T) contextMap.get(key);
    }

    // Added method to clear the context
    public void clearContext() {
        contextMap.clear();
    }
}
