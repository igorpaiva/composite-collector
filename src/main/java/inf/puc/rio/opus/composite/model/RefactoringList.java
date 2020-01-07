//package inf.puc.rio.opus.composite.model;
//
//-----------------------------------com.example.CurrentCommit.java-----------------------------------
//
//package com.example;
//
//import java.util.HashMap;
//import java.util.Map;
//import com.fasterxml.jackson.annotation.JsonAnyGetter;
//import com.fasterxml.jackson.annotation.JsonAnySetter;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//"commit",
//"previousCommit",
//"orderCommit"
//})
//public class CurrentCommit {
//
//@JsonProperty("commit")
//private String commit;
//@JsonProperty("previousCommit")
//private String previousCommit;
//@JsonProperty("orderCommit")
//private Integer orderCommit;
//@JsonIgnore
//private Map<String, Object> additionalProperties = new HashMap<String, Object>();
//
//@JsonProperty("commit")
//public String getCommit() {
//return commit;
//}
//
//@JsonProperty("commit")
//public void setCommit(String commit) {
//this.commit = commit;
//}
//
//@JsonProperty("previousCommit")
//public String getPreviousCommit() {
//return previousCommit;
//}
//
//@JsonProperty("previousCommit")
//public void setPreviousCommit(String previousCommit) {
//this.previousCommit = previousCommit;
//}
//
//@JsonProperty("orderCommit")
//public Integer getOrderCommit() {
//return orderCommit;
//}
//
//@JsonProperty("orderCommit")
//public void setOrderCommit(Integer orderCommit) {
//this.orderCommit = orderCommit;
//}
//
//@JsonAnyGetter
//public Map<String, Object> getAdditionalProperties() {
//return this.additionalProperties;
//}
//
//@JsonAnySetter
//public void setAdditionalProperty(String name, Object value) {
//this.additionalProperties.put(name, value);
//}
//
//}
//-----------------------------------com.example.Element.java-----------------------------------
//
//package com.example;
//
//import java.util.HashMap;
//import java.util.Map;
//import com.fasterxml.jackson.annotation.JsonAnyGetter;
//import com.fasterxml.jackson.annotation.JsonAnySetter;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//"methodName",
//"attributeName",
//"className",
//"packageName",
//"details"
//})
//public class Element {
//
//@JsonProperty("methodName")
//private String methodName;
//@JsonProperty("attributeName")
//private Object attributeName;
//@JsonProperty("className")
//private String className;
//@JsonProperty("packageName")
//private Object packageName;
//@JsonProperty("details")
//private Object details;
//@JsonIgnore
//private Map<String, Object> additionalProperties = new HashMap<String, Object>();
//
//@JsonProperty("methodName")
//public String getMethodName() {
//return methodName;
//}
//
//@JsonProperty("methodName")
//public void setMethodName(String methodName) {
//this.methodName = methodName;
//}
//
//@JsonProperty("attributeName")
//public Object getAttributeName() {
//return attributeName;
//}
//
//@JsonProperty("attributeName")
//public void setAttributeName(Object attributeName) {
//this.attributeName = attributeName;
//}
//
//@JsonProperty("className")
//public String getClassName() {
//return className;
//}
//
//@JsonProperty("className")
//public void setClassName(String className) {
//this.className = className;
//}
//
//@JsonProperty("packageName")
//public Object getPackageName() {
//return packageName;
//}
//
//@JsonProperty("packageName")
//public void setPackageName(Object packageName) {
//this.packageName = packageName;
//}
//
//@JsonProperty("details")
//public Object getDetails() {
//return details;
//}
//
//@JsonProperty("details")
//public void setDetails(Object details) {
//this.details = details;
//}
//
//@JsonAnyGetter
//public Map<String, Object> getAdditionalProperties() {
//return this.additionalProperties;
//}
//
//@JsonAnySetter
//public void setAdditionalProperty(String name, Object value) {
//this.additionalProperties.put(name, value);
//}
//
//}
//-----------------------------------com.example.Example.java-----------------------------------
//
//package com.example;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import com.fasterxml.jackson.annotation.JsonAnyGetter;
//import com.fasterxml.jackson.annotation.JsonAnySetter;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({
//"refactoringType",
//"refactoringId",
//"refactoringDetail",
//"currentCommit",
//"project",
//"elements"
//})
//
//
//}