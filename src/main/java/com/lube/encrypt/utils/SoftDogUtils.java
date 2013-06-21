package com.lube.encrypt.utils;

import ET299jni.CET299;
import ET299jni.ET299Def;
import ET299jni.IET299;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqinghong
 * Date: 13-6-4
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public class SoftDogUtils {
    private IET299 et99 = new CET299();

    public void openKey(String pid) throws Exception{
        int[] keyCount = new int[1];
        et99.FindToken(pid.getBytes(), keyCount);
        et99.OpenToken(pid.getBytes(), 1);
    }

    public void readUserPin() throws Exception{
        et99.Verify(ET299Def.ET_VERIFY_USERPIN, "a75654c728793814".getBytes());
        et99.TurnOnLED();
        et99.TurnOffLED();
    }

    public void close(){
        try {
            et99.CloseToken();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        for (int i = 0; i < 5; i++){
            SoftDogUtils.Et99Thread thread = new Et99Thread();
            thread.start();
        }
    }

    static class Et99Thread extends Thread {
        public void run() {
            SoftDogUtils sd = new SoftDogUtils();
            try {
                long begin = System.currentTimeMillis();
                sd.openKey("8FC5E436");
                for (int i = 0; i < 100000; i++){
                    System.out.println("线程【"+this.getId()+"】 读取加密狗:"+i);
                    sd.readUserPin();
                    Thread.sleep(50);
                }
                System.out.println("线程【"+this.getId()+"】读取加密狗完成");
                System.out.println("共消耗"+((System.currentTimeMillis() - begin)/1000) + "秒");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } finally {
                sd.close();
            }

        }
    }
}




