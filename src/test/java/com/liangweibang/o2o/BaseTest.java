package com.liangweibang.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置 Spring 和 junit 整个，junit 启动时加载 SpringIOC 容器
 * @author liangweibang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉 junit spring 配置文件的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class BaseTest {
	
}
