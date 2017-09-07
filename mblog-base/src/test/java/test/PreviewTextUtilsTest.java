package test;

import mtons.modules.utils.PreviewTextUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * @author langhsu on 2015/8/29.
 */
public class PreviewTextUtilsTest {

    public static void main(String[] args) {
        String html = "<p>这是一个测试文本</p><p><img src='test.png'></p><script>alert('1');</script>  select * from tb_user where 1=1";

        System.out.println(PreviewTextUtils.getText(html, 5));

        System.out.println(PreviewTextUtils.getImgSrc(html));

        System.out.println(HtmlUtils.htmlEscape(html));

        System.out.println(StringEscapeUtils.escapeSql(html));
    }

}
