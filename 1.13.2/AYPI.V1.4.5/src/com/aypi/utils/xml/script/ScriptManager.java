package com.aypi.utils.xml.script;

import java.util.ArrayList;

public class ScriptManager {
	
	private ArrayList<Variable> vs;
	
	public ScriptManager() {
		this.vs = new ArrayList<Variable>();
		
		//test
	}
	
	//NUMBER
	public double product(Variable v1, Variable v2) {
		double product = 0;
		if (v1.getType() == VariableType.NUMBER && v2.getType() == VariableType.NUMBER) {
			product = Double.parseDouble(v1.getValue()) + Double.parseDouble(v2.getValue());
		} else {
			System.out.println("Error operator "+v1.getType()+" "+v2.getType()+" not compatible");
		}
		return product;
	}
	
	public double difference(Variable v1, Variable v2) {
		double difference = 0;
		if (v1.getType() == VariableType.NUMBER && v2.getType() == VariableType.NUMBER) {
			difference = Double.parseDouble(v1.getValue()) - Double.parseDouble(v2.getValue());
		} else {
			System.out.println("Error operator "+v1.getType()+" "+v2.getType()+" not compatible");
		}
		return difference;
	}
	
	public double multiplie(Variable v1, Variable v2) {
		double multiplie = 0;
		if (v1.getType() == VariableType.NUMBER && v2.getType() == VariableType.NUMBER) {
			multiplie = Double.parseDouble(v1.getValue()) * Double.parseDouble(v2.getValue());
		} else {
			System.out.println("Error operator "+v1.getType()+" "+v2.getType()+" not compatible");
		}
		return multiplie;
	}
	
	public double divide(Variable v1, Variable v2) {
		double multiplie = 0;
		if (v1.getType() == VariableType.NUMBER && v2.getType() == VariableType.NUMBER) {
			multiplie = Double.parseDouble(v1.getValue()) / Double.parseDouble(v2.getValue());
		} else {
			System.out.println("Error operator "+v1.getType()+" "+v2.getType()+" not compatible");
		}
		return multiplie;
	}
	
	//BOOLEAN
	public boolean logicOperator(Variable v1, Variable v2, LogicOperator logicOperator) {
		boolean value = false;
		
		if (v1.getType() == VariableType.BOOLEAN && v2.getType() == VariableType.BOOLEAN) {
			
			value = logicOperator(Boolean.getBoolean(v1.getValue()), Boolean.getBoolean(v2.getValue()), logicOperator);
			
		} else {
			System.out.println("Error type:"+v1.getType()+" "+v2.getType()+"not aplicable for "+v1.getName()+" "+v2.getName());
		}
		
		return value;
	}
	
	public boolean logicOperator(boolean b1, boolean b2, LogicOperator logicOperator) {
		boolean value = false;
		
		if (logicOperator == LogicOperator.AND) {
			value = b1 && b2; 
		} else if (logicOperator == LogicOperator.OR) {
			value = b1 || b2;
		} else {
			System.out.println("Error logic operator "+logicOperator+" not exist");
		}
		
		return value;
	}
	
	//STRING
	
	
	
	public ArrayList<Variable> getVariables() {
		return this.vs;
	}
	
	public boolean compileCodeBooleanValue(String code) {
		System.out.println("[DEBUG] "+code);
		String[] args = code.split(" ");
		
		boolean finalBool = true;
		LogicOperator lo = LogicOperator.AND;
		
		for (int i = 0 ; i < args.length ; i++) {
			
			if (args[i].equalsIgnoreCase("true") || args[i].equalsIgnoreCase("false")) {
				System.out.println("[DEBUG] "+finalBool+" "+lo+" "+stringToBool(args[i])+" = "+logicOperator(finalBool, stringToBool(args[i]), lo));
				finalBool = logicOperator(finalBool, stringToBool(args[i]), lo);
				lo = LogicOperator.NULL;
			} else if (isLogicOperator(args[i])) {
				
				LogicOperator l = LogicOperator.valueOf(args[i]);
				if (l == LogicOperator.NOT) {
					if (i+1 < args.length && (isVariable(args[i+1]) || (args[i].equalsIgnoreCase("true") || args[i].equalsIgnoreCase("false")))) {
						args[i+1] = ""+!stringToBool(args[i+1]);
					} else {
						System.out.println("Error: the argument NOT need a coorect value...");
					}
				} else {
					lo = LogicOperator.valueOf(args[i]);
				}
				
			}
			
		}
		
		return finalBool;
		
	} 
	
	boolean stringToBool(String bool) {
		if (bool.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}
	
	boolean isLogicOperator(String name) {
		LogicOperator lo = LogicOperator.getOperator(name);
		if (lo == LogicOperator.NULL) {
			return false;
		}
		return true;
	}
	
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
	
}