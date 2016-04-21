package com.jims.wx.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

import com.jims.wx.vo.BeanChangeVo;
import com.jims.wx.vo.SubjectOptionsVo;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

/**
 * Subject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SUBJECT", schema = "WX")
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","subjectOptionses"})
public class Subject implements java.io.Serializable {

	// Fields

	private String id;
	private String questionContent;
	private String questionType;
	private String preAnswer;
    private String img;
	private Set<AnswerResult> answerResults = new HashSet<AnswerResult>(0);
	private Set<QuestionnaireVsSubject> questionnaireVsSubjects = new HashSet<QuestionnaireVsSubject>(
			0);

    @JsonManagedReference
	private Set<SubjectOptions> subjectOptionses = new HashSet<SubjectOptions>(
			0);

    @Transient
    private List<SubjectOptionsVo> options= new ArrayList<SubjectOptionsVo>();
    @Transient
    private BeanChangeVo<SubjectOptionsVo> beanChangeVo;

	/** default constructor */
	public Subject() {
	}


    /** full constructor */
    public Subject(String id, String questionContent,
                   String questionType, String preAnswer,
                   String img, Set<AnswerResult> answerResults,
                   Set<QuestionnaireVsSubject> questionnaireVsSubjects,
                   Set<SubjectOptions> subjectOptionses, List<SubjectOptionsVo> options,
                   BeanChangeVo<SubjectOptionsVo> beanChangeVo) {
        this.id = id;
        this.questionContent = questionContent;
        this.questionType = questionType;
        this.preAnswer = preAnswer;
        this.img = img;
        this.answerResults = answerResults;
        this.questionnaireVsSubjects = questionnaireVsSubjects;
        this.subjectOptionses = subjectOptionses;
        this.options = options;
        this.beanChangeVo = beanChangeVo;
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

	@Column(name = "QUESTION_CONTENT", length = 500)
	public String getQuestionContent() {
		return this.questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	@Column(name = "QUESTION_TYPE", length = 2)
	public String getQuestionType() {
		return this.questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	@Column(name = "PRE_ANSWER", length = 20)
	public String getPreAnswer() {
		return this.preAnswer;
	}

	public void setPreAnswer(String preAnswer) {
		this.preAnswer = preAnswer;
	}

    @Column(name = "IMG", length = 1024)
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
	public Set<AnswerResult> getAnswerResults() {
		return this.answerResults;
	}

	public void setAnswerResults(Set<AnswerResult> answerResults) {
		this.answerResults = answerResults;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
	public Set<QuestionnaireVsSubject> getQuestionnaireVsSubjects() {
		return this.questionnaireVsSubjects;
	}

	public void setQuestionnaireVsSubjects(
			Set<QuestionnaireVsSubject> questionnaireVsSubjects) {
		this.questionnaireVsSubjects = questionnaireVsSubjects;
	}

    @JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "subject")
	public Set<SubjectOptions> getSubjectOptionses() {
		return this.subjectOptionses;
	}

	public void setSubjectOptionses(Set<SubjectOptions> subjectOptionses) {
		this.subjectOptionses = subjectOptionses;
	}

    @Transient
    public List<SubjectOptionsVo> getOptions() {
        return options;
    }

    public void setOptions(List<SubjectOptionsVo> options) {
        this.options = options;
    }

    @Transient
    public BeanChangeVo<SubjectOptionsVo> getBeanChangeVo() {
        return beanChangeVo;
    }

    public void setBeanChangeVo(BeanChangeVo<SubjectOptionsVo> beanChangeVo) {
        this.beanChangeVo = beanChangeVo;
    }


}