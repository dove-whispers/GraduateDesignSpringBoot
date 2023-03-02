package com.dove.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * key配置
 *
 * @author dove_whispers
 * @date 2023-03-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "key")
public class KeyProperty {
    private String ak;
    private String sk;
}
