package org.wc.user.vip;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.wc.user.BUser;
import org.wc.user.role.BUserRole;

/**
 * 用户的角色是通过购买会员订单来完成的, 会员是有时间限制的, 每一次的订单都是一次会员的有效的时间. <br/>
 * 有效时间过期后, 将失效, 得重新购买这个订单
 * 
 * @author Will
 *
 */
@Entity
@Table(name = "vip")
public class BVip {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long vipid;

  private String orderid; // 订单ID

  @ManyToOne
  @JoinColumn(name = "roleid")
  private BUserRole role; // 会员角色

  private int limitday; // 会员期限

  private Date appytime; // 是否通过时间开始计算

  private int vipstate;// 购买成功或者失败 0 默认状态, 1 为成功, 2为删除

  private Date ctime; // 创建时间

  @ManyToOne
  @JoinColumn(name = "userid")
  private BUser user;

  public Long getVipid() {
    return vipid;
  }

  public void setVipid(Long vipid) {
    this.vipid = vipid;
  }

  public String getOrderid() {
    return orderid;
  }

  public void setOrderid(String orderid) {
    this.orderid = orderid;
  }

  public BUserRole getRole() {
    return role;
  }

  public void setRole(BUserRole role) {
    this.role = role;
  }

  public int getLimitday() {
    return limitday;
  }

  public void setLimitday(int limitday) {
    this.limitday = limitday;
  }

  public Date getAppytime() {
    return appytime;
  }

  public void setAppytime(Date appytime) {
    this.appytime = appytime;
  }

  public int getVipstate() {
    return vipstate;
  }

  public void setVipstate(int vipstate) {
    this.vipstate = vipstate;
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

  /**
   * 生成order
   * 
   * @return
   */
  public String generateOrderid() {
    String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(8);
    uuid = uuid + new Date().getTime();
    return uuid;
  }

}
