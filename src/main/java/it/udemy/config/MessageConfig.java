package it.udemy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@Configuration
public class MessageConfig {

	@Bean(name = "validator")
	LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(this.messageSource());
		return bean;
	}
	
	@Bean
	ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultLocale(Locale.forLanguageTag("us"));
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setAlwaysUseMessageFormat(true);
		
		return messageSource;
	}
	
	@Bean
	LocaleResolver localeResolver() {
		CookieLocaleResolver cookie = new CookieLocaleResolver("localeInfo");
		cookie.setCookieHttpOnly(true);
		cookie.setDefaultLocale(Locale.forLanguageTag("it"));
		
		return cookie;
	}
}
