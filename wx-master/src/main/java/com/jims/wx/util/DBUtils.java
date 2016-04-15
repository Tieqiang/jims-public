package com.jims.wx.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class DBUtils {

    public static void rollBackQuitely(EntityTransaction entityTransaction) {
        try {
            if (entityTransaction != null){
                entityTransaction.rollback();
            }
        } catch (Exception e) {
        }
    }

    public static void closeQuietly(EntityManagerFactory entityManagerFactory) {
        try {
            if (entityManagerFactory != null){
                entityManagerFactory.close();
            }
        } catch (Exception e) {
        }
    }

    public static void closeQuietly(EntityManager entityManager){
        try {
            if (entityManager != null){
                entityManager .close();
            }
        } catch (Exception e){
        }
    }
}
