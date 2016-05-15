package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by cxy on 2016/5/9.
 */
@Entity
@Table(name = "symptom_sickness", schema = "WX")
public class SymptomVsSickness implements Serializable {

    private String id;//主键

    private String symptomId;//症状表主键

    private String sicknessId;//疾病表主键

    private String symptomName;

    private String sicknessName;

    private String sex;



    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "id", unique = true, nullable = false, length = 64)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name="symptom_id")
    public String getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(String symptomId) {
        this.symptomId = symptomId;
    }
    @Column(name="sickness_id")
    public String getSicknessId() {
        return sicknessId;
    }

    public void setSicknessId(String sicknessId) {
        this.sicknessId = sicknessId;
    }

    public SymptomVsSickness(){

    }
    public SymptomVsSickness(String id, String symptomId, String sicknessId) {
        this.id = id;
        this.symptomId = symptomId;
        this.sicknessId = sicknessId;
    }
   @Transient
    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }
    @Transient
    public String getSicknessName() {
        return sicknessName;
    }

    public void setSicknessName(String sicknessName) {
        this.sicknessName = sicknessName;
    }
    @Column(name="sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

