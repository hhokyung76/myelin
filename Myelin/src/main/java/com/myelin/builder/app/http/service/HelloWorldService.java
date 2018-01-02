package com.myelin.builder.app.http.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.myelin.builder.server.main.Customer;

@Component
public class HelloWorldService {
	private static final Logger logger = LogManager.getLogger(HelloWorldService.class);

	@Value("${name:test2ssasssdsfasaa73}")
	private String name;

    @Autowired
    JdbcTemplate jdbcTemplate;

	public String getHelloMessage() {
		logger.info("good");
//		List<RestaurantVo> list = restaurantMapper.getRestaurantsList();
//		logger.info(list.toString());
		jdbcTemplate.query(
                "SELECT id, name, email FROM users ", new Object[] { },
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("name"), rs.getString("email"))
        ).forEach(customer -> logger.info(customer.toString()));
		
		return "Helldso " + this.name;
	}

}