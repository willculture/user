package org.wc.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.wc.area.BArea;
import org.wc.user.role.BUserRole;

@Entity
@Table(name = "user")
public class BUser implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long userid;
  private String avatar;// 头像
  @Column(unique = true)
  private String username;// 用户名
  @Column(unique = true)
  private String nickname;// 昵称
  private String password;// 密码
  
  @Column(unique = true)
  private String email;// email
  private int sex;// 性别
  private long phone; // 手机号码
  private short isemail; // 是否激活邮箱0,默认未激活， 1激活
  private short isphone;// 是否绑定手机0 未激活（默认）, 1激活
  @Type(type = "text")
  private String intro;//个人介绍

  @ManyToOne
  @JoinColumn(name = "nation_id")
  private BArea nation;// 国家
  @ManyToOne
  @JoinColumn(name = "province_id")
  private BArea province;// 省份
  @ManyToOne
  @JoinColumn(name = "city_id")
  private BArea city;// 城市
  @ManyToOne
  @JoinColumn(name = "county_id")
  private BArea county;// 县(区域)
  @ManyToOne
  @JoinColumn(name = "street_id")
  private BArea street;// 街道(路)

  private Date ctime;// 注册时间

  @ManyToOne
  @JoinColumn(name = "role_id")
  private BUserRole role; // 用户角色

  public Long getUserid() {
    return userid;
  }

  public void setUserid(Long userid) {
    this.userid = userid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public Date getCtime() {
    return ctime;
  }

  public void setCtime(Date ctime) {
    this.ctime = ctime;
  }

  public long getPhone() {
    return phone;
  }

  public void setPhone(long phone) {
    this.phone = phone;
  }

  public BArea getNation() {
    return nation;
  }

  public void setNation(BArea nation) {
    this.nation = nation;
  }

  public BArea getProvince() {
    return province;
  }

  public void setProvince(BArea province) {
    this.province = province;
  }

  public BArea getCity() {
    return city;
  }

  public void setCity(BArea city) {
    this.city = city;
  }

  public BArea getCounty() {
    return county;
  }

  public void setCounty(BArea county) {
    this.county = county;
  }

  public BArea getStreet() {
    return street;
  }

  public void setStreet(BArea street) {
    this.street = street;
  }

  public BUserRole getRole() {
    return role;
  }

  public void setRole(BUserRole role) {
    this.role = role;
  }

  public short getIsemail() {
    return isemail;
  }

  public void setIsemail(short isemail) {
    this.isemail = isemail;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public short getIsphone() {
    return isphone;
  }

  public void setIsphone(short isphone) {
    this.isphone = isphone;
  }

  public String getIntro() {
    return intro;
  }

  public void setIntro(String intro) {
    this.intro = intro;
  }

}
