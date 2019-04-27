package com.kieranjohnmoore.baking;

import android.content.Context;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.lang.reflect.Method;

import androidx.test.core.app.ApplicationProvider;

public class BaseInstrumentedTest {
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {
        assertDeviceOrSkip();
    }

    private void assertDeviceOrSkip() {
        try {
            Method m = getClass().getMethod(testName.getMethodName());
            if (m.isAnnotationPresent(TestAnnotations.TabletTest.class)) {
                Assume.assumeTrue("Not running tablet test on a phone", isTablet());
            } else if (m.isAnnotationPresent(TestAnnotations.PhoneTest.class)) {
                Assume.assumeFalse("Not running phone test on a tablet", isTablet());
            }
        } catch (NoSuchMethodException e) {
            /* no-op */
        }
    }

    private boolean isTablet() {
        Context targetContext = ApplicationProvider.getApplicationContext();
        return targetContext.getResources().getBoolean(R.bool.tablet_mode);
    }
}

