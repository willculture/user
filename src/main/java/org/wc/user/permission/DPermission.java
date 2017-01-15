package org.wc.user.permission;

import org.springframework.stereotype.Repository;
import org.wc.orm.HibernateDao;

@Repository("dpermission")
public class DPermission extends HibernateDao<BPermission, Long>{

}
