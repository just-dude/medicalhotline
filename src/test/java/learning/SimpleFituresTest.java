package learning;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by SuslovAI on 15.08.2017.
 */
public class SimpleFituresTest {

    @Test
    public void testWorkWithMap(){
        Map<String,Object> map = new HashMap<>();
        //map.put("asd","321");
        List<Object> list = new ArrayList();
        list.add("firstEl");
        list.add("secondEl");
        map.put("dsas",list);
        assertEquals(1,map.size());
        assertTrue(!map.isEmpty());
    }
}
