package org.wc.user.authority;

import org.springframework.stereotype.Component;
import org.wc.orm.HibernateDao;

@Component
public class DAuthority extends HibernateDao<BAuthority, Long> {

}
