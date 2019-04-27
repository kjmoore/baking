package com.kieranjohnmoore.baking;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

class TestAnnotations {
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface TabletTest {
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface PhoneTest {
    }
}
