/**
 * <copyright>
 * </copyright>
 *
 * 
 */
package eu.hyvar.feature.expression.resource.hyexpression.mopp;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <p>
 * Standard implementation of <code>IContextDependentURIFragment</code>.
 * </p>
 * 
 * @param <ContainerType> the type of the object that contains the reference which
 * shall be resolved by this fragment.
 * @param <ReferenceType> the type of the reference which shall be resolved by
 * this fragment.
 */
public abstract class HyexpressionContextDependentURIFragment<ContainerType extends EObject, ReferenceType extends EObject> implements eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionContextDependentURIFragment<ReferenceType> {
	
	protected String identifier;
	protected ContainerType container;
	protected EReference reference;
	protected int positionInReference;
	protected EObject proxy;
	protected eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionReferenceResolveResult<ReferenceType> result;
	
	private boolean resolving;
	
	public HyexpressionContextDependentURIFragment(String identifier, ContainerType container, EReference reference, int positionInReference, EObject proxy) {
		this.identifier = identifier;
		this.container = container;
		this.reference = reference;
		this.positionInReference = positionInReference;
		this.proxy = proxy;
	}
	
	public boolean isResolved() {
		return result != null;
	}
	
	public eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionReferenceResolveResult<ReferenceType> resolve() {
		if (resolving) {
			return null;
		}
		resolving = true;
		if (result == null || !result.wasResolved()) {
			result = new eu.hyvar.feature.expression.resource.hyexpression.mopp.HyexpressionReferenceResolveResult<ReferenceType>(false);
			// set an initial default error message
			result.setErrorMessage(getStdErrorMessage());
			
			eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionReferenceResolver<ContainerType, ReferenceType> resolver = getResolver();
			// do the actual resolving
			resolver.resolve(getIdentifier(), getContainer(), getReference(), getPositionInReference(), false, result);
			
			// EMFText allows proxies to resolve to multiple objects. The first one is
			// returned, the others are added here to the reference.
			if (result.wasResolvedMultiple()) {
				handleMultipleResults();
			}
		}
		resolving = false;
		return result;
	}
	
	public abstract eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionReferenceResolver<ContainerType, ReferenceType> getResolver();
	
	private void handleMultipleResults() {
		EList<EObject> list = null;
		Object temp = container.eGet(reference);
		if (temp instanceof EList<?>) {
			list = eu.hyvar.feature.expression.resource.hyexpression.util.HyexpressionCastUtil.cast(temp);
		}
		
		boolean first = true;
		for (eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionReferenceMapping<ReferenceType> mapping : result.getMappings()) {
			if (first) {
				first = false;
			} else if (list != null) {
				addResultToList(mapping, proxy, list);
			} else {
				new eu.hyvar.feature.expression.resource.hyexpression.util.HyexpressionRuntimeUtil().logError(container.eClass().getName() + "." + reference.getName() + " has multiplicity 1 but was resolved to multiple elements", null);
			}
		}
	}
	
	private void addResultToList(eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionReferenceMapping<ReferenceType> mapping, EObject proxy, EList<EObject> list) {
		EObject target = null;
		int proxyPosition = list.indexOf(proxy);
		
		if (mapping instanceof eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionElementMapping<?>) {
			target = ((eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionElementMapping<ReferenceType>) mapping).getTargetElement();
		} else if (mapping instanceof eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionURIMapping<?>) {
			target = EcoreUtil.copy(proxy);
			URI uri = ((eu.hyvar.feature.expression.resource.hyexpression.IHyexpressionURIMapping<ReferenceType>) mapping).getTargetIdentifier();
			((InternalEObject) target).eSetProxyURI(uri);
		} else {
			assert false;
		}
		try {
			// if target is an another proxy and list is "unique" add() will try to resolve
			// the new proxy to check for uniqueness. There seems to be no way to avoid that.
			// Until now this does not cause any problems.
			if (proxyPosition + 1 == list.size()) {
				list.add(target);
			} else {
				list.add(proxyPosition + 1, target);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	private String getStdErrorMessage() {
		String typeName = this.getReference().getEType().getName();
		String msg = typeName + " '" + identifier + "' not declared";
		return msg;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public ContainerType getContainer() {
		return container;
	}
	
	public EReference getReference() {
		return reference;
	}
	
	public int getPositionInReference() {
		return positionInReference;
	}
	
	public EObject getProxy() {
		return proxy;
	}
	
}
