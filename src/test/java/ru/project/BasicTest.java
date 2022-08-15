package ru.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.validation.constraints.AssertTrue;

public class BasicTest {

    @Test
    @Timeout(1000)
    @AssertTrue
    public void simpleTest(){
        assertEquals(4,1+3);
    }
}
