package com.lube.encrypt.utils;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqinghong
 * Date: 13-6-3
 * Time: 上午9:51
 * To change this template use File | Settings | File Templates.
 */
public class RSACoderTest {
//    private RSACoder coder = null;
    @Before
    public void setUp() throws Exception {
//        coder = new RSACoder();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCrypt(){
        String strTest = "单元测试";
        try {
            String encode = RSACoder.encryptByPrivateKey(strTest);
            Assert.assertEquals(strTest, RSACoder.decryptByPublicKey(encode));
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }
}
