package com.study;

import org.junit.Test;

import static org.junit.Assert.*;

public class TTest {

    @Test
    public void sum() {
        assertEquals(6, new T().sum(3, 3));
    }
}