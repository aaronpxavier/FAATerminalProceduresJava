package com.fltprep.dttp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DttpBulkTest {
    DttpBulk tester = new DttpBulk();
    @Test
    @DisplayName("Test for DttpBulk.getCurrentCycle()")
    void getCurrentCycle() {
        String testString = tester.getCurrentCycle();
        assertEquals(true, testString.matches("[0-9]{6}"));
    }
}