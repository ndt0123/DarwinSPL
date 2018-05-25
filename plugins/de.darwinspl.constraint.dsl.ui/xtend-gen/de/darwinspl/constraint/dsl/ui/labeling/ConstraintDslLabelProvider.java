/**
 * generated by Xtext 2.12.0
 */
package de.darwinspl.constraint.dsl.ui.labeling;

import com.google.inject.Inject;
import de.darwinspl.ui.labeling.ExpressionDslLabelProvider;
import eu.hyvar.feature.constraint.HyConstraint;
import eu.hyvar.feature.constraint.HyConstraintModel;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
@SuppressWarnings("all")
public class ConstraintDslLabelProvider extends ExpressionDslLabelProvider {
  @Inject
  public ConstraintDslLabelProvider(final AdapterFactoryLabelProvider delegate) {
    super(delegate);
  }
  
  public String text(final HyConstraintModel constraintModel) {
    Object _text = super.text(constraintModel);
    String _plus = ("Constraint Model (" + _text);
    return (_plus + ")");
  }
  
  public String text(final HyConstraint constraint) {
    String label = "Constraint (ID: ";
    if ((constraint != null)) {
      String _label = label;
      String _id = constraint.getId();
      label = (_label + _id);
    }
    String _label_1 = label;
    label = (_label_1 + ")");
    return label;
  }
}
