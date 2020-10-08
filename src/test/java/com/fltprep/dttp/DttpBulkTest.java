package com.fltprep.dttp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DttpBulkTest {
    DttpBulk tester = new DttpBulk();
    @DisplayName("Test dttp.testDttpHelloWorld()")
    @Test
    public void testDttpHelloWorld() {
        assertEquals("Hello World", tester.helloWorld());
    }
}