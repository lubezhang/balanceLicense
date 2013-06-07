package com.lube.encrypt.license;

import com.lube.encrypt.licenseCode.ActivateCode;
import com.lube.encrypt.licenseCode.AuthorizationCode;
import com.lube.encrypt.licenseCode.MakeSoftdog;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqinghong
 * Date: 13-6-3
 * Time: 下午1:27
 * To change this template use File | Settings | File Templates.
 */
public class createUsbKey {
    public static void main(String[] args){
        try{
            ActivateCode usbKey = new ActivateCode();
            String activateCode = usbKey.generateCode();
            System.out.println("activateCode is :" + activateCode);

            AuthorizationCode authorizationCode = new AuthorizationCode();
            String authorCode = authorizationCode.generateCode(activateCode);
            System.out.println("authorCode is :" + authorCode);

            MakeSoftdog softdog = new MakeSoftdog();
            softdog.make(authorCode);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
