package com.lube.encrypt.licenseCode;

import com.lube.encrypt.utils.ET99Utils;
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
    private ET99Entity entity = null;

    private ET99Utils et99Utils = null;

    public MakeSoftdog(){
        et99Utils = new ET99Utils();
    }

    public void make(String authorCode) throws Exception{
        logger.info("开始制作加密狗");
        try {
            String authCode = RSACoder.decryptByPublicKey(authorCode);
            this.entity = (ET99Entity) JSONObject.toBean(JSONObject.fromObject(authCode), ET99Entity.class);
            et99Utils.openToken();
            generateSoftDog();
        } catch (Exception e) {
            logger.error("制作USB KEY异常：", e);
            throw new Exception("制作USB KEY异常:"+e.getMessage());
        } finally {
            et99Utils.close();
        }
    }

    private void generateSoftDog() throws Exception {
        if(!et99Utils.getHardCode().equals(entity.getSn())){
            throw new Exception("授权码与加密狗不一致，请更换加密狗！");
        }
        try{
            et99Utils.resetUserPin(entity.getSoPin());
            et99Utils.setUserPin();
            logger.info("写入用户数据成功");
        } catch (Exception e1){
            logger.error("写入用户数据异常："+ e1.getMessage());
            throw new Exception("写入用户数据异常");
        }
    }



}
