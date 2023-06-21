package com.company.train.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("train_AppConfig")
@PropertySource("classpath:/train.properties")
public class AppConfig {
}