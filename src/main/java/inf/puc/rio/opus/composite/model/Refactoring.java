package inf.puc.rio.opus.composite.model;

import java.util.ArrayList;
import java.util.List;





public class Refactoring {
	
	public final String refactoringType;
	public final String refactoringId;
	public final String refactoringDetail;
	public final Commit currentCommit;
	public final String project;
	
	private List<CodeElement> elements;
    
	public Refactoring(String id, 
			           String project, 
			           Commit commit, 
					   String refactoringType, 
				       String refactoringDetail){
		
		this.refactoringId = id;
		this.project = project;
		this.currentCommit = commit;
		this.refactoringType = refactoringType;
		this.refactoringDetail = refactoringDetail;
		this.elements = new ArrayList<CodeElement>();
		
		
		
	}
	
	
	
	public void setCodeElements(List<CodeElement> elements){
		
	   if(this.elements == null || this.elements.size() == 0) {
		   this.elements = elements;
	   }
		
	}
	
	@Override
	public String toString() {
		return refactoringType;
	}
	
	public List<CodeElement> getElements(){
		return elements;
	}
	




	
	


	
	

}




