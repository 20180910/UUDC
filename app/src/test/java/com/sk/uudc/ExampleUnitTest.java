package com.sk.uudc;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void afd() throws Exception {
        long jianGe=3*60*60*1000;
        System.out.println(new Date().getTime());
    }
    @Test
    public void calculateMethodNum() throws Exception {
        Method[] declaredMethods1 = com.sk.uudc.module.near.network.IRequest.class.getDeclaredMethods();
        Method[] declaredMethods2 = com.sk.uudc.module.home.network.IRequest.class.getDeclaredMethods();
        Method[] declaredMethods3 = com.sk.uudc.module.my.network.IRequest.class.getDeclaredMethods();
        Method[] declaredMethods4 = com.sk.uudc.module.order.network.IRequest.class.getDeclaredMethods();
        Method[] declaredMethods5 = com.sk.uudc.network.IRequest.class.getDeclaredMethods();
        int methodSize = declaredMethods1.length + declaredMethods2.length + declaredMethods3.length + declaredMethods4.length + declaredMethods5.length;
        System.out.println("方法数量:"+methodSize);
    }

    @Test
    public void afsd() throws Exception {
        A a=new A();
        a.a="a";

        A b=a;
        b.a="b";
        System.out.println(a.a);
        System.out.println(b.a);
    }

    public class  A{
        public String a;
    }
}