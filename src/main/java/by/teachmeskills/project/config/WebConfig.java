package by.teachmeskills.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "by.teachmeskills.project")
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public ViewResolver urlBasedViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    //To get access to images
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/fontawesome/**").addResourceLocations("/fontawesome/");
        registry.addResourceHandler("/jsp-scc-styles/**").addResourceLocations("/jsp-scc-styles/");
        registry.addResourceHandler("/jsp-scripts/**").addResourceLocations("/jsp-scripts/");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/sneakersShop",
                HandlerTypePredicate.forAnnotation(Controller.class));
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // LogInterceptor apply to all URLs.
//        registry.addInterceptor(new LogInterceptor());
//    }
}
