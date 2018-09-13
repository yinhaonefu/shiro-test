package com.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {

    String salt = "shiro";

    @Test
    public void customRealmTest(){

        CustomRealm customRealm = new CustomRealm();

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        defaultSecurityManager.setRealm(customRealm);


        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");//加密算法名称
        matcher.setHashIterations(1);//加密次数
        customRealm.setCredentialsMatcher(matcher);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123" + salt);

        subject.login(token);

        System.out.println(subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermission("user:update");

        subject.logout();

    }
}
