// src/main/java/com/miroslav/orarend/config/GeminiClientConfig.java
package com.miroslav.orarend.config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "gemini.enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnExpression(
        "T(org.springframework.util.StringUtils).hasText('${gemini.apiKey:}') " +
                "|| systemEnvironment['GOOGLE_API_KEY'] != null " +
                "|| systemEnvironment['GEMINI_API_KEY'] != null"
)
public class GeminiClientConfig {

    @Bean
    public Client genaiClient(@Value("${gemini.apiKey:}") String propKey) {
        String key = (propKey != null && !propKey.isBlank())
                ? propKey
                : (System.getenv("GOOGLE_API_KEY") != null && !System.getenv("GOOGLE_API_KEY").isBlank())
                ? System.getenv("GOOGLE_API_KEY")
                : System.getenv("GEMINI_API_KEY");
        return Client.builder().apiKey(key).build();
    }
}
