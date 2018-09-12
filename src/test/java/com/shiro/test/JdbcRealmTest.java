package com.shiro.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yinhao on 2018/9/12.
 */
public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();

    @Before
    public void before(){
        dataSource.setUrl("jdbc:mysql://localhost:3306/my_test");
        dataSource.setUsername("root");
        dataSource.setPassword("lijinqiu1983#A");
    }

    @Test
    public void jdbcRealmTest(){

        JdbcRealm jdbcRealm = new JdbcRealm();

        jdbcRealm.setDataSource(dataSource);

        jdbcRealm.setPermissionsLookupEnabled(true);//如果不开启默认false不查询权限表

        //如果表名和表结构不是shiro默认的，可以自定义认证，角色或权限查询语句
        String authenticationSql = "select password from my_users where username = ?";

        jdbcRealm.setAuthenticationQuery(authenticationSql);
//        jdbcRealm.setUserRolesQuery("");
//        jdbcRealm.setPermissionsQuery("");

        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();

        defaultSecurityManager.setRealm(jdbcRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123");

        subject.login(token);

        System.out.println(subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");
        subject.checkPermission("user:query");
    }
}
