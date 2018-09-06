package common;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import common.beanFactory.BeanFactoryProvider;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;


@RunWith(BlockJUnit4ClassRunner.class)
@ContextConfiguration(classes = {javaConfig.spring.test.ApplicationConfig.class})
public abstract class SpringContextTestCase {

    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Before
    public void setUp() throws Exception {
        SecurityUtils.setSecurityManager((SecurityManager) BeanFactoryProvider.getBeanFactory().getBean("securityManager"));
        SecurityUtils.getSubject().login(new UsernamePasswordToken("admin", "123"));
    }
}
