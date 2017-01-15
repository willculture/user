package org.wc.user.role;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wc.orm.Page;

@Service
@Transactional
public class SUserRole {

	@Autowired
	private DUserRole dur;

	public List<BUserRole> find(Criterion... criterions) {
		return dur.find(criterions);
	}

	public Page<BUserRole> find(Page<BUserRole> page, Criterion... criterions) {
		return dur.findPage(page, criterions);
	}
	
	public BUserRole find(Long roleid){		
		return dur.findUnique(Restrictions.eq("roleid", roleid));
	}

	/**
	 * 保存或更新
	 * 
	 * @param role
	 */
	public void save$update(BUserRole role) {
		dur.save(role);
	}
	
	/**
	 * 删除角色
	 * @param role
	 */
	public void delete(BUserRole role){
		dur.delete(role);
	}

  public BUserRole findByAlias(String alias) {     
    return dur.findUnique(Restrictions.eq("rolealias", alias));
  }

	 
	

}
