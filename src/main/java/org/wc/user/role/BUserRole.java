package org.wc.user.role;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * 用户角色
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name="user_role")
public class BUserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long roleid;// id

	@Column(unique = true)
	private String name;// 角色名称
	@Type(type = "text")
	private String roledesc;// 角色说明
	@Column(unique = true)
	private String rolealias;// 角色别名
	private String rolebadge;// 徽章
	private int rank;// 会员等级

	private Date ctime;// 创建时间

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoledesc() {
		return roledesc;
	}

	public void setRoledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getRolealias() {
		return rolealias;
	}

	public void setRolealias(String rolealias) {
		this.rolealias = rolealias;
	}

	public String getRolebadge() {
		return rolebadge;
	}

	public void setRolebadge(String rolebadge) {
		this.rolebadge = rolebadge;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
