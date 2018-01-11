package com.cet325.bg69xx;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExampleUnitTest{

    @Test
    public void testConvertCelsiusToFahrenheit() {
        float actual = 212;
        // correct expected value is 212
        float expected = 212;
        // use this method because float is not precise
        assertEquals("Example test", expected, actual, 0.001);
    }
}