package com.github.starter.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "datasource.jdbc")
@Component
public class JdbcProperties {
    private List<ConfigItem> ref;

    public void setRef(List<ConfigItem> ref) {
        this.ref = ref;
    }

    public List<ConfigItem> getRef() {
        return ref;
    }

}
