package org.wc.user;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wc.orm.Page;
import org.wc.user.role.BUserRole;

@Service("suser")
@Transactional
public class SUser {
  private Logger logger = Logger.getLogger(getClass());
  @Autowired
  private DUser duser;

  public List<BUser> find(Criterion... criterions) {
    return duser.find(criterions);
  }

  public Page<BUser> find(Page<BUser> page, Criterion... criterions) {
    return duser.findPage(page, criterions);
  }

  public BUser find(Long userid) {
    return duser.findUnique(Restrictions.eq("userid", userid));
  }

  public BUser find(String email) {
    return duser.findUnique(Restrictions.eq("email", email));
  }

  /**
   * 保存或更新
   * 
   * @param role
   */
  public void save$update(BUser user) {
    duser.save(user);
  }

  /**
   * 删除用户
   * 
   * @param user
   */
  public void delete(BUser user) {

    duser.delete(user);
  }

  /**
   * 通过昵称来找用户
   * 
   * @param nickname
   * @return
   */
  public BUser findByNick(String nickname) {
    return duser.findUniqueBy("nickname", nickname);
  }

  /**
   * 或者
   * 
   * @param i
   * @return
   */
  public List<BUser> findRandomMedia(int i, BUserRole prole, BUserRole crole) {
    Criterion person = Restrictions.eq("role", prole);

    Criterion company = Restrictions.eq("role", crole);
    Criterion random = Restrictions.sqlRestriction("1=1 order by random()");;
    return duser.findTop("from BUser a where a.role = ? or a.role = ? order by rand()", i, prole,
        crole);
  }

  /**
   * 今日的数据
   * 
   * @return
   */
  public long findCountToday() {
    // return duser
    // .findUnique("select count(1) from BUser group by ctime having date(ctime) = date(now())");
    return findCount(0);
  }



  public long findCountYest() {
    return findCount(-1);
  }

  public long findCountTotal() {
    return duser.findUnique("select count(1) from BUser");
  }


  public long findCount(int day) {
    Calendar calc = Calendar.getInstance();
    calc.add(Calendar.DAY_OF_MONTH, day);
    return duser.findUnique("select count(1) from BUser where date(ctime) = date(?)",
        calc.getTime());
  }

}
