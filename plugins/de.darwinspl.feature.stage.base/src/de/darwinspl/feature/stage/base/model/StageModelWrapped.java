package de.darwinspl.feature.stage.base.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import de.darwinspl.feature.stage.Role;
import de.darwinspl.feature.stage.RoleInclusion;
import de.darwinspl.feature.stage.Stage;
import de.darwinspl.feature.stage.StageComposition;
import de.darwinspl.feature.stage.StageFactory;
import de.darwinspl.feature.stage.StageModel;
import eu.hyvar.evolution.HyEvolutionFactory;
import eu.hyvar.evolution.HyName;

public class StageModelWrapped implements PropertyChangeListener  {

	/**
	 * The real stage Model
	 */
	protected StageModel stageModel;
	
	/**
	 * Lists to keep track of stages and roles
	 * @param model
	 */
//	protected List<RoleWrapped> roles;
//	protected List<StageWrapped> stages;
	
	
//	public StageModelWrapped(HyFeatureModel model) {
//		super(model);
//		// TODO Auto-generated constructor stub
//	}
	
	/**
	 * Constructor for StageModelWrapped
	 * @param stageModel
	 */
	public StageModelWrapped(StageModel stageModel){
		this.stageModel = stageModel;
		
	//	groups = new ArrayList<RoleWrapped()>;
	//	stages = new ArrayList<StageWrapped()>;

	}
	
	
	/**
	 * getter for the current stageModel
	 * @return Stage Model
	 */
	public StageModel getModel(){
		return stageModel;
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Function call to create a new named Stage and initial StageComposition
	 * @param text Name for the new Stage
	 */
	public void addNewStageToModel(String text) {
		Stage newStage = StageFactory.eINSTANCE.createStage();
		StageComposition newStageComposition = StageFactory.eINSTANCE.createStageComposition();
		HyName newName =  HyEvolutionFactory.eINSTANCE.createHyName();
		newName.setName(text);		
	    newStage.getNames().add(newName);
	    newStage.getComposition().add(newStageComposition);
		stageModel.getStages().add(newStage);
	}
	
	/**
	 * Function call to create a new named Role
	 * @param text Name for the new Stage
	 */
	public void addNewRoleToModel(String text) {
		Role newRole =StageFactory.eINSTANCE.createRole();
		//RoleInclusion newRoleInclusion = StageFactory.eINSTANCE.createRoleInclusion();
		HyName newName =  HyEvolutionFactory.eINSTANCE.createHyName();		
		newName.setName(text);		
	    newRole.getNames().add(newName);
	    //newRole.getInclusions().add(newRoleInclusion);
		stageModel.getRoles().add(newRole);
	}
	
	/**
	 * Function call to assign Stage to Role
	 * @param selectedRole
	 * @param selectedStage
	 */
	public void assignRoleToStage(Role selectedRole, Stage selectedStage){
		selectedRole.getAssignedTo().add(selectedStage);
	}
	
	/**
	 * Function call to unassign Role from Stage
	 * @param selectedRole
	 * @param selectedStage
	 */
	public void unassignRoleFromStage(Role selectedRole, Stage selectedStage){
		selectedRole.getAssignedTo().remove(selectedStage);
	}
	
	
	/**
	 * Function to Remove a Stage from the Model
	 * @param stage Selected Stage
	 */
	public void deleteStage(Stage stage){
		// Remove all assigned Roles		
		stageModel.getStages().remove(stage);
		stage.getHasAssigned().clear();
	}
	
	/**
	 * Function to Remove a Role from the Model
	 * @param role Selected Role
	 */
	public void deleteRole(Role role){
		// Remove all assigned Stages
		stageModel.getRoles().remove(role);
		role.getAssignedTo().clear();
	}
	/**
	 * Function to include a role
	 *  @param role Active Role
	 *  @param role Role to be included
	 */
	public void includeRole(Role role, Role includedRole){
		// TODO Alex: Add Evolution here, currently wrong
		
		RoleInclusion newInclusion;		

		if(role.getInclusions().get(0) == null){
			newInclusion = StageFactory.eINSTANCE.createRoleInclusion();	
			newInclusion.setInclusionsFor(role);
			role.getInclusions().add(newInclusion);			
		} else {
			newInclusion = role.getInclusions().get(0);
		}
		
		// Add new role to inclusion reference
		newInclusion.getIncludes().add(includedRole);		
		includedRole.getIncludedBy().add(newInclusion);		

		
	}
	
	/**
	 * Function to remove Role from inclusion
	 *  @param role Active Role
	 *  @param role Role to be included
	 */
	public void unincludeRole(Role role, Role unincludedRole){
		// Remove include from Role Inclusion
		role.getInclusions().get(0).getIncludes().remove(unincludedRole);
		
		// Remove reference to RoleInclsuion from unincludedRole
		unincludedRole.getIncludedBy().remove(role.getInclusions().get(0));
		
	}



}