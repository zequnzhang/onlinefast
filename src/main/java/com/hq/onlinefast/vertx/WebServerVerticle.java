package com.hq.onlinefast.vertx;

import com.hq.onlinefast.bean.User;
import com.hq.onlinefast.mapper.UserMapper;
import com.hq.onlinefast.util.SpringUtil;
import io.vertx.core.AbstractVerticle;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class WebServerVerticle extends AbstractVerticle {

    // Verticle部署时调用
    public void start() {
        log.info("WebServerVerticle启动1");
        try{
            //自动创建新的映射，端口号不能变
            //每次重启时，需要从数据库读取配置，数据库
            String id="{\"buildingCode\":\"370911003233GB00099F0001\",\"buildingName\":\"智能灯杆等设备组装项目1#研发车间\",\"projectCode\":\"370911003233GB00099F0001\",\"projectName\":\"山东诚金市政园林工程有限公司\",\"familyArray\":[{\"familyCode\":1590096,\"location\":\"高新区丰园路117号山东诚金市政园林工程有限公司智能灯杆等设备组装项目1#研发车间1单元501室\",\"unitCode\":\"370911003233GB00099F00010002\",\"peopleName\":\"山东诚金市政园林工程有限公司\",\"estimateConstructionArea\":null,\"estimateInsideArea\":null,\"estimatePublicArea\":null,\"actualConstructionArea\":761.01,\"actualInsideArea\":650.24,\"actualPublicArea\":110.77},{\"familyCode\":1590099,\"location\":\"高新区丰园路117号山东诚金市政园林工程有限公司智能灯杆等设备组装项目1#研发车间1单元401室\",\"unitCode\":\"370911003233GB00099F00010005\",\"peopleName\":\"山东诚金市政园林工程有限公司\",\"estimateConstructionArea\":null,\"estimateInsideArea\":null,\"estimatePublicArea\":null,\"actualConstructionArea\":761.01,\"actualInsideArea\":650.24,\"actualPublicArea\":110.77},{\"familyCode\":1590100,\"location\":\"高新区丰园路117号山东诚金市政园林工程有限公司智能灯杆等设备组装项目1#研发车间1单元301室\",\"unitCode\":\"370911003233GB00099F00010006\",\"peopleName\":\"山东诚金市政园林工程有限公司\",\"estimateConstructionArea\":null,\"estimateInsideArea\":null,\"estimatePublicArea\":null,\"actualConstructionArea\":761.01,\"actualInsideArea\":650.24,\"actualPublicArea\":110.77},{\"familyCode\":1590101,\"location\":\"高新区丰园路117号山东诚金市政园林工程有限公司智能灯杆等设备组装项目1#研发车间1单元201室\",\"unitCode\":\"370911003233GB00099F00010007\",\"peopleName\":\"山东诚金市政园林工程有限公司\",\"estimateConstructionArea\":null,\"estimateInsideArea\":null,\"estimatePublicArea\":null,\"actualConstructionArea\":761.01,\"actualInsideArea\":650.24,\"actualPublicArea\":110.77},{\"familyCode\":1590102,\"location\":\"高新区丰园路117号山东诚金市政园林工程有限公司智能灯杆等设备组装项目1#研发车间1单元102室\",\"unitCode\":\"370911003233GB00099F00010008\",\"peopleName\":\"山东诚金市政园林工程有限公司\",\"estimateConstructionArea\":null,\"estimateInsideArea\":null,\"estimatePublicArea\":null,\"actualConstructionArea\":303.03,\"actualInsideArea\":258.92,\"actualPublicArea\":44.11},{\"familyCode\":1590103,\"location\":\"高新区丰园路117号山东诚金市政园林工程有限公司智能灯杆等设备组装项目1#研发车间1单元601室\",\"unitCode\":\"370911003233GB00099F00010009\",\"peopleName\":\"山东诚金市政园林工程有限公司\",\"estimateConstructionArea\":null,\"estimateInsideArea\":null,\"estimatePublicArea\":null,\"actualConstructionArea\":761.01,\"actualInsideArea\":650.24,\"actualPublicArea\":110.77},{\"familyCode\":1590104,\"location\":\"高新区丰园路117号山东诚金市政园林工程有限公司智能灯杆等设备组装项目1#研发车间1单元101室\",\"unitCode\":\"370911003233GB00099F00010010\",\"peopleName\":\"山东诚金市政园林工程有限公司\",\"estimateConstructionArea\":null,\"estimateInsideArea\":null,\"estimatePublicArea\":null,\"actualConstructionArea\":273.38,\"actualInsideArea\":233.59,\"actualPublicArea\":39.79}]}";
            User user = new User();
            UserMapper userMapper = SpringUtil.getBean(UserMapper.class);
            List<User> users1=userMapper.selectList(null);
            List<User> users = user.selectAll();
            log.info("user对象获取的数据：{}",users.toString());
            log.info("userMapper对象获取的数据：{}",users1.toString());
        }catch (Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
        }

    }

    // 可选 - Verticle撤销时调用
    public void stop() {
        log.info("WebServerVerticle停止");
    }
}
