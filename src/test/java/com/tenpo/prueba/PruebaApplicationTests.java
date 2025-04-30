package com.tenpo.prueba;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class PruebaApplicationTests {

	@Test
	void contextLoads() {
		//Intencional
	}

	@Test
	void testMainMethod2() {
		// Simula la ejecución de la aplicación sin iniciar múltiples veces
		assertDoesNotThrow(() -> PruebaApplication.main(new String[]{}));
	}

}
