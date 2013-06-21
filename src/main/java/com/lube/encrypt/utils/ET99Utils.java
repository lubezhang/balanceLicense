package com.lube.encrypt.utils;

import ET299jni.CET299;
import ET299jni.ET299Def;
import ET299jni.IET299;
import ET299jni.RTException;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-10
 * Time: 下午2:02
 *
 *
 */
public class ET99Utils {
    private static Logger logger = Logger.getLogger(ET99Utils.class);

    private IET299 et99 = new CET299();

    //找到
    private int[] keyCount = new int[1];

    /**
     * 初始化USB KEY
     * 如果使用默认PID能正常打开USB KEY则初始化
     * 用种子重新生成PID和SOPIN
     * @throws Exception
     */
    public void initToken() throws Exception{
        //验证USB KEY是否被初始化过
        boolean isDefault = true;
        try {
            et99.FindToken("FFFFFFFF".getBytes(), keyCount);
            et99.OpenToken("FFFFFFFF".getBytes(), 1);
            et99.Verify(ET299Def.ET_VERIFY_SOPIN, "FFFFFFFFFFFFFFFF".getBytes());
        } catch (RTException e1){
            logger.error("默认PID打开USB KEY失败");
            isDefault = false;
        }

        //如果没有被初始化过，使用种子初始化USB KEY。重新生成PID和超级用户PIN
        if(isDefault){
            try {
                byte[] bytes = RSACoder.decryptByPublicKey(CommonConst.STRING_SEED).getBytes();
                byte[] pid = new byte[8];
                byte[] soPin = new byte[51];
                et99.GenPid(bytes, bytes.length, pid);
                et99.GenSoPIN(bytes, soPin.length, soPin);
                logger.debug("生成新的加密狗PID：" + new String(pid)+",超级用户pin："+new String(soPin));
            } catch (RTException e2){
                logger.error("初始化USB Key异常："+CommonConst.MAP_ERROR_MESSAGE.get(e2.HResult()));
                throw new Exception("初始化USB Key异常");
            }
        }
    }


    /**
     * 使用预定义的PID打开USB KEY
     * @throws Exception
     */
    public void openToken() throws Exception{
        try{
            logger.info("打开加密狗");
            et99.FindToken(RSACoder.decryptByPublicKey(CommonConst.STRING_PID).getBytes(), keyCount);
            et99.OpenToken(RSACoder.decryptByPublicKey(CommonConst.STRING_PID).getBytes(), 1);

            logger.debug("预定义PID打开USB KEY成功");
        } catch (RTException e){
            logger.error("预定义PID打开USB Key异常："+CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()), e);
            throw new Exception("打开USB Key异常");
        }
    }

    /**
     * 使用普通用户权限方式USB KEY
     * @throws Exception
     */
    public void verifyUser() throws Exception{
        try {
            et99.Verify(ET299Def.ET_VERIFY_USERPIN, RSACoder.decryptByPublicKey(CommonConst.STRING_USER_PIN).getBytes());
        } catch (RTException e){
            logger.error("获取普通用户权限异常："+CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()), e);
            throw new Exception("获取普通用户权限");
        }
    }

    /**
     * 使用管理员权限方式USB KEY
     * @throws Exception
     */
    public void verifyAdmin() throws Exception{
        try {
            et99.Verify(ET299Def.ET_VERIFY_SOPIN, RSACoder.decryptByPublicKey(CommonConst.STRING_SO_PIN).getBytes());

        } catch (RTException e){
            logger.error("获取管理员权限异常："+CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()), e);
            throw new Exception("获取管理员权限");
        }
    }

    /**
     * 获取KEY的硬件编码，每个KEY只有一个唯一的编码，不会重复
     *
     * @return 返回唯一的硬件编码
     * @throws Exception
     */
    public String getHardCode() throws Exception{
        String strSN = "";
        try {
            byte[] sn = new byte[8];
            et99.GetSN(sn);
            for(int i = 0; i < sn.length; i++){
                strSN += new Byte(sn[i]).intValue() ;
            }
        } catch (RTException e){
            logger.error("获取硬件编码异常："+CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()), e);
            throw new Exception("获取硬件编码异常");
        }
        return strSN;
    }

    /**
     * 重置普通用户密码
     * @throws Exception
     */
    public void resetUserPin(String soPin) throws Exception{
        try {
            et99.ResetPIN(soPin.getBytes());
        } catch (RTException e){
            logger.error("重置用户密码异常："+CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()), e);
            throw new Exception("重置用户密码异常");
        }
    }

    /**
     * 设置普通用户密码
     * @throws Exception
     */
    public void setUserPin() throws Exception{
        verifyAdmin();
        try{
            et99.ChangeUserPIN("FFFFFFFFFFFFFFFF".getBytes(), RSACoder.decryptByPublicKey(CommonConst.STRING_USER_PIN).getBytes());
        } catch (RTException e){
            logger.error("设置用户密码异常："+CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()), e);
            throw new Exception("设置用户密码异常");
        }
    }

    /**
     * 读取KEY存储区域的数据
     * @return
     * @throws Exception
     */
    public String readData() throws Exception{
        return null;
    }

    /**
     * 将数据写入到KEY的数据存储区域，最多存储1000个字节的数据
     * @param data
     * @throws Exception
     */
    public void wirteData(String data) throws Exception{

    }

    public void close() throws Exception {
        logger.info("关闭加密狗");
        try{
            et99.CloseToken();
        } catch (RTException e){
            logger.error("关闭USB Key异常："+CommonConst.MAP_ERROR_MESSAGE.get(e.HResult()), e);
            throw new Exception("关闭USB Key异常");
        }
    }
}
