package com.sample.shop.config.swagger;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableWebMvc //세큐리티와 연결할때 404떠서 추가
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurationSupport {

    private static final String API_NAME = "Study API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Study API 명세서";

    @Bean
    public Docket api() {
        Parameter parameterBuilder = new ParameterBuilder()
            .name(HttpHeaders.AUTHORIZATION)
            .description("Access Token")
            .modelRef(new ModelRef("string"))
            .parameterType("header")
            .required(false)
            .build();

        List<Parameter> globalParameters = new ArrayList<>();
        globalParameters.add(parameterBuilder);

        return new Docket(DocumentationType.SWAGGER_2)
            .globalOperationParameters(globalParameters)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sample.shop"))
            .paths(PathSelectors.any())
            .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title(API_NAME)
            .version(API_VERSION)
            .description(API_DESCRIPTION)
            .build();
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");

        super.addResourceHandlers(registry);
    }

}
