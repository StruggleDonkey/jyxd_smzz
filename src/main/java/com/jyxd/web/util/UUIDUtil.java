package com.jyxd.web.util;

import java.util.UUID;

public class UUIDUtil {

    /**
     * 生成32位UUID
     * @return uuid
     */
    public static String getUUID(){
        String uuid=UUID.randomUUID().toString();
        return uuid.replaceAll("-","");
    }
}
