package com.thwet.react.test.config;
import java.util.Properties;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.thwet.react.test")
public class SpringMvcWebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/app1/**").addResourceLocations("/app1/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /*
         * registry.addViewController("/").setViewName("login"); registry.addViewController("/login").setViewName("login");
         */
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateEngine((SpringTemplateEngine) templateEngine());
        return resolver;
    }

    @Bean
    public TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCacheable(false);
        return resolver;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(50000000);
        return commonsMultipartResolver;
    }

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("frobo.test01@gmail.com");
        mailSender.setPassword("frobopassword");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");// Prints out everything on screen

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

    @Bean
    ApplicationEventMulticaster applicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        eventMulticaster.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
        return eventMulticaster;
    }

//    @Bean
//    public ModelMapper modelMapper() {
//        ModelMapper mapper = new ModelMapper();
//        mapper.addMappings(new PropertyMap<University, UniversityDto>() {
//            @Override
//            protected void configure() {
//                map(source.id, destination.id);
//                map(source.city.getRegion().getName(), destination.region);
//                map(source.city.getName(), destination.city);
//                map(source.noOfPeople, destination.noOfPeople);
//                map(source.getModified(), destination.modifiedDate);
//            }
//        });
//
//        mapper.addConverter(new Converter<Date, String>() {
//            @Override
//            public String convert(MappingContext<Date, String> arg0) {
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                if (arg0 == null) {
//                    return dateFormat.format(Calendar.getInstance());
//                }
//                return dateFormat.format(arg0.getSource());
//            }
//        });
//        return mapper;
//    }

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addConverter(new Converter<Date, String>() {

			@Override
			public String convert(MappingContext<Date, String> arg0) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				if (arg0 == null) {
					return dateFormat.format(Calendar.getInstance());
				}
				return dateFormat.format(arg0.getSource());
			}
		});

		return mapper;
	}

    @Bean
    public HttpTransport httpTransport() {
        return new ApacheHttpTransport();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return new GsonFactory();
    }
}

