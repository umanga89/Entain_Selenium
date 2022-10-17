package util;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.testng.Reporter;

public class Hooks extends BaseUtil{

    String browserValue;
    private BaseUtil base;
    public Hooks(BaseUtil base){
        this.base = base;
        BaseUtil.logger = LogManager.getLogger(BaseUtil.class.getName());
        browserValue = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser");
    }

    @Before
    public void setup(Scenario scenario) throws Exception {
        try {
            BaseUtil.webUrl = BaseUtil.configProperties.getProperty("web.url");
                //Navigating to URL
                this.base.setDriver(browserValue);
                this.base.driver.get(BaseUtil.webUrl);

            base.message = scenario;
            BaseUtil.logger.log(Level.INFO,scenario.getName()+ " has started");
        } catch (Exception e) {
            BaseUtil.logger.log(Level.ERROR,e.getMessage());
            throw e;
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
                try {
                    this.base.takeScreenShot(this.base.driver, scenario.getName());
                } catch (Exception e) {
                    BaseUtil.logger.log(Level.ERROR, e.getMessage());
                    throw e;
                }
            }
        BaseUtil.logger.log(Level.INFO,scenario.getName()+ " has ended");
            this.base.driver.quit();
    }
}
