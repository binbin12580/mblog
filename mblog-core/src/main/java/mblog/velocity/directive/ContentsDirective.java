/**
 * 
 */
package mblog.velocity.directive;

import mblog.base.context.SpringContextHolder;
import mblog.base.lang.Consts;
import mblog.core.biz.PostBiz;
import mblog.core.data.Post;
import mblog.velocity.BaseDirective;
import mblog.velocity.handler.RenderHandler;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.ServletRequest;
import java.io.IOException;

/**
 * 文章内容查询
 * 
 * 示例：
 * 	请求：http://mtons.com/index?ord=newest&pn=2
 *  使用：#contents(groupId, 'paging')
 *  解析： ord, pn 等参数从request中获取 , groupId 从标签中获取
 *  
 * @author langhsu
 *
 */
public class ContentsDirective extends BaseDirective {
	private PostBiz postPlanet;

	@Override
	public void initBean() {
		postPlanet = SpringContextHolder.getBean(PostBiz.class);
	}

	@Override
	public String getName() {
		return "contents";
	}

	@Override
	public int getType() {
		return BLOCK;
	}

	@Override
	public boolean render(RenderHandler handler) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, IOException {
		ServletRequest request = handler.getRequest();
		
		// request 获取参数
        String ord = ServletRequestUtils.getStringParameter(request, "ord", Consts.order.NEWEST);
        int pn = ServletRequestUtils.getIntParameter(request, "pn", 1);
        
        // 标签中获取参数
        int groupId = handler.getIntParameter(0);
        String alias = handler.getStringParameter(1);

		Pageable pageable = new PageRequest(pn - 1, 10);
		Page<Post> result = postPlanet.paging(pageable, groupId, ord);

		handler.put(alias, result);
		handler.doRender();
		
		postRender(handler.getContext());
		return true;
	}

}
