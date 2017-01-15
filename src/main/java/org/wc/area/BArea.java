package org.wc.area;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * 区域功能中的实体类. 这里主要用于国家, 省份, 城市, 区域(县), 镇, 村
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "area")
public class BArea {

  @Retention(RetentionPolicy.RUNTIME)
  public @interface WithOutChild {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long areaid;// id
  private String areaname; // 名称
  private String areacalias; // 别名
  private String areaimg; // 地区对应的旗子
  private String areacode; // 区号
  private String areapost;// 邮编
  private String areadesc;// 说明

  private Date ctime; // 创建时间

  // 继承
  @ManyToOne
  @JoinColumn(name = "parent_id")
  private BArea parent;

  @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
  @OrderBy("ctime")
  private List<BArea> childs = new ArrayList<BArea>();

  public String getAreaname() {
    return areaname;
  }

  public void setAreaname(String areaname) {
    this.areaname = areaname;
  }

  public String getAreacalias() {
    return areacalias;
  }

  public void setAreacalias(String areacalias) {
    this.areacalias = areacalias;
  }

  public String getAreaimg() {
    return areaimg;
  }

  public void setAreaimg(String areaimg) {
    this.areaimg = areaimg;
  }

  public String getAreacode() {
    return areacode;
  }

  public void setAreacode(String areacode) {
    this.areacode = areacode;
  }

  public String getAreapost() {
    return areapost;
  }

  public void setAreapost(String areapost) {
    this.areapost = areapost;
  }

  public String getAreadesc() {
    return areadesc;
  }

  public void setAreadesc(String areadesc) {
    this.areadesc = areadesc;
  }

  public Long getAreaid() {
    return areaid;
  }

  public void setAreaid(Long areaid) {
    this.areaid = areaid;
  }

  public Date getCtime() {
    return ctime;
  }

  public void setCtime(Date ctime) {
    this.ctime = ctime;
  }

  public BArea getParent() {
    return parent;
  }

  public void setParent(BArea parent) {
    this.parent = parent;
  }

  @WithOutChild
  public List<BArea> getChilds() {
    return childs;
  }

  public void setChilds(List<BArea> childs) {
    this.childs = childs;
  }

}
