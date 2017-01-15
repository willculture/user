package org.wc.user.vip;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wc.orm.Page;
import org.wc.user.BUser;

@Service
@Transactional
public class SVip {

	@Autowired
	private DVip dvip;

	public Page<BVip> find(Page<BVip> page, Criterion... criterions) {
		return dvip.findPage(page, criterions);
	}

	public BVip find(Long vipid) {
		return dvip.findUnique(Restrictions.eq("vipid", vipid));
	}

	/**
	 * 保存或更新
	 * 
	 * @param role
	 */
	public void save$update(BVip vip) {
		dvip.save(vip);
	}

	/**
	 * 删除不是真的删掉, 用作记录!
	 * @param vip
	 */
	public void delete(BVip vip) {
		vip.setVipstate(2);
		dvip.save(vip);
	}

}
