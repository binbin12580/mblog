package mblog.core.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

import mblog.core.persist.dao.AuthMenuDao;
import mblog.core.persist.entity.AuthMenuPO;
import mtons.modules.annotation.Repository;
import mtons.modules.persist.impl.BaseRepositoryImpl;
import org.hibernate.criterion.Order;

@Repository(entity = AuthMenuPO.class)
public class AuthMenuDaoImpl extends BaseRepositoryImpl<AuthMenuPO> implements AuthMenuDao{
	private static final long serialVersionUID = 1L;

	@Override
	@SuppressWarnings("unchecked")
	public List<AuthMenuPO> findByParentId(Long parentId) {
		Query query = createQuery("from AuthMenuPO where parent_id = ? order by sort");
		query.setLong(0, parentId);
		return query.list();
	}

	@Override
	public List<AuthMenuPO> findAll() {
		Criteria criteria = createCriteria();
		criteria.addOrder(Order.asc("id"));
		criteria.addOrder(Order.asc("parentId"));
		return criteria.list();
	}

}
