
package com.eventCamel;

import dto.EventDto;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class CamelRouterTest {

    @Test
    public void testEventsEndpoint() {
        given()
                .when().get("/events")
                .then()
                .statusCode(200);
    }

    @Test
    public void testEventsClientIdEndpoint() {
        given()
                .when().get("/events/0000abf8-d1f5-4536-8fb0-36fe934b1f56")
                .then()
                .body("id", hasItems(1))
                .statusCode(200);
    }

    @Test
    public void testCreateEventEndpoint() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("addrNbr", "00000001")
                        .add("transId", "0000abf8-d1f5-4536-8fb0-36fe934b1f45")
                        .add("clientId", "RPS-00006")
                        .add("eventCnt", 5)
                        .add("eventId", "80fee0d0-3ad5-4f02-993e-0040799f9935")
                        .add("locationCd", "DESTINATION")
                        .add("locationId1", "T8C")
                        .add("locationId2", "1J7")
                        .add("rcNum", "10002")
                        .add("transTms", "20151022102011927EDT")
                        .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/create")
                .then()
                .statusCode(200);

    }

    @Test
    public void testUpdateEventEndpoint() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("addrNbr", "00000001")
                        .add("transId", "0000abf8-d1f5-4536-8fb0-36fe934b1f45")
                        .add("clientId", "RPS-00006")
                        .add("eventCnt", 5)
                        .add("eventId", "80fee0d0-3ad5-4f02-993e-0040799f9935")
                        .add("locationCd", "DESTINATION ALTERED")
                        .add("locationId1", "T8C")
                        .add("locationId2", "1J7")
                        .add("rcNum", "10002")
                        .add("transTms", "20151022102011927EDT")
                        .add("id", 1)
                                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/events/0000abf8-d1f5-4536-8fb0-36fe934b1f45")
                .then()
                .statusCode(200);

    }

    @Test
    public void testDeleteEventEndpoint() {

        given()
                .when()
                .delete("/delete/RPS-00001")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

    }

}
