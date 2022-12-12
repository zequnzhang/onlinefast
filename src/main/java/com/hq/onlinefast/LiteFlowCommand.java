package com.hq.onlinefast;

import cn.hutool.json.JSONUtil;
import com.hq.onlinefast.bean.User;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.util.JsonUtil;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Component
public class LiteFlowCommand implements CommandLineRunner {

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private FlowExecutor flowExecutor;

    @Override
    public void run(String... args) throws Exception {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        DefaultContext context = response.getFirstContextBean();
        List<User> userList  = context.getData("userList");
        log.info(JSONUtil.parseArray(userList).toString());
////        log.info(JsonUtil.toJsonString(context.getData("student")));
//        if (response.isSuccess()){
//            log.info("执行成功");
//        }else{
//            log.info("执行失败");
//        }
        Vertx vertx = Vertx.vertx();

        DeploymentOptions options0 = new DeploymentOptions().setInstances(10);
        DeploymentOptions options1 = new DeploymentOptions().setWorker(true).setInstances(10).setWorkerPoolName("test-1").setWorkerPoolSize(20);
//  val options2 = DeploymentOptions().setWorker(true).setInstances(2).setWorkerPoolName("test-2").setWorkerPoolSize(10)
        vertx.deployVerticle("com.hq.onlinefast.vertx.BusinessVerticle",options0);
        vertx.deployVerticle("com.hq.onlinefast.vertx.WebServerVerticle", options1);
//  vertx.deployVerticle("com.sdyg.test.TestB", options2)
//  vertx.deployVerticle("com.sdyg.test.TestC", options1)
        log.info("vert.x启动完成");
    }
}
