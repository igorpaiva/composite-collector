package inf.puc.rio.opus.composite.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CompositeHeuristic {
	
	
	public List<CompositeRefactoring> getCompositeRangeBased(
			List<Refactoring> refactorings) {
		// TODO Auto-generated method stub
		
		
		List<HashSet<Refactoring>> composites = new ArrayList<HashSet<Refactoring>>();

		for (int i = 0; i < refactorings.size(); i++) {
			// Get class name and package name of current refactoring

			HashSet<Refactoring> composite = new HashSet<Refactoring>();
			composite.add(refactorings.get(i));
			for (CodeElement elementi : refactorings.get(i).getElements()) {

				for (int j = 0; j < refactorings.size(); j++) {
					// Get class name and package name of current refactoring

					if (i != j  && isSameDeveloper(refactorings.get(i).getCurrentCommit(), refactorings.get(j).getCurrentCommit())) {

						for (CodeElement elementj : refactorings.get(j)
								.getElements()) {

							
							if (elementi.getClassName()!= null && elementi.getClassName().equals(elementj.getClassName())) {

								composite.add(refactorings.get(j));

							}
							if(elementi.getClassName() == null && elementi.getPackageName().equals(elementj.getPackageName())){
								composite.add(refactorings.get(j));
							}
							
						}
					}

				}

			}

			composites.add(composite);

		}

		composites = removeDuplicatedRefactoringsInComposites(composites);
		return getComposites("range-based", composites);
	}
	
	

	
	public List<CompositeRefactoring> getCommitBasedComposites(List<Refactoring> refactorings) {
		
	
		Set<String> allCommits = getAllCommitsOfRefactorings(refactorings);
		
		List<HashSet<Refactoring>> composites = new ArrayList<HashSet<Refactoring>>();

	
		for(String commit: allCommits) {
			
			HashSet<Refactoring> composite = new HashSet<Refactoring>();
			
			for (int i = 0; i < refactorings.size(); i++) {
				
				String refCommit = refactorings.get(i).getCurrentCommit().getCommit();
				
				if(commit.equals(refCommit)) {
					composite.add(refactorings.get(i));
				}	

			}
			
			composites.add(composite);
			
		}
		
		composites = removeDuplicatedRefactoringsInComposites(composites);
		return getComposites("commit-based", composites);
	
	}
	
	
	
	private Set<String> getAllCommitsOfRefactorings(List<Refactoring> refactorings) {
		// TODO Auto-generated method stub
		
		Set<String> allCommits = new HashSet<String>();
		
		for(Refactoring ref : refactorings) {
			
			String refCommit = ref.getCurrentCommit().getCommit();
			
			allCommits.add(refCommit);
		}
		
		return allCommits;
	}




	private List<HashSet<Refactoring>> removeDuplicatedRefactoringsInComposites(List<HashSet<Refactoring>> composites) {
		
		for (int b = 0; b < composites.size(); b++) {

			System.out.println("passei aqui " + composites.size());
			HashSet<Refactoring> batchSet = composites.get(b);
			
			for (int ba = b+1; ba < composites.size();) {
				
				List<Refactoring> refs1 = new ArrayList<Refactoring>(composites.get(b));
				List<Refactoring> refs2 = new ArrayList<Refactoring>(composites.get(ba));
				
				if (containsRefactorings(refs1, refs2)) {
			     		
					composites.remove(ba);
					System.out.println("passei aqui - Remove " + composites.size());
					System.out.println("Ba: " + ba);
					System.out.println("b: " + b);
						
				}
				else{
					ba++;
				}
				
			}
			
			

		}
		
		return composites;
	}
	
	private List<CompositeRefactoring> getComposites(String heuristicType, List<HashSet<Refactoring>> composites) {
		
		List<CompositeRefactoring> compositeList = new ArrayList<CompositeRefactoring>();

		int i = 0;
		for(HashSet<Refactoring> compositeSet : composites){
            i++;
			if (compositeSet.size() > 1) {
				CompositeRefactoring compositeRef = new CompositeRefactoring(String.valueOf(i), 
						                                                    new ArrayList(compositeSet), 
						                                                    heuristicType);
				compositeList.add(compositeRef);
			}
		}
		
		return compositeList;
		
	}
	
	private boolean containsRefactorings(List<Refactoring> refs1, List<Refactoring> refs2){
        
		for(int i=0; i<refs1.size(); i++){
			
			for(int j=0; j<refs2.size(); j++){
			   
				if(refs1.get(i).equals(refs2.get(j))){
					return true;
				}
			
		    }
		}
		return false;
		
	}
	
	public boolean isSameDeveloper(Commit commitRefi, Commit commitRefj){
	    return true;
	}

}
