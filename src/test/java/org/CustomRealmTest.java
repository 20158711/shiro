package org;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest{

    @Test
    public void test(){

        CustomRealm realm=new CustomRealm();

        DefaultSecurityManager manager=new DefaultSecurityManager();
        manager.setRealm(realm);

        //加密
        HashedCredentialsMatcher matcher=new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        realm.setCredentialsMatcher(matcher);

        SecurityUtils.setSecurityManager(manager);
        Subject subject=SecurityUtils.getSubject();

        //UsernamePasswordToken token=new UsernamePasswordToken("yanbing1025","123456");
        UsernamePasswordToken token=new UsernamePasswordToken("yanbing1025","123");
        subject.login(token);

        System.out.println(subject.isAuthenticated());
//        subject.checkPermission("user:add");
//        subject.checkRole("admin");

    }

}