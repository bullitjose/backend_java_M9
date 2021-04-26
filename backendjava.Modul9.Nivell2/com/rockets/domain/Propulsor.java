package com.rockets.domain;

public class Propulsor {

	private int potenciaMax;

	private int potenciaActual;

	private boolean ple = false;// el propulsor esta maxima capacitat
	
	private boolean buit=false;// el propulsor esta buit

	public Propulsor(int potenciaMax) {
		this.potenciaMax = potenciaMax;

	}

	public boolean isPle() {
		return ple;
	}

	public void setPle(boolean estaPle) {
		this.ple = estaPle;
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

	public boolean isBuit() {
		return buit;
	}

	public void setBuit(boolean buit) {
		this.buit = buit;
	}

}
