package com.rockets.repository;

import java.util.ArrayList;
import java.util.List;

import com.rockets.domain.Propulsor;
import com.rockets.domain.Rocket;

public class Repository {

	private static List<Rocket> rockets = new ArrayList<>();
	private static List<Propulsor> propulsors = new ArrayList<>();

	public Repository() {

	}


	public void addRocket(Rocket rocket) throws Exception {
		if (rocket == null)	throw new Exception();

		rockets.add(rocket);
	}
	/**
	 * 
	 * @param propulsor
	 * @throws Exception
	 * 
	 * Metode per afegir propulsor a la llista de propulsors que fa de repository
	 */
	public void addPropulsor(Propulsor propulsor) throws Exception {
		if (propulsor == null)throw new Exception();

		propulsors.add(propulsor);
	}

	/**
	 * 
	 * @return List<Rocket>, torna llista de tots els coets
	 */
	public List<Rocket> getAllRockets() {
		return new ArrayList<>(rockets);
	}
	/**
	 * 
	 * @return List<Propulsor>, torna llista de tots els propulsors
	 */
	public List<Propulsor> getAllPropulsors() {
		return new ArrayList<>(propulsors);
	}

	public Propulsor getPropulsor(int nombrePropulsor) {

		return propulsors.get(nombrePropulsor);
	}




	/**
	 * 
	 * @return string, amb tos els coets
	 * 
	 *         Metode que torna els coets que hi ha al repostori
	 */

	public StringBuilder getTotsPropulsors() {
//Variable que suma les potencies maximes del propulsors del coet

		int i=0;
		StringBuilder repositoryToString = new StringBuilder();

		for (Propulsor propulsor : propulsors) {

			repositoryToString.append("\tPropulsor "+i+" Potencia Actual= "+propulsor.getPotenciaActual()+
					", Potencia Maxima= "+propulsor.getPotenciaMax()+" \n");
			i++;
			
			
		}
		
		return repositoryToString;
	}
	
		
	

	/**
	 * 
	 * @return string, amb tos els coets
	 * 
	 *         Metode que torna els coets que hi ha al repostori
	 */

	public StringBuilder getTotsCoets() {



		StringBuilder repositoryToString = new StringBuilder();

		for (Rocket rocket : rockets) {

			repositoryToString.append("Llista de propulsors del coet "+rocket.getNom()+"\n"
					+ rocket.getAllPropulsors() + " \n");

		}
		return repositoryToString;
	}

	/**
	 * 
	 * @param inputPosicio
	 * @return retorna nom coet de rockets(inputPosicio)
	 * 
	 * 
	 */
	public StringBuilder getNomCoet(int inputPosicio) {
		StringBuilder repositoryToString = new StringBuilder();
		return repositoryToString.append(rockets.get(inputPosicio).getNom());
	}


	public Rocket getRocket(int inputPosicio) {
		// TODO Auto-generated method stub
		return rockets.get(inputPosicio);
	}



}


