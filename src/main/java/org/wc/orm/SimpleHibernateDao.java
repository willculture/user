package org.wc.orm;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.wc.utils.ReflectionUtils;

/**
 * 封装Hibernate原生API的DAO泛型基类.
 * 
 * 可在Service层直接使用, 也可以扩展泛型DAO子类使用, 见两个构造函数的注释. 参考Spring2.5自带的Petlinc例子, 取消了HibernateTemplate,
 * 直接使用Hibernate原生API.
 * 
 * @param <T> DAO操作的对象类型
 * @param <PK> 主键类型
 * 
 * @author Tonway
 */
@SuppressWarnings("unchecked")
public class SimpleHibernateDao<T, PK extends Serializable> {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  protected SessionFactory sessionFactory;

  protected Class<T> entityClass;

  /**
   * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
   * SimpleHibernateDao<User, Long>
   */
  public SimpleHibernateDao() {
    this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
  }

  /**
   * 用于用于省略Dao层, 在Service层直接使用通用SimpleHibernateDao的构造函数. 在构造函数中定义对象类型Class. eg.
   * SimpleHibernateDao<User, Long> userDao = new SimpleHibernateDao<User, Long>(sessionFactory,
   * User.class);
   */
  public SimpleHibernateDao(final SessionFactory sessionFactory, final Class<T> entityClass) {
    this.sessionFactory = sessionFactory;
    this.entityClass = entityClass;
  }

  /**
   * 取得sessionFactory.
   */
  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  /**
   * 采用@Autowired按类型注入SessionFactory, 当有多个SesionFactory的时候在子类重载本函数.
   */
  @Autowired
  public void setSessionFactory(final SessionFactory sessionFactory) {
    logger.info("设置sessionFactory...");
    this.sessionFactory = sessionFactory;
  }

  /**
   * 取得当前Session.
   */
  public Session getSession() {
    // return sessionFactory.openSession();
    return sessionFactory.getCurrentSession();
  }

  /**
   * 保存新增或修改的对象.
   */
  public void save(final T entity) {
    Assert.notNull(entity, "entity不能为空");
    // getSession().save(entity);
    getSession().saveOrUpdate(entity);
    
    logger.debug("save entity: {}", entity);
  }

  public void update(final T entity) {
    Assert.notNull(entity, "entity不能为空");
    // getSession().save(entity);
    getSession().saveOrUpdate(entity);
    logger.debug("update entity: {}", entity);
  }

  /**
   * 删除对象.
   * 
   * @param entity 对象必须是session中的对象或含id属性的transient对象.
   */
  public void delete(final T entity) {
    Assert.notNull(entity, "entity不能为空");
    getSession().delete(entity);
    logger.debug("delete entity: {}", entity);
  }

  /**
   * 按id删除对象.
   */
  public void delete(final PK id) {
    Assert.notNull(id, "id不能为空");
    delete(get(id));
    logger.debug("delete entity {},id is {}", entityClass.getSimpleName(), id);
  }

  /**
   * 按id获取对象.
   */
  public T get(final PK id) {
    logger.info("开始进入Dao,通过ID进行查询：" + id);
    Assert.notNull(id, "id不能为空");
    return (T) getSession().load(entityClass, id);
  }

  /**
   * 通过ID查询
   * 
   * @param id
   * @return
   */
  public T load(final PK id) {
    logger.info("开始进入Dao,通过ID进行查询：" + id);
    Assert.notNull(id, "id不能为空");
    String name = getIdName();
    Assert.notNull(name, "不能找到主键字段ID");
    return findUnique(Restrictions.eq(name, id));
  }

  /**
   * 按id列表获取对象列表.
   */
  public List<T> get(final Collection<PK> ids) {
    return find(Restrictions.in(getIdName(), ids));
  }

  /**
   * 获取全部对象.
   */
  public List<T> getAll() {
    return find();
  }

  /**
   * 获取全部对象, 支持按属性行序.
   */
  public List<T> getAll(String orderByProperty, boolean isAsc) {
    Criteria c = createCriteria();
    if (isAsc) {
      c.addOrder(Order.asc(orderByProperty));
    } else {
      c.addOrder(Order.desc(orderByProperty));
    }
    return c.list();
  }

  /**
   * 按属性查找对象列表, 匹配方式为相等.
   */
  public List<T> findBy(final String propertyName, final Object value) {
    Assert.hasText(propertyName, "propertyName不能为空");
    Criterion criterion = Restrictions.eq(propertyName, value);
    return find(criterion);
  }

  /**
   * 按属性查找唯一对象, 匹配方式为相等.
   */
  public T findUniqueBy(final String propertyName, final Object value) {
    Assert.hasText(propertyName, "propertyName不能为空");
    Criterion criterion = Restrictions.eq(propertyName, value);
    return (T) createCriteria(criterion).uniqueResult();
  }

  /**
   * 按HQL查询对象列表.
   * 
   * @param values 数量可变的参数,按顺序绑定.
   */
  public <X> List<X> find(final String hql, final Object... values) {
    return createQuery(hql, values).list();
  }

  public <X> List<X> findTop(final String hql, int top, final Object... values) {
    return createQuery(hql, top, values).list();
  }

  /**
   * 按HQL查询对象列表.
   * 
   * @param values 命名参数,按名称绑定.
   */
  public <X> List<X> find(final String hql, final Map<String, ?> values) {
    return createQuery(hql, values).list();
  }

