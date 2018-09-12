package com.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by yinhao on 2018/9/12.
 */
public class IniRealmTest {

    @Test
    public void iniRealmTest(){

        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        defaultSecurityManager.setRealm(iniRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");

        subject.login(token);

        System.out.println(subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");
    }
}
