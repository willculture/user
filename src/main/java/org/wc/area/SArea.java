package org.wc.area;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wc.orm.Page;

@Service
@Transactional
public class SArea {

	@Autowired
	private DArea darea;

	public Page<BArea> find(Page<BArea> page) {
		return darea.findPage(page);
	}

	public BArea find(Long areaid) {
		return darea.findUnique(Restrictions.eq("areaid", areaid));
	}

	public void save$update(BArea area) {
		darea.save(area);
	}

	public void delete(BArea area) {
		darea.delete(area);
	}

	public List<BArea> findAll() {		
		return darea.find(); 
	}

	/**
	 * 查找除了本身自己的类别, 用于修改类别的时候, 调用父类
	 * @param area
	 * @return
	 */
	public List<BArea>  findAll(BArea area) {
		return darea.find(Restrictions.not(Restrictions.eq("areaid", area.getAreaid())));
	}

	/**
	 * 查找顶级的地区(国家)
	 * @return
	 */
	public List<BArea>  findTop() {
		return darea.find(Restrictions.isNull("parent"));
	}

}
