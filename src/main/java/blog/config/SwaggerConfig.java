package blog.config;
import blog.controller.RestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import blog.controller.HomeController;
import blog.controller.PostController;
import blog.controller.UserController;


import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan(basePackageClasses = {UserController.class,PostController.class,HomeController.class, RestController.class})
@Configuration
public class SwaggerConfig {

  @Bean
  public Docket productsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .select()
      .apis(RequestHandlerSelectors.basePackage("blog.controller"))
      .paths(PathSelectors.regex("/api.*"))
      .build();
  }
}
