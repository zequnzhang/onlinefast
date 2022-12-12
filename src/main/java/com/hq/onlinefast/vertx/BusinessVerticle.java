package com.hq.onlinefast.vertx;

import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessVerticle extends AbstractVerticle {

    // Verticle部署时调用
    public void start() {
        log.info("BusinessVerticle启动");
    }

    // 可选 - Verticle撤销时调用
    public void stop() {
        log.info("BusinessVerticle停止");
    }
}
