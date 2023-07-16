package inf.puc.rio.opus.composite.collector;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import inf.puc.rio.opus.composite.model.CompositeHeuristic;
import inf.puc.rio.opus.composite.model.CompositeRefactoring;
import inf.puc.rio.opus.composite.model.ProjectHistoric;
import inf.puc.rio.opus.composite.model.Refactoring;


public class CompositeCollectorMain{

    public static void main( String[] args ){

    	ObjectMapper mapper = new ObjectMapper();

        try {
				String projectName = "mybatis-3";
				File file = new File("/home/igor/Documentos/Mestrado/0_outputs/composite-refactoring-collector/" + projectName + "-refactorings.json");
    		    Refactoring[] refactorings = mapper.readValue(file, Refactoring[].class);

    		    List<Refactoring> refList = Arrays.asList(refactorings);

    		    ProjectHistoric projectHistoric =  mapper.readValue(new File("/home/igor/Documentos/Mestrado/0_outputs/MSR/" + projectName + "-MSR.json"), ProjectHistoric.class);

    		    CompositeHeuristic heuristic = new CompositeHeuristic(projectHistoric);

    		    List<CompositeRefactoring> compositesRangeBased = heuristic.getCompositeRangeBased(refList, projectHistoric);
    		    mapper.writeValue(new File( projectName + "-composite-rangebased.json"), compositesRangeBased);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
