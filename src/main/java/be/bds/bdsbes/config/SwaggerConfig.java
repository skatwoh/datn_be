//package be.bds.bdsbes.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.Collections;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig extends WebMvcConfigurationSupport {
//
//    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("be.bds.bdsbes.resource"))
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(metaData())
//                .securitySchemes(Collections.singletonList(new ApiKey("Bearer Token", "Authorization", "header")))
//                .securityContexts(Collections.singletonList(
//                        SecurityContext.builder()
//                                .securityReferences(Collections.singletonList(
//                                        new SecurityReference("Bearer Token", new AuthorizationScope[0])
//                                ))
//                                .build()
//                ));
//    }
//
//
//    private ApiInfo metaData() {
//        return new ApiInfoBuilder()
//                .title("Hotel Manager")
//                .description("\"About\"")
//                .version("1.0.0")
//                .contact(new Contact("Dao Quoc Khanh", "https://github.com/skatwoh", "khanhdq2411@gmail.com"))
//                .build();
//    }
//
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//}
