package com.jims.wx.util;

import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Result transformer that allows to transform a result to
 * a user specified class which will be populated via setter
 * methods or fields matching the alias names.
 * <p/>
 * <pre>
 * List resultWithAliasedBean = s.createCriteria(Enrolment.class)
 * 			.createAlias("student", "st")
 * 			.createAlias("course", "co")
 * 			.setProjection( Projections.projectionList()
 * 					.add( Projections.property("co.description"), "courseDescription" )
 * 			)
 * 			.setResultTransformer( new AliasToBeanResultTransformer(StudentDTO.class) )
 * 			.list();
 *
 *  StudentDTO dto = (StudentDTO)resultWithAliasedBean.get(0);
 * 	</pre>
 *
 * @author max
 */
public class AliasToBeanResultTransformer extends AliasedTupleSubsetResultTransformer {

    // IMPL NOTE : due to the delayed population of setters (setters cached
    // 		for performance), we really cannot properly define equality for
    // 		this transformer

    private final Class resultClass;
    private boolean isInitialized;
    private String[] aliases;
    private Setter[] setters;

    public AliasToBeanResultTransformer(Class resultClass) {
        if (resultClass == null) {
            throw new IllegalArgumentException("resultClass cannot be null");
        }
        isInitialized = false;
        this.resultClass = resultClass;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    public Object transformTuple(Object[] tuple, String[] aliases) {
        Object result;

        try {
            if (!isInitialized) {
                initialize(aliases);
            } else {
                check(aliases);
            }

            result = resultClass.newInstance();

            for (int i = 0; i < aliases.length; i++) {
                if (setters[i] != null) {
                    Class<?> aClass = setters[i].getMethod().getParameterTypes()[0];
                    Object temp;
                    if(tuple[i] instanceof BigDecimal) {
                        if(aClass == Integer.class) {
                            temp = ((BigDecimal)tuple[i]).intValue();
                        } else if(aClass == BigInteger.class) {
                            temp = ((BigDecimal)tuple[i]).toBigInteger();
                        } else if(aClass == Double.class) {
                            temp = ((BigDecimal)tuple[i]).doubleValue();
                        } else if(aClass == Float.class) {
                            temp = ((BigDecimal)tuple[i]).floatValue();
                        } else if(aClass == Long.class) {
                            temp = ((BigDecimal)tuple[i]).longValue();
                        } else if(aClass == Short.class) {
                            temp = ((BigDecimal)tuple[i]).shortValue();
                        } else if(aClass == Byte.class) {
                            temp = ((BigDecimal)tuple[i]).byteValue();
                        } else {
                            temp = tuple[i];
                        }
                    } else {
                        temp = tuple[i];
                    }
                    setters[i].set(result, temp, null);
                }
            }
        } catch (InstantiationException e) {
            throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
        } catch (IllegalAccessException e) {
            throw new HibernateException("Could not instantiate resultclass: " + resultClass.getName());
        }

        return result;
    }

    private void initialize(String[] aliases) {
        PropertyAccessor propertyAccessor = new ChainedPropertyAccessor(
                new PropertyAccessor[]{
                        PropertyAccessorFactory.getPropertyAccessor(resultClass, null),
                        PropertyAccessorFactory.getPropertyAccessor("field")
                }
        );
        this.aliases = new String[aliases.length];
        setters = new Setter[aliases.length];
        for (int i = 0; i < aliases.length; i++) {
            String alias = StringUtils.toHumpName(aliases[i]);
            if (alias != null) {
                this.aliases[i] = alias;
                setters[i] = propertyAccessor.getSetter(resultClass, alias);
            }
        }
        isInitialized = false;
    }

    private void check(String[] aliases) {
        if (!Arrays.equals(aliases, this.aliases)) {
            throw new IllegalStateException(
                    "aliases are different from what is cached; aliases=" + Arrays.asList(aliases) +
                            " cached=" + Arrays.asList(this.aliases));
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AliasToBeanResultTransformer that = (AliasToBeanResultTransformer) o;

        if (!resultClass.equals(that.resultClass)) {
            return false;
        }
        if (!Arrays.equals(aliases, that.aliases)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = resultClass.hashCode();
        result = 31 * result + (aliases != null ? Arrays.hashCode(aliases) : 0);
        return result;
    }
}

