package inf.puc.rio.opus.composite.model;

public class CodeElement {
		
	public final String methodName;
	public final String attributeName;
	public final String className;
	
	private String packageName;
	private String details;
	
	
	public CodeElement(String methodName, String attributeName, String className) {
		
		this.methodName = methodName;
		this.attributeName = attributeName;
		this.className = className;
	}
	
	public String getPackageName() {
		// TODO Auto-generated method stub
		return packageName;
	}
	
	
	public void setPackageName(String packageName) {
		
		if(this.packageName == null){
			this.packageName = packageName;
		}
	}

	public void setDetails(String details) {
		
		if(this.details == null){
			this.details = details;
		}
	}
	
	public String getDetails() {
		return details;
	}
	
	
	
	
  
	
	
}
