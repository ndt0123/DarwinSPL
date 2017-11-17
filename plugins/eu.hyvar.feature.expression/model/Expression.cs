SYNTAXDEF hyexpression
FOR <http://hyvar-project.eu/feature/expression/1.0> <Expression.genmodel>
START HyExpression

IMPORTS {
	hydatavalue : <http://hyvar-project.eu/feature/dataValues/1.0> WITH SYNTAX expression <../../eu.hyvar.feature.data/model/DataValues.cs>
}

OPTIONS {
	reloadGeneratorModel = "false";
	usePredefinedTokens = "false";
	
	defaultTokenName = "IDENTIFIER_TOKEN";
	disableNewProjectWizard = "true";
	disableBuilder = "true";
	disableLaunchSupport = "true";
	disableDebugSupport = "true";
	
//	autofixSimpleLeftrecursion = "true";
}

//TOKENS {
//	DEFINE IDENTIFIER_TOKEN $('A'..'Z'|'a'..'z'|'_')('A'..'Z'|'a'..'z'|'_'|'0'..'9')*$;
//	
//	DEFINE SL_COMMENT $'//'(~('\n'|'\r'|'\uffff'))*$;
//	DEFINE ML_COMMENT $'/*'.*'*/'$;
//	
//	DEFINE LINEBREAK $('\r\n'|'\r'|'\n')$;
//	DEFINE WHITESPACE $(' '|'\t'|'\f')$;
//}

TOKENSTYLES  {
	"SL_COMMENT", "ML_COMMENT" COLOR #008000;
		
	"<->", "->", "||", "&&", "!", "/", "*", "%", "+", "-" COLOR #800040, BOLD;
	
	//"^" COLOR #800040, BOLD;
	"?" COLOR #800040, BOLD;
	
	"<", "<=", "=", ">", ">=" COLOR #800040, BOLD;
	"[", "]", "(", ")" COLOR #0000CC;
}

RULES {
	// Operator priorities: http://stackoverflow.com/questions/3114107/modulo-in-order-of-operation
	@Operator(type="binary_left_associative",  weight="0", superclass="HyExpression")
	HyEquivalenceExpression ::= operand1 "<->" operand2;
	
	@Operator(type="binary_left_associative", weight="1", superclass="HyExpression")
	HyImpliesExpression ::= operand1 "->" operand2;
	
	@Operator(type="binary_left_associative", weight="4", superclass="HyExpression")
	HyOrExpression ::= operand1 "||" operand2;
	
	@Operator(type="binary_left_associative", weight="5", superclass="HyExpression")
	HyAndExpression ::= operand1 "&&" operand2;
	
	@Operator(type="unary_prefix", weight="14", superclass="HyExpression")
	HyNotExpression ::= "!" operand;
	
	@Operator(type="unary_prefix", weight="14", superclass="HyExpression")
	HyNegationExpression ::= "-" operand;
	
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyNestedExpression ::= "(" operand ")";
	
	
	@SuppressWarnings(explicitSyntaxChoice)
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyFeatureReferenceExpression ::= (feature['"', '"'] | feature[]) (versionRestriction)?;
	
	@SuppressWarnings(explicitSyntaxChoice)
	@SuppressWarnings(minOccurenceMismatch)
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyConditionalFeatureReferenceExpression ::= "?" (feature['"', '"'] | feature[]) versionRestriction;	

	@SuppressWarnings(explicitSyntaxChoice)
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyContextInformationReferenceExpression ::= "context:" (contextInformation['"', '"'] | contextInformation[]);

	@SuppressWarnings(explicitSyntaxChoice)
	@SuppressWarnings(minOccurenceMismatch)
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyAttributeReferenceExpression ::= (feature['"', '"'] | feature[]) "." attribute[];
	
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyValueExpression ::= value;
	
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyBooleanValueExpression ::= value["true" : "false"];
	
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyMinimumExpression ::= "min(" operand ")";
	
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyMaximumExpression ::= "max(" operand ")";
	
	@Operator(type="primitive", weight="15", superclass="HyExpression")
	HyIfPossibleExpression ::= "ifPossible(" operands ("," operands)* ")";
	
	@Operator(type="binary_left_associative", weight="9", superclass="HyExpression")
	HyLessExpression ::= operand1 "<" operand2;
	
	@Operator(type="binary_left_associative", weight="9", superclass="HyExpression")
	HyLessOrEqualExpression ::= operand1 "<=" operand2;
	
	@Operator(type="binary_left_associative", weight="9", superclass="HyExpression")
	HyGreaterExpression ::= operand1 ">" operand2;
	
	@Operator(type="binary_left_associative", weight="9", superclass="HyExpression")
	HyGreaterOrEqualExpression ::= operand1 ">=" operand2;
	
	@Operator(type="binary_left_associative", weight="12", superclass="HyExpression")
	HySubtractionExpression ::= operand1 "-" operand2;
	
	@Operator(type="binary_left_associative", weight="12", superclass="HyExpression")
	HyAdditionExpression ::= operand1 "+" operand2;
	
	@Operator(type="binary_left_associative", weight="13", superclass="HyExpression")
	HyModuloExpression ::= operand1 "%" operand2;
	
	@Operator(type="binary_left_associative", weight="13", superclass="HyExpression")
	HyMultiplicationExpression ::= operand1 "*" operand2;
	
	@Operator(type="binary_left_associative", weight="13", superclass="HyExpression")
	HyDivisionExpression ::= operand1 "/" operand2;
	
	@Operator(type="binary_left_associative", weight="9", superclass="HyExpression")
	HyEqualExpression ::= operand1 "=" operand2;
	
	@Operator(type="binary_left_associative", weight="9", superclass="HyExpression")
	HyNotEqualExpression ::= operand1 "!=" operand2;
	
	
	HyRelativeVersionRestriction ::= "[" operator[lessThan : "<", lessThanOrEqual : "<=", equal : "=", implicitEqual : "", greaterThanOrEqual : ">=", greaterThan : ">"] version['"','"'] "]";
	HyVersionRangeRestriction ::= "[" lowerIncluded["" : "^"] lowerVersion['"','"'] "-" upperIncluded["" : "^"] upperVersion['"','"'] "]";
}
