package com.pjz.job.utils;

import java.util.Properties;

public class PropertiesParser {

    Properties props;

    public PropertiesParser(Properties props) {
        this.props = props;
    }

    public String getStringProperties(String name, String def) {
        String property = props.getProperty(name, def);
        //属性值为null
        if (property == null) {
            return def;
        }
        //属性值为空字符串
        property = property.trim();
        return (property.isEmpty()) ? def : property;
    }

}
