package org.wc.user;

import org.springframework.stereotype.Component;
import org.wc.orm.HibernateDao;

@Component
public class DUser extends HibernateDao<BUser,Long>{

}
