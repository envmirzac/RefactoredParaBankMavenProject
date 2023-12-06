package stepDefinitions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ScenarioContext;

//An abstract class cannot be instantiated directly, it is intended to be a base class for other classes. Inheritance
public abstract class BaseDefine {
    protected final ScenarioContext scenarioContext = ScenarioContext.getInstance();
    protected static final Logger logger = LoggerFactory.getLogger(BaseDefine.class);
}