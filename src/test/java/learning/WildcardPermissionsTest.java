package learning;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.junit.Assert;
import org.junit.Test;

public class WildcardPermissionsTest {

    @Test
    public void testPermissions(){
        Assert.assertTrue(new WildcardPermission("userAccount:*:*:*").implies(
                new WildcardPermission("userAccount:save:*:*"))
        );
    }
}
