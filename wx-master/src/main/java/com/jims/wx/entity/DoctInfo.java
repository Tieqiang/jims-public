package com.jims.wx.entity;

import java.beans.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

/**
 * DoctInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "DOCT_INFO", schema = "WX")
public class DoctInfo implements java.io.Serializable {


    // Fields
    private String id;
    private String name;
    private String title;
    private String hospitalId;
    private String headUrl;
    private byte[] description;//byte[]
    @Transient
    private String tranDescription;

//    private Set<ClinicIndex> clinicIndexes = new HashSet<ClinicIndex>(0);


    // Constructors

    /**
     * default constructor
     */
    public DoctInfo() {
    }

    /**
     * minimal constructor
     */
    public DoctInfo(String name) {
        this.name = name;
    }

    /**
     * full constructor
     */
    public DoctInfo(String name, String title, String hospitalId,
                    String headUrl, byte[] description) {
        this.name = name;
        this.title = title;
        this.hospitalId = hospitalId;
        this.headUrl = headUrl;
        this.description = description;
//		this.clinicIndexes = clinicIndexes;
    }

    // Property accessors
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false, length = 64)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "NAME", nullable = false, length = 20)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "TITLE", length = 10)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "HOSPITAL_ID", length = 64)
    public String getHospitalId() {
        return this.hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    @Column(name = "HEAD_URL", length = 1024)
    public String getHeadUrl() {
        return this.headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }


//    @JsonManagedReference
//	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "doctInfo")
//	public Set<ClinicIndex> getClinicIndexes() {
//		return this.clinicIndexes;
//	}
//
//	public void setClinicIndexes(Set<ClinicIndex> clinicIndexes) {
//		this.clinicIndexes = clinicIndexes;
//	}

    public void setTranDescription(String tranDescription) {
        this.tranDescription = tranDescription;
    }

    @Column(name = "description")
    public byte[] getDescription() {
        return description;
    }

    public void setDescription(byte[] description) {
        this.description = description;
    }

    @Transient
    public String getTranDescription() {
        if (this.description != null && !"".equals(this.description)) {
            String s = "";
            try {
                s = new String(this.description, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return s;
        }
        return null;
    }
    @Transient
    public String getTranDescription2() {
        if (this.description != null && !"".equals(this.description)) {
            String s = "";
            try {
                s = new String(this.description, "UTF-8");

                s = s.replaceAll("@\"<[^>]*>\"", "");
//                int len = str.length();
//                if (len <= length) {
//                    return str;
//                } else {
//                    str = str.substring(0, length);
//                    str += "......";
//                }
//                return str;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return s;
        }
        return null;
    }


}

//}