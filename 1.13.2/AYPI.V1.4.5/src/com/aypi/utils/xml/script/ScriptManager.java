package com.aypi.utils.xml.script;

import java.util.ArrayList;

import com.aypi.Aypi;

public class ScriptManager {
	
	private ArrayList<Variable> vs;
	
	public ScriptManager() {
		this.vs = new ArrayList<Variable>();
	}
	
	public String compile(String code, int line) {
		Compile compile = new Compile(this, code, line);
		return compile.compile();
	}
	
	private boolean getBoolean(String value) {
		return value.equalsIgnoreCase("true");
	}
	
	//OperatorHelper
	
	public LogicOperator getBooleanOperator(String value) {
		return LogicOperator.getOperator(value);
	}
	
	public MathOperator getMathOperator(String value) {
		return MathOperator.getOperator(value);
	}
	
	public boolean isBooleanOperator(String value) {
		return LogicOperator.getOperator(value) != null;
	}
	
	public boolean isMathOperator(String value) {
		return MathOperator.getOperator(value) != null;
	}
	
	public boolean isMathCalculator(String value) {
		return isMathOperator(value) && !MathOperator.isComparator(value);
	}
	
	public boolean isMathComparator(String value) {
		return isMathOperator(value) && MathOperator.isComparator(value);
	}
	
	//TypeHelper
	
	public boolean isString(String value) {
		char[] tab = value.toCharArray();
		
		if (tab[0] == '\'') {
			if (tab[tab.length - 1] == '\'') {
				if (tab[tab.length - 2] != '\\') {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isBoolean(String value) {
		return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
	}
	
	public boolean isNumber(String value) {
		if (!value.equalsIgnoreCase("")) {
			char[] tab = value.toCharArray();
			for (int i = 0 ; i < tab.length ; i++) {
				if (!isNumberChar(tab[i])) {
					return false;
				}
			}
		}
		
		return true;
	}
	
		
	//code utils
	
	private boolean isNumberChar(char c) {
		return c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '.' || c == '-' || c == 'E';
	}
	
	public String charTabToString(char[] tab, int start, int end) {
		String string = "";
		for (int i = start ; i < end ; i++) {
			string+=tab[i];
		}
		return string;
	}

	public String fuseString(String last, String next, int line, String code) {
		
		if (isString(last) || isString(next)) {
			if (isString(last))
				last = charTabToString(last.toCharArray(), 0, last.toCharArray().length - 1);
			else
				last="'"+last;
			if (isString(next))
				next = charTabToString(next.toCharArray(), 1, next.toCharArray().length);
			else
				next+="'";
			return last+next;
		}
		Aypi.getErrorManager().getError(3).display(line, code);
		return null;
	}
	
	public String doCalcule(String last, String next, String op, int line, String code) {
		
		MathOperator mo = getMathOperator(op);
		if (mo == MathOperator.ADDITION)
			return ""+(Double.parseDouble(last) + Double.parseDouble(next));
		if (mo == MathOperator.SUBSTRACTION)
			return ""+(Double.parseDouble(last) - Double.parseDouble(next));
		if (mo == MathOperator.MULTIPLICATION)
			return ""+(Double.parseDouble(last) * Double.parseDouble(next));
		if (mo == MathOperator.DIVISION) {
			if (!next.equalsIgnoreCase("0"))
				return ""+(Double.parseDouble(last) / Double.parseDouble(next));
			else {
				Aypi.getErrorManager().getError(8).display(line, code);
				return null;
			}
		}
		else
			Aypi.getErrorManager().getError(7).display(line, code);
		return null;
	}
	
	public String doCompare(String last, String next, String word, int line, String code) {
		MathOperator mo = getMathOperator(word);
		if (mo == MathOperator.EQUALS)
			return ""+(Double.parseDouble(last) == Double.parseDouble(next));
		else if (mo == MathOperator.INFERIOR)
			return ""+(Double.parseDouble(last) < Double.parseDouble(next));
		else if (mo == MathOperator.SUPERIOR)
			return ""+(Double.parseDouble(last) > Double.parseDouble(next));
		else if (mo == MathOperator.INFERIOR_OR_EQUALS)
			return ""+(Double.parseDouble(last) <= Double.parseDouble(next));
		else if (mo == MathOperator.SUPERIOR_OR_EQUALS)
			return ""+(Double.parseDouble(last) >= Double.parseDouble(next));
		else
			Aypi.getErrorManager().getError(7).display(line, code);
		return null;
	}
	
	public String doCompareBoolean(String last, String next, String operator, int line, String code) {
		LogicOperator lo = LogicOperator.getOperator(operator);
		if (lo == LogicOperator.AND)
			return ""+(getBoolean(last) && getBoolean(next)); 
		else if (lo == LogicOperator.OR)
			return ""+(getBoolean(last) || getBoolean(next));
		else if (lo == LogicOperator.XOR)
			return ""+((getBoolean(last) && !(getBoolean(next)) || (!getBoolean(last) && getBoolean(next))));
		else
			Aypi.getErrorManager().getError(7).display(line, code);
		return null;
	}
	
	//VARIABLE
	
	public boolean isVariable(String name) {
		return getVariable(name) != null;
	}
	
	public Variable getVariable(String name) {
		
		for (Variable variable : vs) {
			if (variable.getName().equalsIgnoreCase(name)) {
				return variable;
			}
		}
		
		return null;
	}
	
	public void addVariable(Variable variable) {
		vs.add(variable);
	}
	
	public void removeVariable(Variable variable) {
		for (int i = 0 ; i < vs.size() ; i++) {
			if (vs.get(i).getName().equalsIgnoreCase(variable.getName())) {
				vs.remove(i);
				i--;
			}
		}
	}

	
	public ArrayList<Variable> getVariables() {
		return this.vs;
	}
	
}
