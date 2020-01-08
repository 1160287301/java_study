package com.study;

import org.junit.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * keeps the bar green to keeps the code clean
 *
 * @Test:将一个普通方法修饰为一个测试方法
 * @Test(exception=XXX.class)
 * @Test(time=毫秒)
 * @BeforeClass：它会在所有的测试方法前被执行，static修饰
 * @AfterClass：它会在所有的测试方法后被执行，static修饰
 * @Before:它会在每一个测试方法前被执行一次
 * @After：它会在每一个测试方法后被执行一次
 * @Ignore：省略
 * @RunWith：修改运行器org。junit。runner。Runner
 */
public class TTest {
    @BeforeClass
    @Ignore
    public static void beforeclass() {
        System.out.println("beforeclass");
    }

    @AfterClass
    @Ignore
    public static void afterclass() {
        System.out.println("afterclass");
    }


    @Before
    @Ignore
    public void before() {
        System.out.println("before");
    }

    @After
    @Ignore
    public void after() {
        System.out.println("after");
    }

    @Test
    public void sum() {
        System.out.println("sum");
        int s = new T().sum(3, 3);
        assertEquals(6, s);
//        assertTrue(s > 10);
//        assertTrue("太小了", s > 11);
        assertThat(s, is(6));
    }

    //    @Test(expected = java.lang.ArithmeticException.class)
//    @Ignore
    @Test
    public void devide() {
        System.out.println("devide");
        int s = new T().devide(8, 1);
        assertTrue(s > 5);
    }
}