  /**
   * 按HQL查询唯一对象.
   * 
   * @param values 数量可变的参数,按顺序绑定.
   */
  public <X> X findUnique(final String hql, final Object... values) {
    return (X) createQuery(hql, values).uniqueResult();
  }

  /**
   * 按HQL查询唯一对象.
   * 
   * @param values 命名参数,按名称绑定.
   */
  public <X> X findUnique(final String hql, final Map<String, ?> values) {
    return (X) createQuery(hql, values).uniqueResult();
  }

  /**
   * 执行HQL进行批量修改/删除操作.
   * 
   * @param values 数量可变的参数,按顺序绑定.
   * @return 更新记录数.
   */
  public int batchExecute(final String hql, final Object... values) {
    return createQuery(hql, values).executeUpdate();
  }

  /**
   * 执行HQL进行批量修改/删除操作.
   * 
   * @param values 命名参数,按名称绑定.
   * @return 更新记录数.
   */
  public int batchExecute(final String hql, final Map<String, ?> values) {
    return createQuery(hql, values).executeUpdate();
  }

  /**
   * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
   * 
   * @param values 数量可变的参数,按顺序绑定.
   */
  public Query createQuery(final String queryString, final Object... values) {
    Assert.hasText(queryString, "queryString不能为空");
    Query query = getSession().createQuery(queryString);
    if (values != null) {
      for (int i = 0; i < values.length; i++) {
        query.setParameter(i, values[i]);
      }
    }
    return query;
  }

  public Query createQuery(final String queryString, int top, final Object... values) {
    Assert.hasText(queryString, "queryString不能为空");
    Query query = getSession().createQuery(queryString);
    if (values != null) {
      for (int i = 0; i < values.length; i++) {
        query.setParameter(i, values[i]);
      }
    }
    query.setMaxResults(top);
    query.setFirstResult(0);
    return query;
  }

  /**
   * 根据查询HQL与参数列表创建Query对象. 与find()函数可进行更加灵活的操作.
   * 
   * @param values 命名参数,按名称绑定.
   */
  public Query createQuery(final String queryString, final Map<String, ?> values) {
    Assert.hasText(queryString, "queryString不能为空");
    Query query = getSession().createQuery(queryString);
    if (values != null) {
      query.setProperties(values);
    }
    return query;
  }

  /**
   * 按Criteria查询对象列表.
   * 
   * @param criterions 数量可变的Criterion.
   */
  public List<T> find(final Criterion... criterions) {
    return createCriteria(criterions).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
  }

  /**
   *  获取排序后的数据列表.
   *  
   * @param orders 字符串数组： 例如 field-asc 这种格式得字符串
   * @param criterions
   * @return
   */
  public List<T> find (String[] orders, final Criterion...criterions) {
      Criteria c = createCriteria(criterions);
      for(String order : orders) {
          String[] item = order.split("-");
          if (item[1].equalsIgnoreCase("asc")){
            c.addOrder(Order.asc(item[0]));
          } else if (item[1].equalsIgnoreCase("desc")) {
            c.addOrder(Order.desc(item[0]));
          } else {
            c.addOrder(Order.desc(item[0]));
          }
      }
      return c.list();
  }
  /**
   * 按Criteria查询唯一对象.
   * 
   * @param criterions 数量可变的Criterion.
   */
  public T findUnique(final Criterion... criterions) {
    return (T) createCriteria(criterions).uniqueResult();
  }

  /**
   * 根据Criterion条件创建Criteria. 与find()函数可进行更加灵活的操作.
   * 
   * @param criterions 数量可变的Criterion.
   */
  public Criteria createCriteria(final Criterion... criterions) {
    Criteria criteria = getSession().createCriteria(entityClass);
    for (Criterion c : criterions) {
      criteria.add(c);
    } 
    return criteria;
  }

  /**
   * 初始化对象. 使用load()方法得到的仅是对象Proxy, 在传到View层前需要进行初始化. 如果传入entity,
   * 则只初始化entity的直接属性,但不会初始化延迟加载的关联集合和属性. 如需初始化关联属性,需执行:
   * Hibernate.initialize(user.getRoles())，初始化User的直接属性和关联集合.
   * Hibernate.initialize(user.getDescription())， 初始化User的直接属性和延迟加载的Description属性.
   */
  public void initProxyObject(Object proxy) {
    Hibernate.initialize(proxy);
  }

  /**
   * Flush当前Session.
   */
  public void flush() {
    getSession().flush();
  }

  /**
   * 为Query添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
   */
  public Query distinct(Query query) {
    query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    return query;
  }

  /**
   * 为Criteria添加distinct transformer. 预加载关联对象的HQL会引起主对象重复, 需要进行distinct处理.
   */
  public Criteria distinct(Criteria criteria) {
    criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
    return criteria;
  }

  /**
   * 取得对象的主键名.
   */
  public String getIdName() {
    ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
    return meta.getIdentifierPropertyName();
  }

  /**
   * 判断对象的属性值在数据库内是否唯一.
   * 
   * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
   */
  public boolean isPropertyUnique(final String propertyName, final Object newValue,
      final Object oldValue) {
    if (newValue == null || newValue.equals(oldValue)) {
      return true;
    }
    Object object = findUniqueBy(propertyName, newValue);
    return (object == null);
  }
}
