package com.hq.onlinefast.vertx;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hq.onlinefast.bean.User;
import com.hq.onlinefast.mapper.UserMapper;
import com.hq.onlinefast.util.SpringUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.impl.codecs.ClusterSerializableCodec;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.impl.ClusterSerializableUtils;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WebServerVerticle extends AbstractVerticle {
    private static final ObjectMapper mapper = new ObjectMapper();
    private List<Route> routes=new ArrayList<Route>();
    // Verticle部署时调用
    public void start() {
        log.info("WebServerVerticle启动1");
//        try{
//            User user = new User();
//            UserMapper userMapper = SpringUtil.getBean(UserMapper.class);
//            List<User> users1=userMapper.selectList(null);
//            List<User> users = user.selectAll();
//            log.info("user对象获取的数据：{}",users.toString());
//            log.info("userMapper对象获取的数据：{}",users1.toString());
//        }catch (Exception e){
//            log.info(e.getMessage());
//            e.printStackTrace();
//        }

        //定义http
        HttpServer server = vertx.createHttpServer();
        //定义route规则，路由到对应路径后的操作
        Router router = Router.router(vertx);
        server.requestHandler(router).listen(8181, http -> {
            if (http.succeeded()) {
//                startPromise.complete();
                log.info("http服务启动成功");

            } else {
                log.info("http服务启动失败：{}",http.cause());
//                startPromise.fail(http.cause());
            }
        });
        EventBus eb = vertx.eventBus();
        eb.consumer("generate.url", message -> {
            JsonObject paramobject=(JsonObject)message.body();
            log.info("{}:{}}: " ,this.toString(), paramobject.toString());
            String url = paramobject.getString("url");
            String param = paramobject.getString("param");
            Route route11 = router.route().path("/"+url);

            route11.handler(ctx1->{
                String subparam = ctx1.request().getParam(param);
                HttpServerResponse response1 = ctx1.response();
                response1.putHeader("content-type", "text/json");
                //请求参数传递给其他verticle,让其他的verticle处理后返回响应

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
            routes.add(route11);
        });
        Route route1 = router.route().path("/generateUrl");
        route1.handler(ctx->{
            String url = ctx.request().getParam("url","0");
            String param = ctx.request().getParam("param","0");
            JsonObject paramJson = new JsonObject();
            paramJson.put("url",url);
            paramJson.put("param",param);
            eb.publish("generate.url", paramJson);
            //判断url是否已经存在，已经存在的话，返回当前接口已存在的
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/json");
            //请求参数传递给其他verticle,让其他的verticle处理后返回响应
//      EventBus eb = vertx.eventBus();

            JsonObject object = new JsonObject();
            object.put("classid",this.toString());
            object.put("threadname",Thread.currentThread().getName());
            object.put("url","http://localhost:8181/"+url);

            response.end(object.toString());
        });
        Route route2 = router.route().path("/printUrlList");
        route2.handler(ctx->{
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/json");
            JsonObject object = new JsonObject();
            object.put("classid",this.toString());
            object.put("threadname",Thread.currentThread().getName());
            object.put("threadid",Thread.currentThread().getId());
            JsonArray objects = new JsonArray();
            router.getRoutes().forEach(e->{
                objects.add(e.getPath());
            });
            JsonArray routerArray = new JsonArray();
            router.getRoutes().forEach(e->{
                objects.add(e.getPath());
            });
            routes.forEach(e->{
                routerArray.add(e.getPath());
            });
            object.put("urls",objects);
            object.put("routers",routerArray);
            response.end(object.toString());
        });

    }

    // 可选 - Verticle撤销时调用
    public void stop() {
        log.info("WebServerVerticle停止");
    }
}
