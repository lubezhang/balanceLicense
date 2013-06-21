package com.lube.encrypt.license;

import com.lube.encrypt.licenseCode.ActivateCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqinghong
 * Date: 13-6-3
 * Time: 上午9:40
 * To change this template use File | Settings | File Templates.
 */
public class UsbKeyLicenseTest {
    private ActivateCode ukl = null;
    @Before
    public void setUp() throws Exception {
        ukl = new ActivateCode();
    }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMakeSoftDog() throws Exception {
        ActivateCode usbKey = new ActivateCode();
        String activateCode = usbKey.generateCode();
//        System.out.println("activateCode is :" + activateCode);
//        assertEquals(1, 1);
//        assertNull(activateCode);

//        AuthorizationCode authorizationCode = new AuthorizationCode();
//        String authorCode = authorizationCode.generateCode(activateCode);
//        System.out.println("authorCode is :" + authorCode);
//
//        MakeSoftdog softdog = new MakeSoftdog();
//        softdog.make(authorCode);
    }
}
