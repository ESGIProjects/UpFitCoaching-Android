package com.mycoaching.test;

import com.mycoaching.mycoaching.Util.Constants;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static junit.framework.Assert.assertTrue;

/**
 * Created by kevin on 22/06/2018.
 */
public class ConstantsTest{

    @Test
    public void testIsPrivate() throws Exception{
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
