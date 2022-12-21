package com.hq.onlinefast.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hq.onlinefast.bean.Script;
import com.hq.onlinefast.bean.User;
import com.hq.onlinefast.mapper.ScriptMapper;
import com.hq.onlinefast.mapper.UserMapper;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Slf4j
public class TestController {
//    private Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private FlowExecutor flowExecutor;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ScriptMapper scriptMapper;
    private static final ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/getdata")
    @ResponseBody
    public String submit(@Nullable  String reqData){
        try{
            log.info("getdata入参:{}",reqData);
            for(int i=0;i<100;i++){

            LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
            DefaultContext context = response.getFirstContextBean();
            List<User> userList  = context.getData("userList");
            log.info(JSONUtil.parseArray(userList).toString());
            Script script = scriptMapper.selectById(1L);
            script.setScriptData(script.getScriptData()+"\n" +
                    "a++");
            scriptMapper.updateById(script);
            flowExecutor.reloadRule();
            }
            return "success";
        }catch (Throwable t){
            t.printStackTrace();
            return "error";
        }
    }
    @RequestMapping(value = "/refreshRule")
    @ResponseBody
    public String refreshRule(){
        flowExecutor.reloadRule();
        List<Script> scripts = scriptMapper.selectList(null);
        scripts.forEach(System.out::print);
        return "刷新成功";
    }
    @RequestMapping(value = "/testSelect")
    @ResponseBody
    public String testSelect() {
        System.out.println(("----- selectAll method test ------"));
//        List<User> userList = userMapper.selectList(null);
//        userList.forEach(System.out::println);
//        return userList.toString();
        List<Record> users = Db.findAll("user");
        return users.toString();
    }

    @RequestMapping(value = "/testJsonParam")
    @ResponseBody
    public String testJsonParam() {
        flowExecutor.reloadRule();
        ObjectNode rootNode = mapper.createObjectNode();

        ObjectNode childNode1 = mapper.createObjectNode();

        childNode1.put("name1","val1");

        childNode1.put("name2","val2");

        rootNode.set("obj1",childNode1);

        ObjectNode childNode2 = mapper.createObjectNode();

        childNode2.put("name3","val3");

        childNode2.put("name4","val4");

        rootNode.set("obj2",childNode2);

        ObjectNode childNode3 = mapper.createObjectNode();

        childNode3.put("name5","val5");

        childNode3.put("name6","val6");

        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add(childNode1);
        arrayNode.add(childNode2);
        arrayNode.add(childNode3);

        rootNode.set("obj3",childNode3);
        rootNode.set("obj4",arrayNode);
        log.info(rootNode.toString());
        LiteflowResponse response = flowExecutor.execute2Resp("chain2",null ,rootNode);
        log.info(rootNode.toString());
        return rootNode.toString();
    }
}
