package com.lube.encrypt.licenseCode;

import ET299jni.CET299;
import ET299jni.ET299Def;
import ET299jni.IET299;
import ET299jni.RTException;
import com.lube.encrypt.utils.CommonConst;
import com.lube.encrypt.utils.RSACoder;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;


/**
 * Created with IntelliJ IDEA.
 * User: zhangqinghong
 * Date: 13-6-3
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public class MakeSoftdog {
    private static Logger logger = Logger.getLogger(MakeSoftdog.class);

    private IET299 et99 = new CET299();

    public MakeSoftdog(){

    }

    public void make(String authorCode) throws Exception{
        try {
            String authCode = RSACoder.decryptByPublicKey(authorCode);
//            logger.debug("authCode:" + authCode);
            ET99Entity entity = (ET99Entity) JSONObject.toBean(JSONObject.fromObject(authCode), ET99Entity.class);
            openUsbKey(entity);
            verifyHardCode(entity.getSn());
            generateSoftDog();
        } catch (Exception e) {
            logger.error("制作USB KEY异常：", e);
            throw new Exception("制作USB KEY异常:"+e.getMessage());
        }
    }

    public void resetUserPin(String authorCode) throws Exception{
        try {
            String authCode = RSACoder.decryptByPublicKey(authorCode);
            ET99Entity entity = (ET99Entity) JSONObject.toBean(JSONObject.fromObject(authCode), ET99Entity.class);
            openUsbKey(entity);
            et99.ResetPIN(entity.getSoPin().getBytes());
        } catch (RTException e){
            throw new Exception("重置用户数据异常："+CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()));
        }
    }

    private void openUsbKey(ET99Entity entity) throws Exception {
        try{
            int[] keyCount = new int[1];
            et99.FindToken(entity.getPid().getBytes(), keyCount);
            et99.OpenToken(entity.getPid().getBytes(), 1);
            et99.Verify(ET299Def.ET_VERIFY_SOPIN, entity.getSoPin().getBytes());
        } catch (RTException e){
            logger.error("打开USB Key异常："+ CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()));
            throw new Exception("打开USB Key异常");
        }
    }

    private void verifyHardCode(String hCode) throws Exception{
        byte[] sn = new byte[8];
        et99.GetSN(sn);
        String strSN = "";
        for(int i = 0; i < sn.length; i++){
            strSN += new Byte(sn[i]).intValue() ;
        }

        if(!strSN.equals(hCode)){
            throw new Exception("硬件序列号不匹配！");
        } else {
            logger.info("校验硬件序列号通过");
        }
    }

    private void generateSoftDog() throws Exception {
        String userPin = RSACoder.decryptByPublicKey(CommonConst.STRING_USER_PIN);
        try{
            et99.ChangeUserPIN("FFFFFFFFFFFFFFFF".getBytes(), userPin.getBytes());
            logger.info("写入用户数据成功");
        } catch (RTException e1){
            logger.error("写入用户数据异常："+ CommonConst.MAP_ERROR_MESSAGE.get(e1.HResult()));
            throw new Exception("写入用户数据异常");
        }
    }



}
