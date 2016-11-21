package eu.hyvar.feature.graphical.editor.commands.feature;

import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gef.EditPart;

import eu.hyvar.evolution.HyEvolutionUtil;
import eu.hyvar.evolution.HyTemporalElement;
import eu.hyvar.feature.HyFeature;
import eu.hyvar.feature.HyFeatureChild;
import eu.hyvar.feature.HyGroup;
import eu.hyvar.feature.HyGroupComposition;
import eu.hyvar.feature.graphical.base.editparts.HyFeatureEditPart;
import eu.hyvar.feature.graphical.base.editparts.HyRootFeatureEditPart;
import eu.hyvar.feature.graphical.base.model.HyFeatureModelWrapped;
import eu.hyvar.feature.graphical.base.model.HyFeatureWrapped;
import eu.hyvar.feature.graphical.editor.editor.HyGraphicalFeatureModelEditor;

public class HyFeatureDeleteCommand extends FeatureConnectionChangeCommand{
	EditPart host;

	public HyFeatureDeleteCommand(HyGraphicalFeatureModelEditor editor, EditPart host) {
		super(editor);

		this.host = host;
	}

	private HyFeatureWrapped feature;

	public void setFeature(HyFeatureWrapped feature) {
		this.feature = feature;
	}

	public void setModel(HyFeatureModelWrapped model) {
		this.featureModel = model;
	}

	private void deleteFeatureChildrenTemporarily(HyFeature feature){
		Date date = editor.getCurrentSelectedDate();
		if(date.equals(new Date(Long.MIN_VALUE)))
			date = null;

		if(date == null){
			for(HyFeatureChild child : feature.getParentOf()){
				featureModel.getGroups().remove(child.getChildGroup());

				for(HyGroupComposition composition : child.getChildGroup().getParentOf()){
					for(HyFeature childFeature : composition.getFeatures()){
						featureModel.getModel().getFeatures().remove(childFeature);

						deleteFeatureChildrenTemporarily(childFeature);
					}

				}
			}	

			featureModel.getModel().getFeatures().remove(feature);

			for(HyGroupComposition composition : feature.getGroupMembership()){
				if(composition.getFeatures().size() == 1){
					HyGroup group = composition.getCompositionOf();
					featureModel.removeGroup(featureModel.findWrappedGroup(group));
				}
			}

			feature.getGroupMembership().clear();

		}else{
			List<HyFeatureChild> children = HyEvolutionUtil.getValidTemporalElements(feature.getParentOf(), date);
			for(HyFeatureChild child : children){
				List<HyGroupComposition> compositions = HyEvolutionUtil.getValidTemporalElements(child.getChildGroup().getParentOf(), date);

				for(HyGroupComposition composition : compositions){
					for(HyFeature childFeature : composition.getFeatures()){
						childFeature.setValidUntil(date);

						deleteFeatureChildrenTemporarily(childFeature);
					}

				}
			}			
		}
	}

	private void restrichtHyLinearTemporalElementsToParentValidUntil(EList<HyTemporalElement> elements){
		Date date = editor.getCurrentSelectedDate();
		for(HyTemporalElement element : elements){
			if(element.getValidUntil() == null || element.getValidUntil().after(date)){
				element.setValidUntil(date);
			}
		}			
	}

	@Override
	public boolean canExecute() {
		if(host instanceof HyRootFeatureEditPart)
			return false;
		if(host instanceof HyFeatureEditPart)
			return true;
		
		return false;
	}


	@SuppressWarnings("unchecked")
	@Override
	public void execute(){
		HyFeature feature = this.feature.getWrappedModelElement();
		Date date = editor.getCurrentSelectedDate();
		HyGroupComposition composition = HyEvolutionUtil.getValidTemporalElement(feature.getGroupMembership(), date);
		splitComposition(composition, this.feature);

		feature.setValidUntil(date);

		restrichtHyLinearTemporalElementsToParentValidUntil((EList<HyTemporalElement>)(EList<?>)feature.getNames());
		restrichtHyLinearTemporalElementsToParentValidUntil((EList<HyTemporalElement>)(EList<?>)feature.getAttributes());
		restrichtHyLinearTemporalElementsToParentValidUntil((EList<HyTemporalElement>)(EList<?>)feature.getVersions());

		deleteFeatureChildrenTemporarily(feature);

		// delete the selection from the element
		host.setSelected(0);

		editor.getModelWrapped().rearrangeFeatures();
		editor.refreshView();
	}
}
