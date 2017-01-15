package org.wc.user.permission;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.wc.user.role.BUserRole;

/**
 * 用户角色对应的权限
 * <p>
 * 权限ID,创建时间,权限名称,权限说明,权限请求
 * </p>
 * 
 * @author Tonway
 *
 */
@Entity
@Table(name = "permission")
public class BPermission {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long entityid;

	private String psname;

	private String psdesc;

	private String action;

	private Date ctime;

	@ManyToOne
	@JoinColumn(name = "roleid")
	private BUserRole role;

	public Long getEntityid() {
		return entityid;
	}

	public void setEntityid(Long entityid) {
		this.entityid = entityid;
	}

	public String getPsname() {
		return psname;
	}

	public void setPsname(String psname) {
		this.psname = psname;
	}

	public String getPsdesc() {
		return psdesc;
	}

	public void setPsdesc(String psdesc) {
		this.psdesc = psdesc;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public BUserRole getRole() {
		return role;
	}

	public void setRole(BUserRole role) {
		this.role = role;
	}

}
