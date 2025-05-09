package com.example.backend;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CalculatorTest {

    @Test
    public void testAdd() {
        assertEquals(5, 2+3);
    }

    @Test
    public void testFail() {
        assertEquals(5, 2+3);
    }
}
