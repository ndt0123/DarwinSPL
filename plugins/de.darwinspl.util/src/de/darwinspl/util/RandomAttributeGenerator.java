package de.darwinspl.util;

import java.util.Date;
import java.util.Random;

import eu.hyvar.dataValues.HyDataValuesFactory;
import eu.hyvar.dataValues.HyEnum;
import eu.hyvar.dataValues.HyEnumLiteral;
import eu.hyvar.evolution.HyName;
import eu.hyvar.feature.HyBooleanAttribute;
import eu.hyvar.feature.HyEnumAttribute;
import eu.hyvar.feature.HyFeature;
import eu.hyvar.feature.HyFeatureAttribute;
import eu.hyvar.feature.HyFeatureFactory;
import eu.hyvar.feature.HyFeatureModel;
import eu.hyvar.feature.HyNumberAttribute;
import eu.hyvar.feature.util.HyFeatureCreationUtil;

public class RandomAttributeGenerator {
	
	private HyFeatureModel featureModel;
	private int numberOfFeatureAttributes;
	private int numberOfFeatureModelAttributes;
	private Date validSince;
	private Date validUntil;
	
	private int attributeNr = 0;
	private int enumNr = 0;
	
	public RandomAttributeGenerator(HyFeatureModel featureModel, int numberOfFeatureAttributes, int numberOfFeatureModelAttributes, Date validSince, Date validUntil) {
		this.featureModel = featureModel;
		this.numberOfFeatureAttributes = numberOfFeatureAttributes;
		this.numberOfFeatureModelAttributes = numberOfFeatureModelAttributes;
		this.validSince = validSince;
		this.validUntil = validUntil;
	}
	
	public HyFeatureModel generateAttributes() {
		// generate feature attributes
		for (HyFeature feature : featureModel.getFeatures()) {
			for (int i = 0; i < numberOfFeatureAttributes; i++) {
				feature.getAttributes().add(createRandomAttribute());
			}
		}

		// generate feature model attributes
		for (int i = 0; i < numberOfFeatureModelAttributes; i++) {
			HyFeatureAttribute attribute = createRandomAttribute();
			for (HyFeature feature : featureModel.getFeatures()) {
				feature.getAttributes().add(cloneAttribute(attribute));
			}
		}
		return featureModel;
	}
	
	private HyFeatureAttribute cloneAttribute(HyFeatureAttribute attribute) {
		HyFeatureAttribute clone = null;
		if(attribute instanceof HyNumberAttribute) {
			clone = HyFeatureFactory.eINSTANCE.createHyNumberAttribute();
			((HyNumberAttribute)clone).setMin(((HyNumberAttribute)attribute).getMin());
			((HyNumberAttribute)clone).setMax(((HyNumberAttribute)attribute).getMax());
			((HyNumberAttribute)clone).setDefault(((HyNumberAttribute)attribute).getDefault());
		} else if (attribute instanceof HyEnumAttribute) {
			clone = HyFeatureFactory.eINSTANCE.createHyEnumAttribute();
			((HyEnumAttribute)clone).setEnumType(((HyEnumAttribute)attribute).getEnumType());
		} else if (attribute instanceof HyBooleanAttribute) {
			clone = HyFeatureFactory.eINSTANCE.createHyBooleanAttribute();
		}
		
		HyName name = HyFeatureCreationUtil.createName(attribute.getNames().get(0).getName(), validSince, validUntil, null);	
		clone.getNames().add(name);
		return clone;
	}

	private HyFeatureAttribute createRandomAttribute() {
		HyFeatureAttribute attribute = null;
		HyName name = HyFeatureCreationUtil.createName("attribute " + attributeNr++, validSince, validUntil, null);
		switch (new Random().nextInt(3)) {
		case 0:
			attribute = HyFeatureCreationUtil.createBooleanAttribute(validSince, validUntil, name);
			break;
//		case 1:
//			attribute = HyFeatureCreationUtil.createEnumAttribute(validSince, validUntil, name);
//			HyEnum hyEnum = createHyEnum();
//			featureModel.getEnums().add(hyEnum);
//			((HyEnumAttribute)attribute).setEnumType(hyEnum);
//			break;
		case 2:
		default:
			attribute = HyFeatureCreationUtil.createNumberAttribute(name, validSince, validUntil);
			
			int min = new Random().nextInt(1000);
			int max = new Random().nextInt(1000) + min;
			int def = new Random().nextInt(max - min + 1) + min;
			
			((HyNumberAttribute)attribute).setMin(min);
			((HyNumberAttribute)attribute).setMax(max);
			((HyNumberAttribute)attribute).setDefault(def);

			break;
		}
		return attribute;
	}
	
	private HyEnum createHyEnum() {
		HyEnum hyEnum = HyDataValuesFactory.eINSTANCE.createHyEnum();
		hyEnum.setValidSince(validSince);
		hyEnum.setValidUntil(validUntil);
		hyEnum.setName("Enum " + ++enumNr);
	
		
		HyEnumLiteral lit1 = HyDataValuesFactory.eINSTANCE.createHyEnumLiteral();
		HyEnumLiteral lit2 = HyDataValuesFactory.eINSTANCE.createHyEnumLiteral();
		HyEnumLiteral lit3 = HyDataValuesFactory.eINSTANCE.createHyEnumLiteral();

		lit1.setEnum(hyEnum);
		lit1.setName("Enum " + enumNr + " lit 1");
		lit1.setValidSince(validSince);
		lit1.setValidUntil(validUntil);
		

		lit2.setEnum(hyEnum);
		lit2.setName("Enum " + enumNr + " lit 2");
		lit2.setValidSince(validSince);
		lit2.setValidUntil(validUntil);

		lit3.setEnum(hyEnum);
		lit3.setName("Enum " + enumNr + " lit 3");
		lit3.setValidSince(validSince);
		lit3.setValidUntil(validUntil);
		
		return hyEnum;
	}

}