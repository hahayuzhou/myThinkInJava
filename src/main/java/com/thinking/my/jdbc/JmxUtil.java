package com.thinking.my.jdbc;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * @Description
 * @Author liyong
 * @Date 2021/3/31 5:37 下午
 **/
public class JmxUtil {
    public static ObjectName registerJmx(ObjectName base, String keyprop, Object obj) {
        ObjectName oname = null;
        try {
            oname = getObjectName(base, keyprop);
            if (oname != null) ManagementFactory.getPlatformMBeanServer().registerMBean(obj, oname);
        } catch (Exception e) {
        }
        return oname;
    }

    public static void unregisterJmx(ObjectName oname) {
        if (oname ==null) return;
        try {
            ManagementFactory.getPlatformMBeanServer().unregisterMBean(oname);
        } catch (Exception e) {
        }
    }

    private static ObjectName getObjectName(ObjectName base, String keyprop)
            throws MalformedObjectNameException {
        if (base == null) return null;
        StringBuilder OnameStr = new StringBuilder(base.toString());
        if (keyprop != null) OnameStr.append(keyprop);
        ObjectName oname = new ObjectName(OnameStr.toString());
        return oname;
    }
}
