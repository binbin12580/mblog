/**
 *
 */
package mblog.web.controller.desk.posts;

import mblog.core.persist.service.PostService;
import mblog.web.controller.BaseController;
import mblog.web.controller.desk.Views;
import mtons.modules.pojos.Paging;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 标签
 * @author langhsu
 *
 */
@Controller
public class TagController extends BaseController {
    @Autowired
    private PostService postService;

    @RequestMapping("/tag/{tag}")
    public String tag(@PathVariable String tag, Integer pn, ModelMap model) {
        Paging page = wrapPage(pn);
        try {
            if (StringUtils.isNotEmpty(tag)) {
                postService.searchByTag(page, tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.put("page", page);
        model.put("tag", tag);
        return getView(Views.TAGS_TAG);
    }

}
