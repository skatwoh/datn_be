package be.bds.bdsbes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Configuration
public class LocaleConfiguration extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    @Value("${spring.messages.basename}")
    private String messageBasename;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : parseLocale(headerLang);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        rs.setBasename(messageBasename);
        return rs;
    }

    private Locale parseLocale(String languageCode) {
        try {
            return Locale.forLanguageTag(languageCode);
        } catch (Exception e) {
            return Locale.getDefault();
        }
    }
}