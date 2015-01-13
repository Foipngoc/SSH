package com.userprivilege.dao.privilege;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.userprivilege.model.privilege.Function;

@SuppressWarnings("all")
@Repository("functionDao")
public class FunctionDao extends BaseDaoDB<Function> {

}
