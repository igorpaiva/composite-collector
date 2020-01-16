package inf.puc.rio.opus.composite.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "authorName", "authorEmail", "authorTimeZone", "committerName", "committerEmail",
		"committerTimeZone", "numberOfBranches", "hash", "msg", "modifiedFiles", "date", "numberOfModifiedFiles" })
public class CommitHistoric {

	@JsonProperty("authorName")
	private String authorName;
	@JsonProperty("authorEmail")
	private String authorEmail;
	@JsonProperty("authorTimeZone")
	private String authorTimeZone;
	@JsonProperty("committerName")
	private String committerName;
	@JsonProperty("committerEmail")
	private String committerEmail;
	@JsonProperty("committerTimeZone")
	private String committerTimeZone;
	@JsonProperty("numberOfBranches")
	private Integer numberOfBranches;
	@JsonProperty("hash")
	private String hash;
	@JsonProperty("msg")
	private String msg;
	@JsonProperty("modifiedFiles")
	private List<String> modifiedFiles = null;
	
	@JsonProperty("date")
	private long date;

	@JsonProperty("numberOfModifiedFiles")
	private Integer numberOfModifiedFiles;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("authorName")
	public String getAuthorName() {
		return authorName;
	}

	@JsonProperty("authorName")
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	@JsonProperty("authorEmail")
	public String getAuthorEmail() {
		return authorEmail;
	}

	@JsonProperty("authorEmail")
	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	@JsonProperty("authorTimeZone")
	public String getAuthorTimeZone() {
		return authorTimeZone;
	}

	@JsonProperty("authorTimeZone")
	public void setAuthorTimeZone(String authorTimeZone) {
		this.authorTimeZone = authorTimeZone;
	}

	@JsonProperty("committerName")
	public String getCommitterName() {
		return committerName;
	}

	@JsonProperty("committerName")
	public void setCommitterName(String committerName) {
		this.committerName = committerName;
	}

	@JsonProperty("committerEmail")
	public String getCommitterEmail() {
		return committerEmail;
	}

	@JsonProperty("committerEmail")
	public void setCommitterEmail(String committerEmail) {
		this.committerEmail = committerEmail;
	}

	@JsonProperty("committerTimeZone")
	public String getCommitterTimeZone() {
		return committerTimeZone;
	}

	@JsonProperty("committerTimeZone")
	public void setCommitterTimeZone(String committerTimeZone) {
		this.committerTimeZone = committerTimeZone;
	}

	@JsonProperty("numberOfBranches")
	public Integer getNumberOfBranches() {
		return numberOfBranches;
	}

	@JsonProperty("numberOfBranches")
	public void setNumberOfBranches(Integer numberOfBranches) {
		this.numberOfBranches = numberOfBranches;
	}

	@JsonProperty("hash")
	public String getHash() {
		return hash;
	}

	@JsonProperty("hash")
	public void setHash(String hash) {
		this.hash = hash;
	}

	@JsonProperty("msg")
	public String getMsg() {
		return msg;
	}

	@JsonProperty("msg")
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@JsonProperty("modifiedFiles")
	public List<String> getModifiedFiles() {
		return modifiedFiles;
	}

	@JsonProperty("modifiedFiles")
	public void setModifiedFiles(List<String> modifiedFiles) {
		this.modifiedFiles = modifiedFiles;
	}

	@JsonProperty("date")
	public long getDate() {
		return date;
	}

	@JsonProperty("date")
	public void setDate(long date) {
		this.date = date;
	}

	@JsonProperty("numberOfModifiedFiles")
	public Integer getNumberOfModifiedFiles() {
		return numberOfModifiedFiles;
	}

	@JsonProperty("numberOfModifiedFiles")
	public void setNumberOfModifiedFiles(Integer numberOfModifiedFiles) {
		this.numberOfModifiedFiles = numberOfModifiedFiles;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}