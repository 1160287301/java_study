package com.test;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageUtilTest2 {
    String message = "Robert";
    MessageUtil messageUtil = new MessageUtil(message);

    @Test
    public void salutationMessage() {
        System.out.println("Inside testSalutationMessage()");
        message = "Hi!" + "Robert";
        assertEquals(message,messageUtil.salutationMessage());
        String a = "";
    }
}