package dw.darwinspl.feature.graphical.base.factory;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalViewer;

import dw.darwinspl.feature.graphical.base.editor.DwGraphicalFeatureModelViewer;
import dw.darwinspl.feature.graphical.base.editparts.DwAttributeEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwEnumContainerEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwEnumEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwEnumLiteralEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwFeatureEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwFeatureModelEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwGroupEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwParentChildConnectionEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwRootFeatureEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwVersionEditPart;
import dw.darwinspl.feature.graphical.base.model.DwEnumContainerWrapped;
import dw.darwinspl.feature.graphical.base.model.DwFeatureModelWrapped;
import dw.darwinspl.feature.graphical.base.model.DwFeatureWrapped;
import dw.darwinspl.feature.graphical.base.model.DwGroupWrapped;
import dw.darwinspl.feature.graphical.base.model.DwParentChildConnection;
import dw.darwinspl.feature.graphical.base.model.DwRootFeatureWrapped;
import eu.hyvar.dataValues.HyEnum;
import eu.hyvar.dataValues.HyEnumLiteral;
import eu.hyvar.feature.HyFeatureAttribute;
import eu.hyvar.feature.HyVersion;

public class DwFeatureModelEditPartFactory implements EditPartFactory{
	protected DwFeatureModelWrapped featureModel;
	protected GraphicalViewer viewer;
	
	protected DwGraphicalFeatureModelViewer editor;
	
	public DwFeatureModelEditPartFactory(GraphicalViewer viewer, DwGraphicalFeatureModelViewer editor){
		this.viewer = viewer;
		this.editor = editor;
	}
	
	public DwFeatureModelWrapped getFeatureModel() {
		return featureModel;
	}

	public void setFeatureModel(DwFeatureModelWrapped featureModel) {
		this.featureModel = featureModel;
	}

	
	

	
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		
		
		if(model instanceof DwFeatureModelWrapped){
			part = new DwFeatureModelEditPart(editor);
			viewer.getControl().addControlListener((DwFeatureModelEditPart)part);
			viewer.addPropertyChangeListener((DwFeatureModelEditPart)part);
			
			featureModel = (DwFeatureModelWrapped)model;
		}else if(model instanceof DwRootFeatureWrapped){
			part = new DwRootFeatureEditPart(editor, featureModel);
		}else if(model instanceof DwFeatureWrapped){
			part = new DwFeatureEditPart(editor, featureModel);
		}else if(model instanceof DwGroupWrapped){
			part = new DwGroupEditPart(editor, featureModel);
		}else if(model instanceof DwParentChildConnection){
			part = new DwParentChildConnectionEditPart(editor, featureModel);
		}else if(model instanceof HyVersion){
			part = new DwVersionEditPart(editor, featureModel);
		}else if(model instanceof HyFeatureAttribute){
			part = new DwAttributeEditPart(editor, featureModel);
		}else if(model instanceof DwEnumContainerWrapped){
			part = new DwEnumContainerEditPart(editor, featureModel);
		}else if(model instanceof HyEnum){
			part = new DwEnumEditPart(editor, featureModel);			
		}else if(model instanceof HyEnumLiteral){
			part = new DwEnumLiteralEditPart(editor, featureModel);
		}


		if(context != null && model != null && !(model instanceof DwParentChildConnection)){
			part.setParent(context);
		}
		if(part != null){
			part.setModel(model);
		}		
		
		return part;
	}

}
