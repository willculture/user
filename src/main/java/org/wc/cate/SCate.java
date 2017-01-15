package org.wc.cate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wc.orm.Page;

@Service
@Transactional
public class SCate {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	private DCate dCate;

	/**
	 * 保存/更新
	 * 
	 * @param term
	 */
	public void saveCate(BCate cate) {
		dCate.save(cate);
	}

	/**
	 * 分页获取
	 * 
	 * @param page
	 * @param type
	 * @return
	 */
	public Page<BCate> getCate(Page<BCate> page, String type) {
		return dCate.findPage(page, "from BCate where ctype=? and parent is null", type);
	}

	/**
	 * 获取全部
	 * 
	 * @param type
	 * @return
	 */
	public List<BCate> getCate(String type) {
		return dCate.find("select cate from BCate cate where cate.ctype=? and cate.parent is null", type);
	}

	/**
	 * 查询页面类别
	 * 
	 * @return
	 */
	public Page<BCate> getPageCate(Page<BCate> page) {
		return getCate(page, "page");
	}

	/**
	 * 查询文章类别
	 * 
	 * @return
	 */
	public Page<BCate> getInforCate(Page<BCate> page) {
		return getCate(page, "post");
	}

	/**
	 * 查询所有文章类别
	 * 
	 * @return
	 */
	public List<BCate> getAllInforCate() {
		return getCate("post");
	}

	/**
	 * 通过父类找子类
	 * 
	 * @param cateid
	 * @return
	 */
	public List<BCate> findChild(Long cateid) {
		Criterion crit1 = Restrictions.eq("parent", findCateById(cateid));
		List<BCate> list = dCate.find(crit1);
		return list;
	}

	/**
	 * 查询连接类别
	 * 
	 * @return
	 */
	public Page<BCate> getLinkCate(Page<BCate> page) {
		return getCate(page, "link");
	}

	/**
	 * 获取所有的链接类别
	 * 
	 * @return
	 */
	public List<BCate> getLinkCate() {
		return getCate("link");
	}

	/**
	 * 删除类别
	 * 
	 * @param cateid
	 */
	public void deleteCate(Long cateid) {
		dCate.delete(cateid);
	}

	/**
	 * 获取单个分类
	 * 
	 * @param cateid
	 * @return
	 */
	public BCate findCateById(Long cateid) {
		return dCate.findUnique(Restrictions.eq("cateid", cateid));
	}

}
