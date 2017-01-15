package org.wc.user.role;

import org.springframework.stereotype.Component;
import org.wc.orm.HibernateDao;

@Component
public class DUserRole extends HibernateDao<BUserRole, Long> {

}
