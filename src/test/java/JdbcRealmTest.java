import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class JdbcRealmTest {

    DruidDataSource dataSource=new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro?useSSL=false&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }

    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm=new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //权限开关，默认为false
        jdbcRealm.setPermissionsLookupEnabled(true);

        String sql="select password from test_user where user_name=?";
        jdbcRealm.setAuthenticationQuery(sql);

        DefaultSecurityManager manager=new DefaultSecurityManager();
        manager.setRealm(jdbcRealm);

        SecurityUtils.setSecurityManager(manager);
        Subject subject=SecurityUtils.getSubject();

        //UsernamePasswordToken token=new UsernamePasswordToken("yanbing1025","123456");
        UsernamePasswordToken token=new UsernamePasswordToken("xm","123");
        subject.login(token);

        System.out.println(subject.isAuthenticated());

//        subject.checkRole("admin");
//        subject.checkPermission("user:select");
    }

}
