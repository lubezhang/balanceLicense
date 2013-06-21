package com.lube.encrypt.licenseCode;

import com.lube.encrypt.utils.RSACoder;
import com.lube.encrypt.utils.RSACoderPrivate;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqinghong
 * Date: 13-6-3
 * Time: 下午1:04
 * To change this template use File | Settings | File Templates.
 */
public class AuthorizationCode {
    private static Logger logger = Logger.getLogger(AuthorizationCode.class);

    public String generateCode(String activateCode){
        String authCode = "";
        try {
            String code = RSACoderPrivate.decryptByPrivateKey(activateCode);
            logger.debug(code);
//            JSONObject json = JSONObject.fromObject(authCode);
            authCode = RSACoderPrivate.encryptByPrivateKey(code);
        } catch (Exception e) {
            logger.error(e);
        }
        return authCode;
    }

    public static void main(String[] args){
        String activateCode = "gXjXncaVR551MmqSQu8KVqCUCiIKpwJH09bfkRVvLDkfehZ9nRSDBKYcGICk1rsiUy4+8R2TSy2nmV4ax+rkGWd4eP+1oMKO37kxi6VVUGXQKC3hdJBYJQB2/b96AJ4GW9IMM3QGrpufYqXEvDg4AV4tukKcuCoAuNiVOgHA1mA=";
        AuthorizationCode authorizationCode = new AuthorizationCode();
        System.out.println(authorizationCode.generateCode(activateCode));

    }
}
