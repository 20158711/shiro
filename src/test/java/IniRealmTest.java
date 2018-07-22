import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {

    @Test
    public void testAuthentication(){

        IniRealm iniRealm=new IniRealm("classpath:user.ini");
        //构建security环境
        DefaultSecurityManager manager=new DefaultSecurityManager();
        manager.setRealm(iniRealm);
        //主体提交谁认证
        SecurityUtils.setSecurityManager(manager);
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("yanbing1025","123456");
        subject.login(token);

        System.out.println(subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermission("user:delete");
    }

}
