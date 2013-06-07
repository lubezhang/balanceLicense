package com.lube.encrypt.licenseCode;

import ET299jni.CET299;
import ET299jni.ET299Def;
import ET299jni.IET299;
import ET299jni.RTException;
import com.lube.encrypt.utils.CommonConst;
import com.lube.encrypt.utils.RSACoder;
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

    private IET299 et99 = new CET299();

    private int[] keyCount = new int[1];

    /**
     * 生成激活码
     */
    public String generateCode() throws Exception{
        String activateCode = "";
        try {
            initKey();
            openKey();
            activateCode = generateActivateCode();
        } catch (Exception e) {
            logger.error("生成激活码异常",e);
            throw new Exception("生成激活码异常:"+e.getMessage());
        }
        return activateCode;
    }

    /**
     * 产生一个激活码，用于制作加密狗所需的授权码
     * @return
     */
    private String generateActivateCode() throws Exception {
        String activateCode = "";

        boolean verifyDefaultSOPin = true;
        try {
            et99.Verify(ET299Def.ET_VERIFY_SOPIN,"FFFFFFFFFFFFFFFF".getBytes());
        } catch (RTException e){
            verifyDefaultSOPin = false;
        }

        boolean verifySOPin = true;
        try {
            et99.Verify(ET299Def.ET_VERIFY_SOPIN, RSACoder.decryptByPublicKey(CommonConst.STRING_SU_PIN).getBytes());
        } catch (RTException e){
            verifySOPin = false;
        }

        Map<String, String> codeMap = new HashMap<String,String>();
        //获取硬件code
        byte[] sn = new byte[8];
        et99.GetSN(sn);
        String strSN = "";
        for(int i = 0; i < sn.length; i++){
            strSN += new Byte(sn[i]).intValue() ;
        }
        codeMap.put("sn", strSN);

        //生成soPIN
        String strSoPIN = "";
        if(verifyDefaultSOPin){
            byte[] soPin = new byte[16];
            byte[] bytes = RSACoder.decryptByPublicKey(CommonConst.STRING_SEED).getBytes();
            et99.GenSoPIN(bytes, bytes.length, soPin);
            strSoPIN = RSACoder.encryptByPublicKey(new String(soPin));
        } else if(verifySOPin){
            strSoPIN = RSACoder.decryptByPublicKey(CommonConst.STRING_SU_PIN);
//            strSoPin = RSACoder.encryptByPublicKey(soPin);
        }
        codeMap.put("soPin", new String(strSoPIN));
        codeMap.put("pid",RSACoder.decryptByPublicKey(CommonConst.STRING_PID));

        activateCode = JSONObject.fromObject(codeMap).toString();
        return RSACoder.encryptByPublicKey(activateCode);
    }

    /**
     * 初始化USB KEY
     * 如果使用默认PID能正常打开USB KEY则初始化
     * 用种子重新生成PID和SOPIN
     * @throws Exception
     */
    private void initKey() throws Exception{
        boolean isDefault = true;
        try{
            et99.FindToken("FFFFFFFF".getBytes(), keyCount);
            et99.OpenToken("FFFFFFFF".getBytes(), 1);
            et99.Verify(ET299Def.ET_VERIFY_SOPIN, "FFFFFFFFFFFFFFFF".getBytes());
        } catch (RTException e1){
            logger.error("默认PID打开USB KEY失败");
            isDefault = false;
        }

        if(isDefault){
            try {
                //初始化Key
                byte[] bytes = RSACoder.decryptByPublicKey(CommonConst.STRING_SEED).getBytes();
                byte[] pid = new byte[8];
                et99.GenPid(bytes, bytes.length, pid);
                logger.debug("生成新的加密狗PID：" + new String(pid));
            } catch (RTException e2){
                logger.error("初始化USB Key异常："+CommonConst.MAP_ERROR_MESSAGE.get(e2.HResult()));
                throw new Exception("初始化USB Key异常");
            }
        }
    }

    /**
     * 使用已经生成的PID打开USB
     * @throws Exception
     */
    private void openKey() throws Exception{
        try{
            et99.FindToken(RSACoder.decryptByPublicKey(CommonConst.STRING_PID).getBytes(), keyCount);
            et99.OpenToken(RSACoder.decryptByPublicKey(CommonConst.STRING_PID).getBytes(), 1);
            logger.debug("预定义PID打开USB KEY成功");
        } catch (RTException e){
            logger.error("预定义PID打开USB Key异常："+CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()), e);
            throw new Exception("打开USB Key异常");
        }
    }

    public static void main(String[] args){
        try {
            ActivateCode usbKey = new ActivateCode();
            String activateCode = usbKey.generateCode();
            System.out.println("激活码：" + activateCode);

            System.out.println(RSACoder.decryptByPrivateKey(activateCode));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
