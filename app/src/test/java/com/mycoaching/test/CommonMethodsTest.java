package com.mycoaching.test;

import android.content.Context;
import android.test.mock.MockContext;
import android.widget.EditText;

import com.mycoaching.mycoaching.Util.CommonMethods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkEmail;
import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.clearFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getSHAPassword;
import static com.mycoaching.mycoaching.Util.CommonMethods.isSame;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

/**
 * Created by kevin on 22/06/2018.
 */

public class CommonMethodsTest{

    @Test
    public void testIsPrivate() throws Exception{
        Constructor<CommonMethods> constructor = CommonMethods.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testCheckField(){
        assertTrue(checkFields("Test 1"," Test 2", "Test 3"));
        assertFalse(checkFields("Test 1",""));
    }

    @Test
    public void testIsSame(){
        assertTrue(isSame("Upfit","Upfit"));
        assertFalse(isSame("Upfit","upfit"));
    }

    @Test
    public void testCheckEmail(){
        assertTrue(checkEmail("test@"));
    }

    @Test
    public void testGetSHAPassword(){
        assertEquals("967520ae23e8ee14888bae72809031b98398ae4a636773e18fff917d77679334",
                getSHAPassword("motdepasse"));
    }
}
