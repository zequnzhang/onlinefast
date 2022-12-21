package com.hq.onlinefast.vertx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GenerateUrlVerticle extends AbstractVerticle {
    private static final ObjectMapper mapper = new ObjectMapper();
    // Verticle部署时调用
    public void start() {
        log.info("GenerateUrlVerticle启动");

        //定义http
        HttpServer server = vertx.createHttpServer();
        //定义route规则，路由到对应路径后的操作
        Router router = Router.router(vertx);
        server.requestHandler(router).listen(8765, http -> {
            if (http.succeeded()) {
//                startPromise.complete();
                log.info("http服务启动成功");

            } else {
                log.info("http服务启动失败：{}",http.cause());
//                startPromise.fail(http.cause());
            }
        });
        Route route1 = router.route().path("/generateUrl");
        route1.handler(ctx->{
            String url = ctx.request().getParam("url","0");
            String param = ctx.request().getParam("param","0");
            String chain = ctx.request().getParam("chain","0");
            //判断url是否已经存在，已经存在的话，返回当前接口已存在的
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/json");
            //请求参数传递给其他verticle,让其他的verticle处理后返回响应
//      EventBus eb = vertx.eventBus();
            Route route11 = router.route().path("/"+url);
            route11.handler(ctx1->{
                String subparam = ctx1.request().getParam(param);
                HttpServerResponse response1 = ctx1.response();
                response1.putHeader("content-type", "text/json");
                //请求参数传递给其他verticle,让其他的verticle处理后返回响应
                EventBus eb = vertx.eventBus();
                ObjectNode rootNode = mapper.createObjectNode();
                rootNode.put("url",url);
                rootNode.put("param",subparam);
                JsonObject object = new JsonObject();
                object.put("url",url);
                object.put("param",subparam);
//                ClusterSerializableUtils
//                ClusterSerializableCodec
                eb.request("business", rootNode, ar -> {
                    if (ar.succeeded()) {
                        log.info("Received reply: " + ar.result().body());
                        response1.end( ar.result().body().toString());
                    }else{
                        response1.end( ar.cause().getMessage());
                    }
                });

            });
            response.end("新接口地址 http://localhost:8181/"+url);
        });
    }

    // 可选 - Verticle撤销时调用
    public void stop() {
        log.info("WebServerVerticle停止");
    }
}
