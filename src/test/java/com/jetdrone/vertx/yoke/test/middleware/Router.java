package com.jetdrone.vertx.yoke.test.middleware;

import com.jetdrone.vertx.yoke.annotations.GET;
import com.jetdrone.vertx.yoke.annotations.Path;
import com.jetdrone.vertx.yoke.middleware.YokeRequest;
import com.jetdrone.vertx.yoke.test.Response;
import com.jetdrone.vertx.yoke.test.YokeTester;
import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.testtools.TestVerticle;

import static org.vertx.testtools.VertxAssert.assertEquals;
import static org.vertx.testtools.VertxAssert.testComplete;

public class Router extends TestVerticle {

    @Path("/ws")
    public static class TestRouter {
        @GET
        public void get(YokeRequest request) {
            request.response().end("Hello ws!");
        }
    }

    @Test
    public void testAnnotatedRouter() {
        YokeTester yoke = new YokeTester(vertx);
        yoke.use(com.jetdrone.vertx.yoke.middleware.Router.from(new TestRouter()));

        yoke.request("GET", "/ws", new Handler<Response>() {
            @Override
            public void handle(Response resp) {
                assertEquals(200, resp.getStatusCode());
                assertEquals("Hello ws!", resp.body.toString());
                testComplete();
            }
        });
    }

}
