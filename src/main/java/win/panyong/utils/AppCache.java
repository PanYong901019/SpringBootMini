package win.panyong.utils;


import com.easyond.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class AppCache {

    //系统参数配置缓存信息
    private static Map<String, String> systemConfig = null;
    private static List<String> requestMappingList = new ArrayList<>();

    public Map<String, String> getSystemConfig() {
        if (systemConfig == null) {
            throw new AppException("读取配置失败");
        }
        return systemConfig;
    }

    public void setSystemConfig(Map<String, String> systemConfig) {
        AppCache.systemConfig = systemConfig;
    }

    public String getConfigValue(String key, String defaultValue) {
        String value = "";
        value = getSystemConfig().get(key);
        if (StringUtil.invalid(value)) {
            value = defaultValue;
        }
        return value;
    }

    public List<String> getRequestMappingList() {
        return new ArrayList<>(new TreeSet<>(requestMappingList));
    }

    public void setRequestMappingList(List<String> requestMappingList) {
        AppCache.requestMappingList = requestMappingList;
    }
}
