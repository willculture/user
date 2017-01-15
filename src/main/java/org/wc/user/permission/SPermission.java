package org.wc.user.permission;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wc.orm.Page;
import org.wc.user.role.BUserRole;

@Service("spermission")
@Transactional
public class SPermission {

	@Autowired
	private DPermission dao;

	public BPermission queryByAction(String action) {
		return dao.findUnique(Restrictions.eq("action", action));
	}

	public void queryByPage(Page<BPermission> bpage) {

		dao.findPage(bpage);

	}

	public void save(BPermission permission) {

		dao.save(permission);
	}

	public BPermission find(long entityid) {
		return dao.findUnique(Restrictions.eq("entityid", entityid));
	}

	public void delete(BPermission permission) {
		dao.delete(permission);
	}

	public BPermission find(BUserRole role, String classSign) {		 
		BPermission per = dao.findUnique(Restrictions.eq("role", role), Restrictions.eq("action", classSign));
		return per;
	}

}
