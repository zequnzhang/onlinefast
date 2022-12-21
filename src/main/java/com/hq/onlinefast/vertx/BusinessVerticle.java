package com.hq.onlinefast.vertx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hq.onlinefast.mapper.UserMapper;
import com.hq.onlinefast.util.SpringUtil;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessVerticle extends AbstractVerticle {
    private FlowExecutor flowExecutor;

    private static final ObjectMapper mapper = new ObjectMapper();

    // Verticle部署时调用
    public void start() {
        flowExecutor = SpringUtil.getBean(FlowExecutor.class);
        log.info("BusinessVerticle启动");
        EventBus eventBus = vertx.eventBus();
        MessageConsumer<ObjectNode> consumer = eventBus.consumer("business");
        consumer.handler(message -> {
            //获取到链路、入参、
            //根据链路获取链路参数，将接口入参添加到链路参数中
            ObjectNode rootNode = mapper.createObjectNode();
            try {
//                JsonObject object = message.body();
//                ObjectNode jsonNode = (ObjectNode) mapper.readTree();
                ObjectNode jsonNode =message.body();
                rootNode.set("param",jsonNode);
                rootNode.put("test","test");
            } catch (Exception e) {
                e.printStackTrace();
            }

            //开启链路
            LiteflowResponse response = flowExecutor.execute2Resp("chain2",null,rootNode );
            message.reply(rootNode.toString());
        });
    }

    // 可选 - Verticle撤销时调用
    public void stop() {
        log.info("BusinessVerticle停止");
    }
}
