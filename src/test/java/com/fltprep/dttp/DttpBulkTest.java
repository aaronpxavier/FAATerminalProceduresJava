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

    @Test
    @DisplayName("Test for DttpBulk.getFourDigitCycle()")
    void getFourDigitCycle() {
        String testString = tester.getFourDigitCycle();
        assertEquals(true, testString.matches("[0-9]{4}"));
    }

//    @Test
//    @DisplayName("Test for DttpBulk.dloadMetaFile")
//    void dloadMetaFile() {
//        tester.dloadMetaFile("./testdir");
//    }
}