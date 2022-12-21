package com.hq.onlinefast;

import cn.hutool.json.JSONUtil;
import com.hq.onlinefast.bean.User;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.druid.DruidPlugin;
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
    static String jdbcUrl = "jdbc:mysql://localhost:3306/liteflow?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    static String user = "root";
    static String password = "admin";

    public static DruidPlugin createDruidPlugin() {
        DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, user, password);
        return druidPlugin;
    }

    public static void initActiveRecordPlugin() {
        DruidPlugin druidPlugin = createDruidPlugin();

        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        arp.setDevMode(true);
        arp.setShowSql(true);

        // 添加 sql 模板文件，实际开发时将 sql 文件放在 src/main/resources 下
//        arp.addSqlTemplate("com/jfinal/plugin/activerecord/test.sql");

        // 所有映射在生成的 _MappingKit.java 中自动化搞定
//        _MappingKit.mapping(arp);

        // 先启动 druidPlugin，后启动 arp
        druidPlugin.start();
        arp.start();
    }
    @Override
    public void run(String... args) throws Exception {
//        JFinal.start();
//        UndertowServer.start(DemoConfig.class, 8008, true);
        initActiveRecordPlugin();
        Record user0 = new Record().set("name", "James").set("age", 25).set("id",6);
//        log.info("jfinal0:{}",user0.toString());
//        Db.save("user", user0);
        Record user1 = Db.findById("user", 1);
        log.info("jfinal:{}",user1);
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
        DeploymentOptions options1 = new DeploymentOptions().setWorker(true).setInstances(10).setWorkerPoolName("worker").setWorkerPoolSize(20);
//  val options2 = DeploymentOptions().setWorker(true).setInstances(2).setWorkerPoolName("test-2").setWorkerPoolSize(10)
        vertx.deployVerticle("com.hq.onlinefast.vertx.BusinessVerticle",options1);
        vertx.deployVerticle("com.hq.onlinefast.vertx.WebServerVerticle", options0);

        log.info("vert.x启动完成");
    }
}
