package top.fedoseev.restaurant.voting.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
@EnableCaching
public class ApplicationConfig {

    private static final int MAX_PAYLOAD_LENGTH = 1000;

    @Bean
    Module module() {
        return new Hibernate5Module();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(MAX_PAYLOAD_LENGTH);
        filter.setIncludeHeaders(false);
        return filter;
    }

}
