package com.aypi.utils.xml.script;

public enum MathOperator {
	
	ADDITION, SUBSTRACTION, MULTIPLICATION, DIVISION, 
	EQUALS, SUPERIOR, INFERIOR, SUPERIOR_OR_EQUALS, INFERIOR_OR_EQUALS,
	NULL;
	
	static MathOperator getOperator(String op) {
		if (op.equalsIgnoreCase("+"))
			return ADDITION;
		else if (op.equalsIgnoreCase("-"))
			return SUBSTRACTION;
		else if (op.equalsIgnoreCase("*"))
			return MULTIPLICATION;
		else if (op.equalsIgnoreCase("/"))
			return DIVISION;
		if (op.equalsIgnoreCase("=="))
			return EQUALS;
		else if (op.equalsIgnoreCase(">"))
			return SUPERIOR;
		else if (op.equalsIgnoreCase("<"))
			return INFERIOR;
		else if (op.equalsIgnoreCase(">="))
			return SUPERIOR_OR_EQUALS;
		else if (op.equalsIgnoreCase("<="))
			return INFERIOR_OR_EQUALS;
		else
			return NULL;
	}
	
	static boolean isComparator(MathOperator operator) {
		if (operator == ADDITION || operator == MathOperator.SUBSTRACTION || operator == MULTIPLICATION || operator == MathOperator.DIVISION)
			return false;
		else
			return true;
	}
	
	static boolean isComparator(String operator) {
		return isComparator(getOperator(operator));
	}

}
