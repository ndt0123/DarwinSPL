package eu.hyvar.feature.graphical.editor.editor;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionFactory;

import eu.hyvar.feature.graphical.editor.actions.HyGroupEditCardinalitiesAction;
import eu.hyvar.feature.graphical.editor.actions.HyLinearTemporalElementChangeValidityAction;
import eu.hyvar.feature.graphical.editor.actions.attribute.HyAttributeRenameAction;
import eu.hyvar.feature.graphical.editor.actions.attribute.HyAttributeCreateBooleanAction;
import eu.hyvar.feature.graphical.editor.actions.attribute.HyAttributeCreateNumberAction;
import eu.hyvar.feature.graphical.editor.actions.attribute.HyAttributeCreateStringAction;
import eu.hyvar.feature.graphical.editor.actions.feature.HyFeatureCreateSiblingAction;
import eu.hyvar.feature.graphical.editor.actions.feature.HyFeatureEditCardinalitiesAction;
import eu.hyvar.feature.graphical.editor.actions.feature.HyFeatureEditNamesAction;
import eu.hyvar.feature.graphical.editor.actions.feature.HyFeatureEvolutionCreateChildAction;
import eu.hyvar.feature.graphical.editor.actions.version.HyVersionCreateSuccessorAction;
import eu.hyvar.feature.graphical.editor.actions.version.HyVersionCreateVersionAction;

public class HyFeatureModelEvolutionGraphicalEditorContextMenuProvider extends HyFeatureModelGraphicalEditorContextMenuProvider	{

	public HyFeatureModelEvolutionGraphicalEditorContextMenuProvider(EditPartViewer viewer,
			ActionRegistry actionRegistry) {
		super(viewer, actionRegistry);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		GEFActionConstants.addStandardActionGroups(menu);
		
        IAction action;
        
        action = getActionRegistry().getAction(ActionFactory.UNDO.getId());
        menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
        action = getActionRegistry().getAction(ActionFactory.REDO.getId());
        menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
        action = getActionRegistry().getAction(ActionFactory.DELETE.getId());
        menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
        
        action = getActionRegistry().getAction(HyLinearTemporalElementChangeValidityAction.FEATURE_CHANGE_VALIDITY);
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
        
        action = getActionRegistry().getAction(HyFeatureEvolutionCreateChildAction.FEATURE_EVOLUTION_CREATE_CHILD);
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
        action = getActionRegistry().getAction(HyFeatureCreateSiblingAction.FEATURE_CREATE_SIBLING);
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
        action = getActionRegistry().getAction(HyFeatureEditCardinalitiesAction.FEATURE_EDIT_CARDINALITIES);
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
        action = getActionRegistry().getAction(HyFeatureEditNamesAction.FEATURE_EDIT_NAMES);
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);      
        action = getActionRegistry().getAction(HyGroupEditCardinalitiesAction.GROUP_EDIT_CARDINALITIES);
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, new Separator());
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
        action = getActionRegistry().getAction(HyLinearTemporalElementChangeValidityAction.FEATURE_CHANGE_VALIDITY);
        
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, new Separator());
        action = getActionRegistry().getAction(HyVersionCreateVersionAction.FEATURE_ADD_VERSION);
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
        action = getActionRegistry().getAction(HyVersionCreateSuccessorAction.FEATURE_CREATE_SUCCESSOR_VERSION);
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);         
        
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, new Separator());
        action = getActionRegistry().getAction(HyAttributeCreateNumberAction.FEATURE_ADD_NUMBER_ATTRIBUTE);     
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
        action = getActionRegistry().getAction(HyAttributeCreateBooleanAction.FEATURE_ADD_BOOLEAN_ATTRIBUTE);     
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
        action = getActionRegistry().getAction(HyAttributeCreateStringAction.FEATURE_ADD_STRING_ATTRIBUTE);     
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);      

        action = getActionRegistry().getAction(HyAttributeRenameAction.ATTRIBUTE_EDIT_NAMES);
        menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
	}
}
