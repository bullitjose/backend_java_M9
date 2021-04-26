package com.rockets.domain;

public class Propulsor   {
	
	private int potenciaMax;
	
	private int potenciaActual;
	
	public Propulsor(int potenciaMax) {
		this.potenciaMax=potenciaMax;
		potenciaActual=0;
	}

	public int getPotenciaMax() {
		return potenciaMax;
	}

	public void setPotenciaMax(int potenciaMax) {
		this.potenciaMax = potenciaMax;
	}

	public int getPotenciaActual() {
		return potenciaActual;
	}

	public void setPotenciaActual(int potenciaActual) {
		this.potenciaActual = potenciaActual;
	}

	
	


}
