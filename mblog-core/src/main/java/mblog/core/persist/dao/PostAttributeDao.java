package mblog.core.persist.dao;

import mblog.core.persist.entity.PostAttribute;
import mtons.modules.persist.BaseRepository;

/**
 * Created by langhsu on 2017/9/27.
 */
public interface PostAttributeDao extends BaseRepository<PostAttribute> {
    void submit(PostAttribute attr);
}
