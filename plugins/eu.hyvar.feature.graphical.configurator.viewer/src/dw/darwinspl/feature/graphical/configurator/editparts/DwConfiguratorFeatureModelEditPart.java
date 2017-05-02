package dw.darwinspl.feature.graphical.configurator.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;

import dw.darwinspl.feature.graphical.base.editor.DwGraphicalFeatureModelViewer;
import dw.darwinspl.feature.graphical.base.editparts.DwEnumEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwFeatureEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwFeatureModelEditPart;
import dw.darwinspl.feature.graphical.base.editparts.DwParentChildConnectionEditPart;
import dw.darwinspl.feature.graphical.configurator.figures.DwConfiguratorFeatureModelFigure;
import dw.darwinspl.feature.graphical.configurator.viewer.DwFeatureModelConfiguratorViewer;

public class DwConfiguratorFeatureModelEditPart extends DwFeatureModelEditPart{
	public DwConfiguratorFeatureModelEditPart(DwGraphicalFeatureModelViewer editor) {
		super(editor);
	}

	@Override
	protected IFigure createFigure() {
	    return new DwConfiguratorFeatureModelFigure(editor.getModelWrapped(), (DwFeatureModelConfiguratorViewer)editor);
	}
	
	@Override
	public void refresh(){
		this.figure.erase();
		super.refresh();
		
		for(Object child : this.getChildren()){
			if(child instanceof DwParentChildConnectionEditPart)
				((EditPart)child).refresh();
			if(child instanceof DwFeatureEditPart){
				((EditPart)child).refresh();
			}
			if(child instanceof DwEnumEditPart){
				((EditPart)child).refresh();
			}
		}	
	}
}
