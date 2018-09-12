package com.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yinhao on 2018/9/12.
 */
public class AuthorizationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    String username = "zhangsan";

    String password = "123";

    String adminRole = "admin";

    String userRole = "user";

    boolean rememberMe = true;

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount(username, password, adminRole, userRole);
    }

    @Test
    public void authorizationTest(){

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        defaultSecurityManager.setRealm(simpleAccountRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);

        subject.login(token);

        System.out.println(subject.isAuthenticated());

        subject.checkRole(adminRole);

    }
}
