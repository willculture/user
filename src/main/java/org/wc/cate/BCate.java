package org.wc.cate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 类别
 * 
 * @author Administrator
 * 
 */
@Entity
public class BCate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long cateid;
	private String cimg; // 图片
	private String cname; // 名称
	private String calias;// 别名
	private String ctype;// 类别类型 // post(文章) | link(超链接)
	@ManyToOne(cascade=CascadeType.REFRESH)
	private BCate parent; // 父类
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private Set<BCate> childs = new HashSet<BCate>();// 子类
	private String ctemplate; // 模板
	private Date ctime; // 插入时间
	private String ckey;// 关键词
	private String cdesc;// 说明
	private int csort;// 排序

	public Long getCateid() {
		return cateid;
	}

	public void setCateid(Long cateid) {
		this.cateid = cateid;
	}

	public String getCimg() {
		return cimg;
	}

	public void setCimg(String cimg) {
		this.cimg = cimg;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCalias() {
		return calias;
	}

	public void setCalias(String calias) {
		this.calias = calias;
	}

	public BCate getParent() {
		return parent;
	}

	public void setParent(BCate parent) {
		this.parent = parent;
	}

	public String getCtemplate() {
		return ctemplate;
	}

	public void setCtemplate(String ctemplate) {
		this.ctemplate = ctemplate;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getCkey() {
		return ckey;
	}

	public void setCkey(String ckey) {
		this.ckey = ckey;
	}

	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	public int getCsort() {
		return csort;
	}

	public void setCsort(int csort) {
		this.csort = csort;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public Set<BCate> getChilds() {
		return childs;
	}

	public void setChilds(Set<BCate> childs) {
		this.childs = childs;
	}

}
