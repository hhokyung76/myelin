package com.ltmh.core.vertx.server;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.ltmh.core.vertx.conf.ApplicationConfiguration;

@Component
public class VertxServerVerticle extends AbstractVerticle {
	private static final Logger log = LogManager.getLogger(VertxServerVerticle.class);

	@Autowired
	private ApplicationConfiguration applicationConfiguration;
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private Environment env;

	private List<String> names = new ArrayList<>();

	@Override
	public void start() throws Exception {
		super.start();
		log.info("=============================================================");
		log.info("## Vert.x HttpServer Starting...");
		log.info("## Vert.x HttpServer vertx =" + vertx);
		log.info("## Vert.x HttpServer vertx =" + applicationConfiguration.httpPort());
		log.info("=============================================================");
		Vertx vertx = Vertx.vertx();
		vertx.createHttpServer().requestHandler(router()::accept).listen(applicationConfiguration.httpPort());
	}

	private Router router() {
		Router router = Router.router(vertx);
		router.route("/info").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("Content-Type", "application/json");
			response.end(Json.encodePrettily(discoveryClient.getInstances(applicationConfiguration.applicationName())));
		});
		router.route("/info2").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("Content-Type", "application/json");
			response.end(Json.encodePrettily(env.getProperty("spring.application.name")));
		});
		router.route("/info3").handler(routingContext -> {
			HttpServerResponse response = routingContext.response();
			response.putHeader("Content-Type", "application/json");
			response.end(Json.encodePrettily(env.getProperty("spring.application.name")));
		});
		router.route("/api/whiskies*").handler(BodyHandler.create());
		// router.post("/api/whiskies").handler(this::addOne);
		
		router.route().handler(BodyHandler.create());
		router.post("/names2").handler(routingContext -> {
		   System.out.println(routingContext.getBodyAsString());
		});
		router.route(HttpMethod.POST, "/names").handler(BodyHandler.create());
		router.route(HttpMethod.POST, "/names").handler(rc -> {
			rc.request().bodyHandler(body -> {
			    System.out.println(body.toString());
			  });
			
			rc.response().write("dfskjlfgdskldfjksl"); 
			// Read the body
//			String sectionType = rc.request().getParam("sectionId");
//			JsonObject j = rc.getBodyAsJson();
//
//			System.out.println("rc: " + rc);
//			System.out.println("rc.getbody: " + rc.getBodyAsString());
//			String name = rc.getBodyAsString();
//			if (name.isEmpty()) {
//				// Invalid body -> Bad request
//				rc.response().setStatusCode(400).end();
//			} else if (names.contains(name)) {
//				// Already included name -> Conflict
//				rc.response().setStatusCode(409).end();
//			} else {
//				// Add the name to the list -> Created
//				names.add(name);
//				rc.response().setStatusCode(201).end(name);
//			}
		});

		return router;
	}

	private void addOne(RoutingContext routingContext) {
		final Whisky whisky = Json.decodeValue(routingContext.getBodyAsString(), Whisky.class);
		// products.put(whisky.getId(), whisky);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(Json.encodePrettily(whisky));
	}
}