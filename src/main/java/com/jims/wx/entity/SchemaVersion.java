package com.jims.wx.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * SchemaVersion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SCHEMA_VERSION", schema = "WX")
public class SchemaVersion implements java.io.Serializable {

	// Fields

	private String version;
	private BigDecimal versionRank;
	private BigDecimal installedRank;
	private String description;
	private String type;
	private String script;
	private BigDecimal checksum;
	private String installedBy;
	private String installedOn;
	private BigDecimal executionTime;
	private Boolean success;

	// Constructors

	/** default constructor */
	public SchemaVersion() {
	}

	/** minimal constructor */
	public SchemaVersion(BigDecimal versionRank, BigDecimal installedRank,
			String description, String type, String script, String installedBy,
			String installedOn, BigDecimal executionTime, Boolean success) {
		this.versionRank = versionRank;
		this.installedRank = installedRank;
		this.description = description;
		this.type = type;
		this.script = script;
		this.installedBy = installedBy;
		this.installedOn = installedOn;
		this.executionTime = executionTime;
		this.success = success;
	}

	/** full constructor */
	public SchemaVersion(BigDecimal versionRank, BigDecimal installedRank,
			String description, String type, String script,
			BigDecimal checksum, String installedBy, String installedOn,
			BigDecimal executionTime, Boolean success) {
		this.versionRank = versionRank;
		this.installedRank = installedRank;
		this.description = description;
		this.type = type;
		this.script = script;
		this.checksum = checksum;
		this.installedBy = installedBy;
		this.installedOn = installedOn;
		this.executionTime = executionTime;
		this.success = success;
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "version", unique = true, nullable = false, length = 50)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "version_rank", nullable = false, precision = 22, scale = 0)
	public BigDecimal getVersionRank() {
		return this.versionRank;
	}

	public void setVersionRank(BigDecimal versionRank) {
		this.versionRank = versionRank;
	}

	@Column(name = "installed_rank", nullable = false, precision = 22, scale = 0)
	public BigDecimal getInstalledRank() {
		return this.installedRank;
	}

	public void setInstalledRank(BigDecimal installedRank) {
		this.installedRank = installedRank;
	}

	@Column(name = "description", nullable = false, length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "type", nullable = false, length = 20)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "script", nullable = false, length = 1000)
	public String getScript() {
		return this.script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	@Column(name = "checksum", precision = 22, scale = 0)
	public BigDecimal getChecksum() {
		return this.checksum;
	}

	public void setChecksum(BigDecimal checksum) {
		this.checksum = checksum;
	}

	@Column(name = "installed_by", nullable = false, length = 100)
	public String getInstalledBy() {
		return this.installedBy;
	}

	public void setInstalledBy(String installedBy) {
		this.installedBy = installedBy;
	}

	@Column(name = "installed_on", nullable = false)
	public String getInstalledOn() {
		return this.installedOn;
	}

	public void setInstalledOn(String installedOn) {
		this.installedOn = installedOn;
	}

	@Column(name = "execution_time", nullable = false, precision = 22, scale = 0)
	public BigDecimal getExecutionTime() {
		return this.executionTime;
	}

	public void setExecutionTime(BigDecimal executionTime) {
		this.executionTime = executionTime;
	}

	@Column(name = "success", nullable = false, precision = 1, scale = 0)
	public Boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}