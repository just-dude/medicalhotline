package learning;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

/**
 * Created by Артем on 19.04.2018.
 */
public class EscapeHtmlTest {

    @Test
    public void testEscapeHtml(){
        String source="<div>\"123\"</div><script></script>";
        String escaped=StringEscapeUtils.escapeHtml4(source);
        String unscaped=StringEscapeUtils.unescapeHtml4(escaped);
        System.out.println("source: "+source);
        System.out.println("escaped: "+escaped);
        System.out.println("unscaped: "+unscaped);
    }
}
