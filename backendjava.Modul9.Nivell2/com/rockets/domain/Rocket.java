package com.rockets.domain;

import java.util.ArrayList;
import java.util.List;

public class Rocket {

	protected List<Propulsor> propulsors = new ArrayList<>();

	protected String nom;

	protected Propulsor propulsor;

	private int potenciaTotal=0;//potencia Maxima del coet

	private int potenciaActualCoet=0;//potencia actual del coet



	public Rocket(String nom, int inputPropulsor1,
			int inputPropulsor2, int inputPropulsor3)
					throws Exception {

		this.nom = nom;

		propulsor = new Propulsor(inputPropulsor1);
		propulsors.add(propulsor);

		propulsor = new Propulsor(inputPropulsor2);
		propulsors.add(propulsor);

		propulsor = new Propulsor(inputPropulsor3);
		propulsors.add(propulsor);

	}

	public Rocket(String nom, int inputPropulsor1,
			int inputPropulsor2, int inputPropulsor3,
			int inputPropulsor4, int inputPropulsor5,
			int inputPropulsor6) throws Exception {

		this.nom = nom;

		propulsor = new Propulsor(inputPropulsor1);
		this.propulsors.add(propulsor);

		propulsor = new Propulsor(inputPropulsor2);
		this.propulsors.add(propulsor);

		propulsor = new Propulsor(inputPropulsor3);
		this.propulsors.add(propulsor);

		propulsor = new Propulsor(inputPropulsor4);
		this.propulsors.add(propulsor);

		propulsor = new Propulsor(inputPropulsor5);
		this.propulsors.add(propulsor);

		propulsor = new Propulsor(inputPropulsor6);
		this.propulsors.add(propulsor);

	}

	public List<Propulsor> getPropulsors() {
		return propulsors;
	}

	public void setPropulsors(ArrayList<Propulsor> propulsors) {
		this.propulsors = propulsors;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * 
	 * @return String, string amb tot els propulsors del coet
	 */
	public String getAllPropulsors() {

		StringBuilder llistaPropulsors = new StringBuilder();
		int i = 0;
		for (Propulsor propulsor : propulsors) {

			llistaPropulsors.append(
					"\tPropulsor(Thread) " + i + " Potencia Actual= "
							+ propulsor.getPotenciaActual()
							+ ", Potencia Maxima= "
							+ propulsor.getPotenciaMax() + "\n");

			//sumar potenciaMax de cada propulsor per calcular potenciaTotal del coet
			potenciaTotal =potenciaTotal+ propulsor.getPotenciaMax();
			
			i++;

		}

		//set potenciaTotal del coet
		setPotenciaTotal(potenciaTotal);

		return llistaPropulsors.toString();
	}

	private void setPotenciaTotal(int potenciaTotal) {
		// TODO Auto-generated method stub
		this.potenciaTotal = potenciaTotal;

	}
	public int getPotenciaActualCoet() {
		
		for (Propulsor propulsor : propulsors) {

			
			potenciaActualCoet=potenciaActualCoet+ propulsor.getPotenciaActual();
			

		}
		
		
		return potenciaActualCoet;
	}

	public void setPotenciaActualCoet(int potenciaActualCoet) {
		this.potenciaActualCoet = potenciaActualCoet;
	}


	public int getPotenciaTotal() {
		return potenciaTotal;
	}
	/**
	 * 
	 * @param propulsors
	 * 
	 *                   Metode que afegeix propulsors a coet
	 */

	public void addPropulsor(Propulsor propulsor) {
		this.propulsors.add(propulsor);
	}

	public Propulsor getPropulsor(int nombrePropulsor) {

		return propulsors.get(nombrePropulsor);
	}



}
