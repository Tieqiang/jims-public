package com.jims.wx;

import com.google.common.base.Optional;
import com.jims.wx.entity.ClinicForRegist;
import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;

import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Throwables.propagate;

/**
 * Created by heren on 2014/10/13.
 */
public class BaseFacade {

    @Inject
    protected EntityManager entityManager;
    protected BaseFacade() {
    }

    protected <T> T strictFindByPrimaryKey(Class<T> entityClass, Object key, String... exceptionMsg) throws Exception {
        String msg = exceptionMsg != null && exceptionMsg.length > 0 ? exceptionMsg[0] : "指定的资源无法找到";
        if (key == null) {
            throw new Exception(String.format(msg + ": null"));
        }
        T t = entityManager.find(entityClass, key);
        if (t == null) {
            throw new Exception(String.format(msg + ": [%s]", key.toString()));
        }
        return t;
    }

    /**
     * 查找实体
     *
     * @param <T>
     * @param entityClass 实体类
     * @param pk          主键
     * @return 根据指定主键返回实体
     */
    public <T> T get(Class<T> entityClass, Object pk) {
        return entityManager.find(entityClass, pk);
    }


    /**
     * 持久化对象，进行数据插入的时候尽量使用次方方法
     * @param entity
     * @param <T>
     * @return
     */
    public <T> T persist(T entity) {
        entityManager.persist(entity);
        return entity;
    }


    /**
     * 修改实体 在进行更新时使用此方法
     *
     * @param entity
     * @return
     */
    public <T> T merge(T entity) {
        return  entityManager.merge(entity);
    }

    /**
     * 删除实体
     *
     * @param entity
     * @param <T>
     * @return
     */
    public <T> void remove(T entity) {
        this.getEntityManager().remove(entity);
    }

    /**
     * 删除一组实体
     * @param list
     */
    public void remove(List<Object> list) {
        for (Object obj : list) {
            this.getEntityManager().remove(obj);
        }
    }

    /**
     * 批量删除
     *
     * @param entityClass
     * @param ids
     */
    public void remove(Class<?> entityClass, List<Long> ids) {
        for (Long pk : ids) {
            this.getEntityManager().remove(entityManager.merge(entityManager.getReference(entityClass, pk)));
        }
    }

    /**
     * 批量删除
     *
     * @param entityClass
     * @param ids
     */
    public void removeByStringIds(Class<?> entityClass, List<String> ids) {
        for (String pk : ids) {
            this.getEntityManager().remove(entityManager.find(entityClass, pk));
        }
    }


