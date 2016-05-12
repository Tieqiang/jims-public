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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.*;
import javax.persistence.Transient;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer;
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
    private String img;
    private String hospitalName;

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
                s = s.replaceAll("</?[a-zA-Z]+[^><]*>", "").
                        replace("<h1>", "").replace("</h1>", "").
                        replace("<h2>", "").replace("</h2>", "").
                        replace("<h3>", "").replace("</h3>", "").
                        replace("<h4>", "").replace("</h4>", "").
                        replace("<h5>", "").replace("</h5>", "").
                        replace("<h6>", "").replace("</h6>", "");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return s;
        }
        return null;
    }

    @Transient
    public String getTranDescription3() {
        if (this.description != null && !"".equals(this.description)) {
            String s = "";
            String sub="";
            try {
                s = new String(this.description, "UTF-8");
                s = s.replaceAll("</?[a-zA-Z]+[^><]*>", "").
                        replace("<h1>", "").replace("</h1>", "").
                        replace("<h2>", "").replace("</h2>", "").
                        replace("<h3>", "").replace("</h3>", "").
                        replace("<h4>", "").replace("</h4>", "").
                        replace("<h5>", "").replace("</h5>", "").
                        replace("<h6>", "").replace("</h6>", "");
                 if(s.length()>22){
                     sub=s.substring(0,22)+"...";
                 }else{
                     sub=s;
                 }
             } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "<a title='"+s+"' style='text-decoration: none;'>"+sub+"</a>";
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
//        JSWBlend_SRC_OUTPeer.
//        String regexstr = "@"<(?!br).\\\\*?>"";   //去除所有标签，只剩br
        String s = "<span><a>你好</a></span>";
//        String regex="<[^>]*>";
//        Pattern pattern= Pattern.compile(regex);
//        Matcher matcher=pattern.matcher(s);
//
////        String all=s.replace(regex, s);
//        System.out.println( matcher.group());
        s = s.replaceAll("<[A-z/ =']*>", "");
        System.out.println(s);


    }

    @Transient
    public String getImg() {
        return this.img;
    }

    public void setImg(String url) {
        this.img = "<img src='" + url + "' style='width:100%;'/>";
    }

    @Transient
    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
}
