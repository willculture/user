package org.wc.user.authority;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.wc.user.BUser;
import org.wc.user.role.BUserRole;

/**
 * 用户权限
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "authority")
public class BAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long authid;// 权限id

  private String name;// 权限名称

  private String aymethod;// 权限方法

  private String url;// 权限方法对应的链接

  private String icon;// 权限方法对应的图标

  @Type(type = "text")
  private String aydesc;// 权限说明

  private boolean ayload;// 是否加载使用

  private Date ctime;// 创建权限时间

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private BAuthority parent; // 父类
  @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
  @OrderBy("ctime desc")
  private Set<BAuthority> child = new LinkedHashSet<BAuthority>();// 子类

  @ManyToMany
  @JoinTable(name = "authority_role", joinColumns = @JoinColumn(name = "authid"),
      inverseJoinColumns = @JoinColumn(name = "roleid"))
  private Set<BUserRole> role;// 角色,对应的是多对多的关系
  @ManyToOne
  @JoinColumn(name = "userid")
  private BUser user;// 哪个用户创建的；（一般是超级管理员）,一个用户可以创建过个权限

  @Transient
  private boolean active;// 是否被点击使用状态,不需要数据库字段

  public boolean getAyload() {
    return ayload;
  }

  public void setAyload(boolean ayload) {
    this.ayload = ayload;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getIcon() {
    return icon;
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public BAuthority getParent() {
    return parent;
  }

  public void setParent(BAuthority parent) {
    this.parent = parent;
  }

  public Set<BAuthority> getChild() {
    return child;
  }

  public void setChild(Set<BAuthority> child) {
    this.child = child;
  }

  public BUser getUser() {
    return user;
  }

  public void setUser(BUser user) {
    this.user = user;
  }

  public Date getCtime() {
    return ctime;
  }

  public void setCtime(Date ctime) {
    this.ctime = ctime;
  }

  public Set<BUserRole> getRole() {
    return role;
  }

  public void setRole(Set<BUserRole> role) {
    this.role = role;
  }

  public Long getAuthid() {
    return authid;
  }

  public void setAuthid(Long authid) {
    this.authid = authid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAymethod() {
    return aymethod;
  }

  public void setAymethod(String aymethod) {
    this.aymethod = aymethod;
  }

  public String getAydesc() {
    return aydesc;
  }

  public void setAydesc(String aydesc) {
    this.aydesc = aydesc;
  }

}
