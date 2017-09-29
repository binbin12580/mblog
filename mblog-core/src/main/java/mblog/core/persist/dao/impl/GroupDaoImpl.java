/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package mblog.core.persist.dao.impl;

import java.util.List;

import mblog.core.persist.dao.GroupDao;
import mblog.core.persist.entity.GroupPO;
import mtons.modules.annotation.Repository;
import mtons.modules.lang.Const;
import mtons.modules.persist.impl.BaseRepositoryImpl;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author langhsu
 *
 */
@Repository(entity = GroupPO.class)
public class GroupDaoImpl extends BaseRepositoryImpl<GroupPO> implements GroupDao {
	private static final long serialVersionUID = -3510165157507261158L;

	@Override
	public List<GroupPO> findAll(int status) {
		Criteria criteria = createCriteria();

		if (status > Const.IGNORE) {
			criteria.add(Restrictions.eq("status", status));
		}
		return criteria.list();
	}

	@Override
	public GroupPO getByKey(String key) {
		return findUniqueBy("key", key);
	}

}
