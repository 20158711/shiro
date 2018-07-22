import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm realm=new SimpleAccountRealm();

    @Before
    public void addUser(){
        realm.addAccount("yanbing1025","123456","admin","user");
    }

    @Test
    public void testAuthentication(){
        //构建security环境
        DefaultSecurityManager manager=new DefaultSecurityManager();
        manager.setRealm(realm);
        //主体提交谁认证
        SecurityUtils.setSecurityManager(manager);
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("yanbing1025","123456");
        subject.login(token);

        System.out.println(subject.isAuthenticated());
        subject.checkRoles("admin","user");

    }

}
