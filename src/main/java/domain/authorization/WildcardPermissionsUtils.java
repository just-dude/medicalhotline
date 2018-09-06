package domain.authorization;

import org.apache.shiro.authz.permission.WildcardPermission;

public class WildcardPermissionsUtils {


    public static boolean isWildcardStartsWith(WildcardPermission wildcardPermission,String startsWith){
        return wildcardPermission.toString().startsWith(startsWith.toLowerCase());
    }

    public static String getWildcardPartValue(WildcardPermission wildcardPermission,int index){
        String[] parts=wildcardPermission.toString().split(":");
        if(index<parts.length){
            return parts[index];
        } else{
            return null;
        }
    }
}