    /**
     * 此方法 获取一个范围内的T对象
     * @param entity
     * @param range
     * @param <T>
     * @return
     */
    public <T> List<T> findRange(T entity, int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entity.getClass()));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     *
     * @param entity
     * @param <T>
     * @return
     */
    public <T> int count(T entity) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entity.getClass());
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public <T> List<T> findAll(Class<T> entity) {
        TypedQuery query = entityManager.createQuery("FROM " + entity.getCanonicalName(), entity);
        return (List<T>) query.getResultList();
    }

    public Query createNativeQuery(String sqlString){
        return  entityManager.createNativeQuery(sqlString);
    }

    /**
     * 执行查询方法
     *
     * @param <T>
     * @param entityClass 实体类
     * @param whereJpql   指定查询返回的第一条记录
     * @param orderBy     用于排序
     * @param args        作为JPQL 查询字符的参数的值
     * @return 返回查询得到的实体List
     */
    public <T> List<T> getResultList(Class<T> entityClass, String whereJpql,
                                     LinkedHashMap<String, String> orderBy, Object... args) {
        //获取实体名称
        String entityName = entityClass.getSimpleName();
        //创建查询
        TypedQuery query = this.getEntityManager().createQuery(" from " + entityName
                + " as o " + whereJpql + this.buildOrderby(orderBy), entityClass);

        //为查询字符串中的参数设置值
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }

        //返回结果集
        return (List<T>) query.getResultList();
    }

    /**
     * 执行查询，并进行分页
     *
     * @param <T>
     * @param entityClass 实体类
     * @param whereJpql   指定查询返回的第一条记录
     * @param firstResult 启始记录数
     * @param maxResult   显示的最大记录数
     * @param orderBy     用于排序
     * @param args        作为JPQL 查询字符的参数的值
     * @return 返回查询得到的实体List
     */
    public <T> List<T> getResultList(Class<T> entityClass, String whereJpql,
                                     int firstResult, int maxResult,
                                     LinkedHashMap<String, String> orderBy, Object... args) {
        //获取实体名称
        String entityName = entityClass.getSimpleName();
        //创建查询
        TypedQuery query = this.getEntityManager().createQuery("select o from " + entityName
                + " as o " + whereJpql + this.buildOrderby(orderBy), entityClass);

        //为查询字符串中的参数设置值
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        //对查询的结果集进行分页
        query.setMaxResults(maxResult).setFirstResult(maxResult);

        //返回结果集
        return (List<T>) query.getResultList();
    }


    /**
     * 构建排序子句
     *
     * @param orderby 排序条件
     * @return
     */
    private static String buildOrderby(LinkedHashMap<String, String> orderby) {
        StringBuffer out = new StringBuffer();

        if (orderby != null && orderby.size() > 0) {
            out.append(" order by ");
            for (String key : orderby.keySet()) {
                out.append(" o." + key + " " + orderby.get(key) + " ,");
            }
            out.deleteCharAt(out.length() - 1);
        }
        return out.toString();
    }

    /**
     * 组合排序拼接orderBy
     * @param orderBySort
     * @param jpqlQuery
     * @param tableAlias
     */
    public void buildOrderBy(String orderBySort, StringBuilder jpqlQuery, String tableAlias) {
        if (orderBySort != null && !orderBySort.equals("")) {
            String orderBy = " order by ";
            String[] feilds = orderBySort.split(",");
            int count = feilds.length;
            for (String str : feilds) {
                if (count > 1) {
                    orderBy += tableAlias + "." + str + ",";
                } else {
                    orderBy += tableAlias + "." + str;
                }
                count--;
            }
            jpqlQuery.append(orderBy);
        }
    }

    public JpqlQueryBuilder jpqlQueryBuilder(String jpql, Object param, String judge) {
        return jpqlQueryBuilder(new StringBuilder(jpql), param, judge, new ArrayList<>());
    }

    public JpqlQueryBuilder jpqlQueryBuilder(StringBuilder jpql, Object param, String judge) {
        return jpqlQueryBuilder(jpql, param, judge, new ArrayList<>());
    }

    public JpqlQueryBuilder jpqlQueryBuilder(StringBuilder jpql, Object param, String judge, boolean addWhereOrNot) {
        return jpqlQueryBuilder(jpql, param, judge, new ArrayList<>(), addWhereOrNot);
    }

    public JpqlQueryBuilder jpqlQueryBuilder(StringBuilder jpql, Object param, String judge, ArrayList<Object> parameters) {
        return jpqlQueryBuilder(jpql, param, judge, parameters, true);
    }

    public JpqlQueryBuilder jpqlQueryBuilder(StringBuilder jpql, Object param, String judge, ArrayList<Object> parameters, boolean addWhereOrNot) {
        return new JpqlQueryBuilder(jpql, param, judge, parameters, addWhereOrNot);
    }



    public static class JPQLBuilder {
        private StringBuilder sb = new StringBuilder();
        private List<Object> params = new ArrayList<>();
        private JPQLBuilder () {}
        public static JPQLBuilder getInstance() {
            return new JPQLBuilder();
        }
        public StringBuilder get$QL () {
            return sb;
        }
        public List<Object> getParams () {
            return params;
        }
        public JPQLBuilder append (String $ql) {
            sb.append(" " + $ql + " ");
            return this;
        }
        public JPQLBuilder select (String selectJPQL) {
            sb.append(" select " + selectJPQL).append(" ");
            return this;
        }
        public JPQLBuilder select () {
            sb.append(" select ");
            return this;
        }
        public JPQLBuilder from (String fromJPQL) {
            sb.append(" from " + fromJPQL).append(" ");
            return this;
        }
        public JPQLBuilder from () {
            sb.append(" from ");
            return this;
        }
        public JPQLBuilder where () {
            sb.append(" where 1 = 1 ");
            return this;
        }
        public JPQLBuilder where (String whereJPQL) {
            sb.append(" where " + whereJPQL).append(" ");
            return this;
        }
        public JPQLBuilder add (String addJpQL) {
            sb.append(" and ").append(addJpQL).append(" ");
            return this;
        }
        public JPQLBuilder addIsNull (String field) {
            sb.append(" and " + field + " is null ");
            return this;
        }
        public JPQLBuilder addIsNotNull (String field) {
            sb.append(" and " + field + " is not null ");
            return this;
        }
        public JPQLBuilder addEqualTo (String field, Object param) {
            if (param != null) {
                sb.append(" and " + field + " = ? ");
                params.add(param);
            }
            return this;
        }
        public JPQLBuilder addNotEqualTo (String field, Object param) {
            if (param != null) {
                sb.append(" and " + field + " <> ? ");
                params.add(param);
            }
            return this;
        }
        public JPQLBuilder addGreaterThan (String field, Object param) {
            if (param != null) {
                sb.append(" and " + field + " > ? ");
                params.add(param);
            }
            return this;
        }
        public JPQLBuilder addGreaterThanOrEqualTo (String field, Object param) {
            if (param != null) {
                sb.append(" and " + field + " >= ? ");
                params.add(param);
            }
            return this;
        }
        public JPQLBuilder addLessThan (String field, Object param) {
            if (param != null) {
                sb.append(" and " + field + " < ? ");
                params.add(param);
            }
            return this;
        }
        public JPQLBuilder addLessThanOrEqualTo (String field, Object param) {
            if (param != null) {
                sb.append(" and " + field + " <= ? ");
                params.add(param);
            }
            return this;
        }
        public JPQLBuilder addLike (String field, Object param) {
            if (param != null) {
                sb.append(" and " + field + " like ? ");
                params.add(param);
            }
            return this;
        }
        public JPQLBuilder addNotLike (String field, Object param) {
            if (param != null) {
                sb.append(" and " + field + " not like ? ");
                params.add(param);
            }
            return this;
        }
        public JPQLBuilder addIn (String field, List<String> param) {
            if (param != null && !param.isEmpty()) {
                String elements = "";
                for (int i = 0, size = param.size(); i < size; i++) {
                    if ("".equals(param.get(i))) {
                        continue;
                    }
                    if (i + 1 == size) {
                        elements = elements + "\'" + param.get(i) + "\'";
                    } else {
                        elements = elements + "\'" + param.get(i) + "\',";
                    }
                }
                if (!"".equals(elements)) {
                    sb.append(" and " + field + " in (" + elements + ")");
                }
            }
            return this;
        }
        public JPQLBuilder addNotIn (String field, List<String> param) {
            if (param != null && !param.isEmpty()) {
                String elements = "";
                for (int i = 0, size = param.size(); i < size; i++) {
                    if ("".equals(param.get(i))) {
                        continue;
                    }
                    if (i + 1 == size) {
                        elements = elements + "\'" + param.get(i) + "\'";
                    } else {
                        elements = elements + "\'" + param.get(i) + "\',";
                    }
                }
                if (!"".equals(elements)) {
                    sb.append(" and " + field + " not in (" + elements + ")");
                }
            }
            return this;
        }
        public JPQLBuilder addBetween(String field, Object start, Object end) {
            if (start != null && end != null) {
                sb.append(" and " + field + " between ? and ? ");
                params.add(start);
                params.add(end);
            }
            return this;
        }
        public JPQLBuilder addNotBetween(String field, Object start, Object end) {
            if (start != null && end != null) {
                sb.append(" and " + field + " not between ? and ? ");
                params.add(start);
                params.add(end);
            }
            return this;
        }
    }


    public <T> List<T> createNativeQuery(String sql, List<Object> params, Class<T> clazz) {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(new AliasToBeanResultTransformer(clazz));
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
        }
        return query.getResultList();
    }




    public class JpqlQueryBuilder {

        private StringBuilder jpql;
        private int count;
        private ArrayList<Object> params;
        private boolean addWhereOrNot;
        /**
         * 根据传入的初始jpql，以及初始参数和该初始参数在JPQL中需要显示的查询语句片段，以便构造JpqlQueryBuilder。
         * @param jpql
         * @param param
         * @param judge
         */
        public JpqlQueryBuilder(StringBuilder jpql, Object param, String judge, ArrayList<Object> params, boolean addWhereOrNot) {
            this.params = params;
            this.count = 0;
            this.jpql = jpql;
            this.addWhereOrNot = addWhereOrNot;
            this.param(param, judge);
        }

        /**
         * 传入需要进行JPQL语句组织时，用到的参数和参数查询语句片段。
         * @param param
         * @param judge
         * @return
         */
        public JpqlQueryBuilder param(Object param, String judge) {
            if (param != null && !"".equals(param)) {
                if (count == 0 && addWhereOrNot) {
                    jpql.append(" where " + judge);
                } else {
                    jpql.append(" and " + judge);
                }
                if(!param.equals("joinsExpression")){
                    params.add(param);
                }
                ++count;
            }
            return this;
        }

        /**
         * 获得最终组织好的Jpql语句。
         * @return
         */
        public StringBuilder getJpql() {
            return jpql;
        }

        /**
         * 获得最终组织好的、相对于Jpql语句的参数列表。
         * @return
         */
        public ArrayList<Object> getJpqlParams() {
            return params;
        }

        /**
         * 获得计数器当前的值。
         * @return
         */
        public int getCount() {
            return count;
        }
    }

    public <T> List<T> find(Class<T> type, String query, List<Object> parameters) {
        return createQuery(type, query, parameters).getResultList();
    }

    public <T> TypedQuery<T> createQuery(Class<T> type, String query, List<Object> parameters) {
        TypedQuery<T> typedQuery = entityManager.createQuery(query, type);
        for (int i = 0; i < parameters.size(); i++){
            if(parameters.get(i) instanceof Date){
                typedQuery.setParameter(i + 1, (Date) parameters.get(i), TemporalType.TIMESTAMP);
            } else {
                typedQuery.setParameter(i + 1, parameters.get(i));
            }
        }
        return typedQuery;
    }


    public <T> List<T> find(Class<T> type, String query, Object... parameters) {
        return createQuery(type, query, parameters).getResultList();
    }

    public <T> Optional<T> first(Class<T> type, String query, Object... parameters) {
        try {
            return Optional.of(createQuery(type, query, parameters).setMaxResults(1).getSingleResult());
        } catch (NoResultException e) {
            return absent();
        }
    }

    private <T> TypedQuery<T> createQuery(Class<T> type, String query, Object[] parameters) {
        TypedQuery<T> typedQuery = entityManager.
                createQuery(query, type);
        for (int i = 0; i < parameters.length; i++){
            if(parameters[i] instanceof Date){
                typedQuery.setParameter(i + 1, (Date) parameters[i], TemporalType.DATE);
            } else {
                typedQuery.setParameter(i + 1, parameters[i]);
            }
        }
        return typedQuery;
    }


    /**
     * 根据传入的SQL和对应的VO类型获取VO的列表
     * @param type
     * @param sql
     * @param <T>
     * @return
     */
    public <T> List<T> findNativeList(Class<T> type,String sql){
        if(!sql.isEmpty()) {

            Query query = this.entityManager.createNativeQuery(sql);
            query.unwrap(SQLQuery.class).setResultTransformer(new AliasToBeanResultTransformer(type));
            return query.getResultList();
        }else {
            return new ArrayList<T>() ;
        }
    }


    /***
     * 根据完整的类名获取信息
     * @param className
     * @param sql
     * @param <T>
     * @return
     */
    public <T> List<T> findNativeList(String className,String sql){
        try {
            Class type = Class.forName(className);
            return this.findNativeList(type,sql) ;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null ;
        }
    }

    /***
     * 添加包名和类名获取
     * @param packageName
     * @param className
     * @param sql
     * @param <T>
     * @return
     */
    public <T> List<T> findNativeList(String packageName,String className,String sql ) {
        String classFullName = packageName+"."+className ;
        return this.findNativeList(classFullName,sql) ;
    }

    /***
     * 获取带参数的SQL对象
     * @param packageName
     * @param className
     * @param sql
     * @param paras
     * @param <T>
     * @return
     */
    public <T> List<T> findNativeList(String packageName,String className,String sql ,Object[] paras){

        if (paras !=null){
            String fullSql = String.format(sql,paras) ;
            return this.findNativeList(packageName,className,fullSql) ;
        }else{
            return this.findNativeList(packageName,className,sql) ;
        }

    }

    protected int update(String queryStr, Object... parameters) {
        try {
            Query query = entityManager.createQuery(queryStr);
            for(int i = 0; i < parameters.length; i++) {
                if(parameters[i] instanceof  Date) {
                    query.setParameter(i + 1, (Date) parameters[i], TemporalType.TIMESTAMP);
                } else {
                    query.setParameter(i + 1, parameters[i]);
                }
            }
            int i = query.executeUpdate();
            return i;
        } catch (Exception e) {
            propagate(e);
        }
        return 0;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }
}
