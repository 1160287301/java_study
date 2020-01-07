package com.study;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * keeps the bar green to keeps the code clean
 *
 * @Test 都是测试方法
 */
public class TTest {

    @Test
    public void sum() {
        int s = new T().sum(3, 3);
        assertEquals(6, s);
//        assertTrue(s > 10);
//        assertTrue("太小了", s > 11);
        assertThat(s, is(6));
    }

//    @Test(expected = java.lang.ArithmeticException.class)
    @Test
    public void devide() {
        int s = new T().devide(8, 1);
        assertTrue(s > 5);
    }
}