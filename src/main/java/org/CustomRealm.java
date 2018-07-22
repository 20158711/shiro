package org;

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

/**
 * 自定义realm
 */
public class CustomRealm extends AuthorizingRealm {

    Map<String,String> userMap=new HashMap<>();
    {
        userMap.put("yanbing1025","202cb962ac59075b964b07152d234b70");
        super.setName("customRealm");
    }

    @Override
    /**
     * 授权
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName= (String) principals.getPrimaryPrincipal();
        Set<String> roles=getRolesByUsername(userName);
        Set<String> permissions=getPermissionByUserName(userName);
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }

    public static void main(String[] args) {
        Md5Hash md5Hash=new Md5Hash("123");
        System.out.println(md5Hash.toString());
    }

    @Override
    /**
     * 认证
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1,从主体传过来的认证信息中获取用户名
        String username= (String) token.getPrincipal();
        //2,到数据库中获取凭证
        String password=getPasswordByUserName(username);
        if (password == null) {
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo("yanbing1025",password,"customRealm");
        return authenticationInfo;
    }

    /**
     * 模拟数据库获取用户认证数据
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName){
        return userMap.get(userName);
    }

    /**
     * 模拟数据库获取角色数据
     * @param username
     * @return
     */
    private Set<String> getRolesByUsername(String username){
        Set<String> set=new HashSet<>();
        set.add("admin");
        return set;
    }

    /**
     * 模拟数据库获取权限数据
     * @param username
     * @return
     */
    private Set<String> getPermissionByUserName(String username){
        Set<String> set=new HashSet<>();
        set.add("user:delete");
        set.add("user:add");
        return set;
    }
}
