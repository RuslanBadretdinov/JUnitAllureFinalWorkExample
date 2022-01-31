package ru.rencredit.framework.basetestsclass;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.rencredit.framework.managers.DriverManager;
import ru.rencredit.framework.managers.InitManager;
import ru.rencredit.framework.managers.PageManager;
import ru.rencredit.framework.managers.TestPropManager;
import ru.rencredit.framework.utils.PropConst;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaseTests {
    /**
     * Менеджер страничек
     *
     * @see PageManager#getPageManager()
     */
    protected PageManager pageManager = PageManager.getPageManager();

    /**
     * Менеджер WebDriver
     *
     * @see DriverManager#getDriverManager()
     */
    private final DriverManager driverManager = DriverManager.getDriverManager();

    @BeforeAll
    public static void beforeAll() {
        InitManager.initFramework();
    }

    @BeforeEach
    public void beforeEach() {
        driverManager.getDriver().get(TestPropManager.getTestPropManager().getProperty(PropConst.BASE_URL));
    }

    @AfterAll
    public static void afterAll() {
        InitManager.quitFramework();
    }
}