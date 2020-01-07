package inf.puc.rio.opus.composite.model;

public class Commit {
	
	public final String commit;
	public final String previousCommit;
	public final int orderCommit;
	
	
	public Commit(String commit, String previousCommit, int orderCommit) {
		
		this.commit = commit;
		this.previousCommit = previousCommit;
		this.orderCommit = orderCommit;
	}
	
	
	
	
	

}
