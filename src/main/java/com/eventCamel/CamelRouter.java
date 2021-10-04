package com.eventCamel;


import dto.EventDto;
import dto.TestDto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CamelRouter extends RouteBuilder {


    @Override
    public void configure() throws Exception{

        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        restConfiguration().bindingMode(RestBindingMode.auto)
                .component("platform-http")
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/").port(8080)
                .apiContextPath("/openapi")
                .apiProperty("api.title", "Camel Quarkus Demo API")
                .apiProperty("api.version", "1.0.0-SNAPSHOT")
                .apiProperty("cors", "true")
                .bindingMode(RestBindingMode.auto);

        rest().produces("application/json")

                .get("/events").description("list all events").route().routeId("restevents").to("sql:select * from eventdto")
                .choice().when(body().isNull()).setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404))
                .endChoice().otherwise()
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200)).endRest()


                .get("/events/{clientId}")
                .description("Consult an event by clientId")
                .route().routeId("restquerybyclientid").to("sql:select * from eventdto where clientid = :#clientid").choice()
                .when(body().isNull()).setHeader(Exchange.HTTP_RESPONSE_CODE, constant(404)).endChoice().otherwise()
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .endRest()

                .post("/create").type(EventDto.class)
                .description("Create new Event")
                .route().routeId("eventinsert")
                .to("jpa:" + EventDto.class.getName() + "?usePersist=true")
                .endRest()

                .put("/update").type(EventDto.class)
                .description("Update Event")
                .responseMessage().code(200).message("event updated").endResponseMessage()
                .responseMessage().code(500).message("Update failed").endResponseMessage()
                .route().routeId("eventupdate")
                .process(new Processor() {
                    @Override
                    public void process(final Exchange exchange) {
                        EventDto event = exchange.getIn().getBody(EventDto.class);
                        event.setId(event.getId());
                    }
                })
                .to("jpa:" + EventDto.class + "?useExecuteUpdate=true")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .endRest()

                .delete("/delete/{clientid}")
                .description("Delete an Event")
                .route().routeId("resteventdelete").to("sql:DELETE FROM eventdto WHERE clientid = :#clientid;")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204))
                .endRest();
    }
}
