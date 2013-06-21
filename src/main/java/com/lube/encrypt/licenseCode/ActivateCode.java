package com.lube.encrypt.licenseCode;

import com.lube.encrypt.utils.CommonConst;
import com.lube.encrypt.utils.ET99Utils;
import com.lube.encrypt.utils.RSACoder;
import com.lube.encrypt.utils.RSACoderPrivate;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqinghong
 * Date: 13-5-31
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class ActivateCode {
    private static Logger logger = Logger.getLogger(ActivateCode.class);

    private ET99Utils et99Utils = null;

    public ActivateCode(){
        et99Utils = new ET99Utils();
    }
    /**
     * 生成激活码
     */
    public String generateCode() throws Exception{
        String activateCode = null;
        try {
            et99Utils.initToken();
            et99Utils.openToken();
            activateCode = generateActivateCode();
        } catch (Exception e) {
            logger.error("生成激活码异常",e);
            throw new Exception("生成激活码异常:"+e.getMessage());
        } finally {
            et99Utils.close();
        }
        return activateCode;
    }

    /**
     * 产生一个激活码，用于制作加密狗所需的授权码
     * @return
     */
    private String generateActivateCode() throws Exception {
        Map<String, String> codeMap = new HashMap<String,String>();
        codeMap.put("sn", et99Utils.getHardCode());
        codeMap.put("soPin", RSACoder.decryptByPublicKey(CommonConst.STRING_SO_PIN));
        codeMap.put("pid",RSACoder.decryptByPublicKey(CommonConst.STRING_PID));

        String activateCode = JSONObject.fromObject(codeMap).toString();
        return RSACoder.encryptByPublicKey(activateCode);
    }

    public static void main(String[] args){
        try {
            ActivateCode usbKey = new ActivateCode();
            String activateCode = usbKey.generateCode();
            System.out.println("激活码：" + activateCode);

            System.out.println(RSACoderPrivate.decryptByPrivateKey(activateCode));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
