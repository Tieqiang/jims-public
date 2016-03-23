package com.jims.wx.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by heren on 2015/10/9.
 */
@Entity
@Table(name = "BASE_DICT", schema = "WX")
public class BaseDict implements Serializable {

    private String id ;
    private String baseCode ;
    private String baseName ;
    private String baseType ;
    private String inputCode ;

    public BaseDict(String id, String baseCode, String baseName, String baseType, String inputCode) {
        this.id = id;
        this.baseCode = baseCode;
        this.baseName = baseName;
        this.baseType = baseType;
        this.inputCode = inputCode;
    }

    public BaseDict() {
    }

    // Property accessors
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

    @Column(name="base_code")
    public String getBaseCode() {
        return baseCode;
    }

    public void setBaseCode(String baseCode) {
        this.baseCode = baseCode;
    }

    @Column(name = "base_name")
    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    @Column(name="base_type")
    public String getBaseType() {
        return baseType;
    }

    public void setBaseType(String baseType) {
        this.baseType = baseType;
    }

    @Column(name="input_code")
    public String getInputCode() {
        return inputCode;
    }

    public void setInputCode(String inputCode) {
        this.inputCode = inputCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BaseDict{");
        sb.append("id='").append(id).append('\'');
        sb.append(", baseCode='").append(baseCode).append('\'');
        sb.append(", baseName='").append(baseName).append('\'');
        sb.append(", baseType='").append(baseType).append('\'');
        sb.append(", inputCode='").append(inputCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
