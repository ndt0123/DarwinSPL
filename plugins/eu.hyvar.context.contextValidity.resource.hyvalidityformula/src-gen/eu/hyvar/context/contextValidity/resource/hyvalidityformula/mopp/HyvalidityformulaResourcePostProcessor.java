/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package eu.hyvar.context.contextValidity.resource.hyvalidityformula.mopp;


public class HyvalidityformulaResourcePostProcessor implements eu.hyvar.context.contextValidity.resource.hyvalidityformula.IHyvalidityformulaResourcePostProcessor {
	
	public void process(eu.hyvar.context.contextValidity.resource.hyvalidityformula.mopp.HyvalidityformulaResource resource) {
		// Set the overrideResourcePostProcessor option to false to customize resource
		// post processing.
	}
	
	public void terminate() {
		// To signal termination to the process() method, setting a boolean field is
		// recommended. Depending on the value of this field process() can stop its
		// computation. However, this is only required for computation intensive
		// post-processors.
	}
	
}
