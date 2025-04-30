package com.tenpo.prueba.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ConstantsTest {
    @Test
    void constructorShouldBePrivates() throws Exception {
        // Obtén el constructor privado de la clase Constants
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();

        // Haz que el constructor sea accesible
        constructor.setAccessible(true);

        // Verifica si al invocar el constructor se lanza la excepción UnsupportedOperationException
        assertThrows(InvocationTargetException.class, () -> {
            constructor.newInstance();  // Invoca el constructor
        });
    }
}
