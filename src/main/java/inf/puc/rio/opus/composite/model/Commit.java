package inf.puc.rio.opus.composite.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"commit",
"previousCommit",
"orderCommit"
})
public class Commit {

	@JsonProperty("commit")
	private String commit;
	@JsonProperty("previousCommit")
	private String previousCommit;
	@JsonProperty("orderCommit")
	private Integer orderCommit;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("commit")
	public String getCommit() {
		return commit;
	}

	@JsonProperty("commit")
	public void setCommit(String commit) {
		this.commit = commit;
	}

	@JsonProperty("previousCommit")
	public String getPreviousCommit() {
		return previousCommit;
	}

	@JsonProperty("previousCommit")
	public void setPreviousCommit(String previousCommit) {
		this.previousCommit = previousCommit;
	}

	@JsonProperty("orderCommit")
	public Integer getOrderCommit() {
		return orderCommit;
	}

	@JsonProperty("orderCommit")
	public void setOrderCommit(Integer orderCommit) {
		this.orderCommit = orderCommit;
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