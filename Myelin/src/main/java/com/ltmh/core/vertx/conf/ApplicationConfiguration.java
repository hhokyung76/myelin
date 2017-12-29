package com.ltmh.core.vertx.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfiguration {
   @Autowired
   private Environment env;
   
   public String applicationName() {
       return env.getProperty("spring.application.name");
   }
   public int httpPort() {
       return env.getProperty("server.vertx.port", Integer.class);
   }
}