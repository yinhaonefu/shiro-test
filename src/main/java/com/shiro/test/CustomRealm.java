package com.shiro.test;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm{

    static Map<String,String> userMap = new HashMap<>();

    String realmName = "customRealm";

    String salt = "shiro";//密码加盐

    {
        userMap.put("zhangsan", new Md5Hash("123" + salt).toString());
        userMap.put("lisi", new Md5Hash("456" + salt).toString());

        super.setName(realmName);//设置realm名称
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRolesByUsername(username);
        Set<String> permissions = getPermissionsByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    //模拟从数据库获取角色
    private Set<String> getRolesByUsername(String username){
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    //模拟从数据库获取权限
    private Set<String> getPermissionsByUsername(String username){
        Set<String> permissions = new HashSet<>();
        permissions.add("user:update");
        permissions.add("user:delete");
        permissions.add("user:select");
        return permissions;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        String password = getPasswordByUsername(username);
        if(null == password){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, realmName);
        return authenticationInfo;
    }

    //模拟从数据库获取用户名密码
    private String getPasswordByUsername(String username){
        return userMap.get(username);

    }
}
