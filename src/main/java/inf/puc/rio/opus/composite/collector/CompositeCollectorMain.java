package inf.puc.rio.opus.composite.collector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.model.CompositeHeuristic;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.ProjectHistoric;
import inf.puc.rio.opus.composite.model.Refactoring;

/**
 * Hello world!
 *
 */
public class CompositeCollectorMain{
	
    public static void main( String[] args ){
       //Recebe json 
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
       //Collect composites 
       //collect commit-based 
       //collect scope-based 
    	
   
    	
        try {
			
    			// List<Refactoring> refactorings = Arrays.asList(mapper.readValue(new File("dubbo-test.json"), Refactoring[].class));
    		    Refactoring[] refactorings = mapper.readValue(new File("dubbo-test.json"), Refactoring[].class);
    		  
    		    List<Refactoring> refList = Arrays.asList(refactorings);
    		    
    		    ProjectHistoric projectHistoric =  mapper.readValue(new File("dubbo.json"), ProjectHistoric.class);
    		    
    		    CompositeHeuristic heuristic = new CompositeHeuristic(projectHistoric);
   
    		    
    		    List<CompositeRefactoring> compositesRangeBased = heuristic.getCompositeRangeBased(refList, projectHistoric);
    		    mapper.writeValue(new File("dubbo-compositesRangeBased.json"), compositesRangeBased);
    		    
    		    List<CompositeRefactoring> compositesCommitBased = heuristic.getCommitBasedComposites(refList);
    		    mapper.writeValue(new File("dubbo-compositesCommitBased.json"), compositesCommitBased);

    		    
    		    System.out.println(refList.size());
    		    
    		    
    		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
