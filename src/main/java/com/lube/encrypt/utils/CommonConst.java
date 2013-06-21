package com.lube.encrypt.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-1
 * Time: 上午12:26
 * To change this template use File | Settings | File Templates.
 */
public class CommonConst {
    public static final String STRING_SEED = "P7o5ADovz7wajNoghr4eBzhA45XdJDi9PqRHxglnAueEH/x7jyjYcezumJhrGFZ183b+EhbI4gKH8Oad4WulQ1rLQitYDygqVZSjurilDJuQBTdvboZLi0w7fb9BdXMEY4UT7yPIOLi5xd2Pc5UWBqXfkehYe9T4XB1XJKz+gNo=";
    public static final String STRING_PID = "B19hs+fYcQI/hk8kodGxMFCJzamtdFV9J92PaeknwMCKMJ2pJIR7RtM/z4Xzy3YGTmYYbswS8xh21uxVM4kNc85P34D7kPDhOzRxBX37vt/aVEC6jy9yist7QipOGrr2Dgydn3kIJbHUzjO9utyPvod2+L8Xr6xHc8tmkHF/DDQ=";
    public static final String STRING_SO_PIN = "Kw7gnxewesFdB2AvNMM/lYBSV+ncOA883BU1vFOvAs/YEPKTN0UIdrIve4y7OBu9h4yYBN+949uivKzLzQ4Dvk8fJeTAMV4wrh2OB5a02TVSHWpaPj34hzYmWPSOPZI/kYEg+tzNcGYfF3fFOdWAnM4g7+LklHMFpORPOtSbOow=";
    public static final String STRING_USER_PIN = "V7zlPqN3ZxMD66BpFCiurXYHf2//UjsxACwKc4mALboAS5d7ktnC1UxbwGhs2k4hKvQzSU6CyyEa3CsAs9m6vGrkVMHjyTbVje37cSd8RebcCipZa93xqGqOR9AUCggTuZW28NwnlBrvZV5tFdtyPhFxaerkVKQChA3CLIZMJ4M=";



    /**  ET99加密狗异常代码的错误描述 */
    public static final Map<Integer, String> MAP_ERROR_MESSAGE = new HashMap<Integer, String>(0);
    static{
        MAP_ERROR_MESSAGE.put(0x00,"执行成功");
        MAP_ERROR_MESSAGE.put(0x01,"访问被拒绝，权限不够");
        MAP_ERROR_MESSAGE.put(0x02,"通讯错误，没有打开设备");
        MAP_ERROR_MESSAGE.put(0x03,"无效的参数，参数出错");
        MAP_ERROR_MESSAGE.put(0x04,"没有设备PID");
        MAP_ERROR_MESSAGE.put(0x05,"打开指定的设备失败");
        MAP_ERROR_MESSAGE.put(0x06,"硬件错误");
        MAP_ERROR_MESSAGE.put(0x07,"未知错误");
        MAP_ERROR_MESSAGE.put(0x0F,"验证PIN码掩码");
        MAP_ERROR_MESSAGE.put(0xFF,"验证PIN码错误且永远不锁死");
    }
}
