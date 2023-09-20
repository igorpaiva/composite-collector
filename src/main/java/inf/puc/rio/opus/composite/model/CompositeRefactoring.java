package inf.puc.rio.opus.composite.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CompositeRefactoring {

	public String id;

	public List<Refactoring> refactorings;

	private List<String> elements;

	private String elementType;
	
	private List<Method> involvedMethodsBeforeRefactor;

	private List<Method> involvedMethodsAfterRefactor;

	public String type;

	public CompositeRefactoring(String id, List<Refactoring> refactorings, String type) {

		this.id = id;
		this.refactorings = refactorings;
		this.type = type;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
	}

	public static class CompositeRefactoringBuilder {
		private List<String> elements;
		private String elementType;
		private List<Method> involvedMethodsBeforeRefactor;
		private List<Method> involvedMethodsAfterRefactor;

		public CompositeRefactoringBuilder elements(List<String> elements) {
			this.elements = elements;
			return this;
		}

		public CompositeRefactoringBuilder elementType(String elementType) {
			this.elementType = elementType;
			return this;
		}
		
		public CompositeRefactoringBuilder involvedMethodsBeforeRefactor(List<Method> methods) {
			this.involvedMethodsBeforeRefactor = methods;
			return this;
		}

		public CompositeRefactoringBuilder involvedMethodsAfterRefactor(List<Method> methods) {
			this.involvedMethodsAfterRefactor = methods;
			return this;
		}

		// Other builder methods for id, refactorings, and type...

		public CompositeRefactoring build() {
			CompositeRefactoring compositeRefactoring = new CompositeRefactoring(id, refactorings, type);
			compositeRefactoring.elements = this.elements;
			compositeRefactoring.elementType = this.elementType;
			compositeRefactoring.involvedMethodsBeforeRefactor = this.involvedMethodsBeforeRefactor;
			compositeRefactoring.involvedMethodsAfterRefactor = this.involvedMethodsAfterRefactor;
			return compositeRefactoring;
		}
	}
}
