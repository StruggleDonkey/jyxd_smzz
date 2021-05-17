package com.jyxd.web.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectUtil {

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    /**
     * 判断数据是否为空
     *
     * @param object
     * @return
     */
    public static boolean objectStrIsNull(Object object) {
        if (Objects.isNull(object)) {
            return true;
        }
        if (StringUtils.isEmpty(String.valueOf(object))) {
            return true;
        }
        return false;
    }

}
