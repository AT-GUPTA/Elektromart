package com.elektrodevs.elektromart;

import com.elektrodevs.elektromart.controller.*;
import com.elektrodevs.elektromart.service.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        // Controllers
        AuthenticationControllerTest.class,
        ProductControllerTests.class,
        CartControllerTests.class,
        StaffControllerTests.class,
        OrderControllerTests.class,

        // services
        AuthenticationServiceTests.class,
        CartServiceTests.class,
        OrderServiceTests.class,
        ProductServiceTests.class,
        UserServiceTests.class
})
public class TestSuite {
}
