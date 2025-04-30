package com.tenpo.prueba.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ConstantsTest {
    @Test
    void constructorShouldBePrivates() throws Exception {
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance();
        });
    }
}
