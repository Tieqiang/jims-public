package com.jims.wx.util;


import com.jims.wx.entity.ClinicMasterHis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenxy on 2016/8/29.
 */
public class BatchSave {
    private static String URL="";
    private static String USER_NAME="";
    private static String PASS_WORD="";

    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BatchSave.class);
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ResourceBundle resourceBundle=ResourceBundle.getBundle("jims");
            URL=resourceBundle.getString("jdbc.url");
            USER_NAME=resourceBundle.getString("jdbc.username");
            PASS_WORD=resourceBundle.getString("jdbc.password");
            connectionThreadLocal.set(DriverManager.getConnection(URL,USER_NAME,PASS_WORD));
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }


    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection =DriverManager.getConnection(URL,USER_NAME,PASS_WORD);
            return connection;
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
            return null;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static boolean batchSaveTest(ClinicMasterHis clinicMasterHis) {
        int insertCount = 0;
        Statement statement = null;
        Connection connection = connectionThreadLocal.get();
        try {
            if (connection == null) {
                connection = getConnection();
            }
            if (connection.isClosed()) {
                connection = getConnection();
            }
            connection.setAutoCommit(false);
                 statement = connection.createStatement();

//                 String sql="insert into clinic_master (VISIT_DATE, VISIT_NO, CLINIC_LABEL, VISIT_TIME_DESC, SERIAL_NO, PATIENT_ID, NAME, NAME_PHONETIC, SEX, AGE, IDENTITY, CHARGE_TYPE, INSURANCE_TYPE, INSURANCE_NO, UNIT_IN_CONTRACT, CLINIC_TYPE, FIRST_VISIT_INDICATOR, VISIT_DEPT, VISIT_SPECIAL_CLINIC, DOCTOR, MR_PROVIDE_INDICATOR, REGISTRATION_STATUS, REGISTERING_DATE, SYMPTOM, REGIST_FEE, CLINIC_FEE, OTHER_FEE, CLINIC_CHARGE, OPERATOR, RETURNED_DATE, RETURNED_OPERATOR, MODE_CODE, CARD_NAME, CARD_NO, ACCT_DATE_TIME, ACCT_NO, PAY_WAY, MR_PROVIDED_INDICATOR, INVOICE_NO, CLINIC_NO, MR_NO, ISPRN, PAT_TYPE, VALID_DATE, AUTO_FLAG, PRINT_OPERATOR, PE_VISIT_ID, MAILING_ADDRESS, DATE_OF_BIRTH, AGE_1, RETURNED_REASON)" +
//                         "values ('"+clinicMasterHis.getVisitDate1()+"',"+clinicMasterHis.getVisitNo()+", '"+clinicMasterHis.getClinicLabel()+"', '"+clinicMasterHis.getVisitTimeDesc1()+"',"+clinicMasterHis.getSerialNo()+", '"+clinicMasterHis.getPatientId()+"', '"+clinicMasterHis.getName()+"', '"+clinicMasterHis.getNamePhonetic()+"', '"+clinicMasterHis.getSex()+"',"+clinicMasterHis.getAge()+", '"+clinicMasterHis.getIdentity()+"', '"+clinicMasterHis.getChargeType()+"', null, null, null, '"+clinicMasterHis.getClinicType()+"', 1, '"+clinicMasterHis.getVisitDept()+"', null, '"+clinicMasterHis.getDoctor()+"', null, "+clinicMasterHis.getRegistrationStatus()+", "+clinicMasterHis.getRegisteringDate()+", null, "+clinicMasterHis.getRegistFee()+", "+clinicMasterHis.getClinicFee()+","+clinicMasterHis.getOtherFee()+","+clinicMasterHis.getClinicCharge()+", '微信支付', null, null, 'A', '微信支付', "+clinicMasterHis.getCardNo()+", null, null, '微信', 0, null, null, null, null, null, null, null, null, null, null, null, null, null)";
                String sql="insert into OUTPADM.clinic_master (VISIT_DATE, VISIT_NO, CLINIC_LABEL, VISIT_TIME_DESC,\n" +
                        " PATIENT_ID, NAME, IDENTITY, CHARGE_TYPE,\n" +
                        "\n" +
                        " VISIT_DEPT,  REGISTRATION_STATUS,\n" +
                        " REGISTERING_DATE,  REGIST_FEE, CLINIC_FEE, OTHER_FEE, CLINIC_CHARGE,\n" +
                        " OPERATOR,  MODE_CODE, CARD_NAME, CARD_NO,\n" +
                        "  PAY_WAY,sex,serial_no,clinic_type\n" +
                        "  \n" +
                        " )values (to_date('"+clinicMasterHis.getVisitDateTrans()+"', 'dd-mm-yyyy'),\n" +
                        " "+clinicMasterHis.getVisitNo()+", '"+clinicMasterHis.getClinicLabel()+"', '"+clinicMasterHis.getVisitTimeDesc1()+"', '"+clinicMasterHis.getPatientId()+"',\n" +
                        " '"+clinicMasterHis.getName()+"','"+clinicMasterHis.getIdentity()+"', '"+clinicMasterHis.getChargeType()+"',\n" +
                        " '"+clinicMasterHis.getVisitDept()+"',  2, to_date('"+clinicMasterHis.getRegistDateTrans()+"', 'dd-mm-yyyy'), "+clinicMasterHis.getRegistFee()+", "+clinicMasterHis.getClinicFee()+","+clinicMasterHis.getOtherFee()+",\n" +
                        ""+clinicMasterHis.getClinicCharge()+", '微信支付', 'A', '微信支付', "+clinicMasterHis.getCardNo()+", '微信','"+clinicMasterHis.getSex()+"',"+clinicMasterHis.getSerialNo()+",'"+clinicMasterHis.getClinicType()+"')";
                System.out.println("sql="+sql);
                int count = statement.executeUpdate(sql);
                insertCount += count;
                statement.close();

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        if (insertCount > 0)
            return true;
        return false;
    }


}
