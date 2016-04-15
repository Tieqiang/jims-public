package com.jims.wx.util.reps;

import org.hibernate.dialect.Oracle10gDialect;

import java.sql.Types;

/**
 * Created by heren on 2014/12/5.
 */
public class Oracle11gDialect extends Oracle10gDialect {
    public Oracle11gDialect(){
        super();
        this.registerColumnType(Types.DOUBLE,"number");
    }
}


