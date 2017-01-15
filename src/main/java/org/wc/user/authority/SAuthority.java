package org.wc.user.authority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wc.orm.Page;
import org.wc.user.role.BUserRole;
import org.wc.user.role.DUserRole;

@Service
@Transactional
public class SAuthority {

	@Autowired
	DAuthority dauth;

	/**
	 * 获取所有的权限
	 * 
	 * @return 权限列表
	 */
	public List<BAuthority> find(Criterion... criterions) {
		return dauth.find(criterions);
	}
	
	public List<BAuthority> find(){
		return dauth.find("from BAuthority");
	}

	public Page<BAuthority> find(Page<BAuthority> page, Criterion... criterions) {
		return dauth.findPage(page, criterions);
	}
	
	public Page<BAuthority> find(Page<BAuthority> page,String hql){
		return dauth.findPage(page, hql);
	}

	public BAuthority find(Long authid) {
		return dauth.findUnique(Restrictions.eq("authid", authid));
	}

	public BAuthority find(String aymethod) {
		return dauth.findUnique(Restrictions.eq("aymethod", aymethod));
	}
	
	public List<BAuthority> findMenu(BUserRole role){
		List<BAuthority> list = new ArrayList<BAuthority>();
		if(role.getRoleid()==1){
		
		     list = dauth.find("select auth from BAuthority auth where auth.parent is null and auth.ayload = true order by ctime desc");
		}else{
			list = dauth.find("select auth from BAuthority auth left join auth.role r where r.roleid="+role.getRoleid()+" and auth.parent is null and auth.ayload = true order by auth.ctime desc");
		}
		return list; 
		
	}

	/**
	 * 保存或更新
	 * 
	 * @param role
	 */
	public void save$update(BAuthority auth) {
		dauth.save(auth);
	}

	/**
	 * 删除权限
	 * 
	 * @param auth
	 */
	public void delete(BAuthority auth) {
		dauth.delete(auth);
	}

}
