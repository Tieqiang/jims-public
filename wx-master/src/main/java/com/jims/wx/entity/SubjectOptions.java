package com.jims.wx.entity;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

/**
 * SubjectOptions entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SUBJECT_OPTIONS", schema = "WX")
public class SubjectOptions implements java.io.Serializable {

	// Fields

	private String id;
    @JsonBackReference
	private Subject subject;
	private String optContent;
	private String optStatus;
    private String image;
    @Transient
private String subjectId;
	// Constructors

	/** default constructor */
	public SubjectOptions() {
	}


    /** full constructor */
    public SubjectOptions(Subject subject, String optContent, String optStatus, String image) {
        this.subject = subject;
        this.optContent = optContent;
        this.optStatus = optStatus;
        this.image = image;
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

    @JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUBJECT_ID")
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(name = "OPT_CONTENT", length = 70)
	public String getOptContent() {
		return this.optContent;
	}

	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}

	@Column(name = "OPT_STATUS", length = 2)
	public String getOptStatus() {
		return this.optStatus;
	}

	public void setOptStatus(String optStatus) {
		this.optStatus = optStatus;
	}

    @Column(name = "IMAGE", length = 1024)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }




    @Transient
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}