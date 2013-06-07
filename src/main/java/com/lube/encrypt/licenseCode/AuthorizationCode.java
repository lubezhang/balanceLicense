package com.lube.encrypt.licenseCode;

import com.lube.encrypt.utils.RSACoder;
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
            String code = RSACoder.decryptByPrivateKey(activateCode);
            logger.debug(code);
//            JSONObject json = JSONObject.fromObject(authCode);
            authCode = RSACoder.encryptByPrivateKey(code);
        } catch (Exception e) {
            logger.error(e);
        }
        return authCode;
    }

    public static void main(String[] args){
        String activateCode = "OJxs/zgWOZDYaC64gOE1qCcGf1+K9tiMRAbcq4WCp6hjelMzEzosUpuraKIleZdWL28r3cljjgDyS4J0UeauUVuI49Kr7Ey3REWLYsAOLzGKNQvdu5YB82gwHJAoqxOEa9Y4v7v5COpE5ruzVu8aGhhrGGQN9LOouK0M5FOVxpE=";
        AuthorizationCode authorizationCode = new AuthorizationCode();
        System.out.println(authorizationCode.generateCode(activateCode));

    }
}
