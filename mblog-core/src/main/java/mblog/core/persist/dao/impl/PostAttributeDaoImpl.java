package mblog.core.persist.dao.impl;

import mblog.core.persist.dao.PostAttributeDao;
import mblog.core.persist.entity.PostAttribute;
import mtons.modules.annotation.Repository;
import mtons.modules.persist.impl.BaseRepositoryImpl;

/**
 * Created by langhsu on 2017/9/27.
 */
@Repository(entity = PostAttribute.class)
public class PostAttributeDaoImpl extends BaseRepositoryImpl<PostAttribute> implements PostAttributeDao {
    @Override
    public void submit(PostAttribute attr) {
        PostAttribute po = get(attr.getId());
        if (po != null) {
            po.setContent(attr.getContent());
        } else {
            save(attr);
        }
    }
}
