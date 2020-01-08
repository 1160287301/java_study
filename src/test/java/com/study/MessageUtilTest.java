package com.study;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageUtilTest {

    String message = "Robert";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void printMessage() {
        System.out.println("Inside testPrintMessage()");
        assertEquals(message, messageUtil.printMessage());
    }
}