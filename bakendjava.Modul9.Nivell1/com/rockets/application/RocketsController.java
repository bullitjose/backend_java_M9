package com.rockets.application;



import com.rockets.domain.Propulsor;
import com.rockets.domain.Rocket;
import com.rockets.repository.Repository;

public class RocketsController {

	private Repository repository;

	public RocketsController() {
		repository = new Repository();
	}

	public Repository createRocket(String nom, int propulsor1,
			int propulsor2, int propulsor3) throws Exception {
		// crear el primer coet
		Rocket rocket = new Rocket(nom, propulsor1, propulsor2,
				propulsor3);

		// afegir al repositori el nou coet
		repository.addRocket(rocket);
		
		

		// afegir al repositori els nous propulsors
		for (Propulsor p : rocket.getPropulsors()) {
			repository.addPropulsor(p);
		}

		return repository;
	}

	public Repository createRocket(String nom, int propulsor1,
			int propulsor2, int propulsor3, int propulsor4,
			int propulsor5, int propulsor6) throws Exception {

		// crear el segon coet
		Rocket rocket = new Rocket(nom, propulsor1, propulsor2,
				propulsor3, propulsor4, propulsor5, propulsor6);
		//afegir al repositori el nou coet
		repository.addRocket(rocket);

		// afegir al repositori els nous propulsors
		for (Propulsor p : rocket.getPropulsors()) {
			repository.addPropulsor(p);
		}
		
		return repository;
	}

	/**
	 * 
	 * @return string, amb tos els coets
	 * 
	 *         Metode que torna els coets que hi ha al repostori
	 */

	public StringBuilder getTotsCoets() {

	

		StringBuilder repositoryToString = new StringBuilder();

		for (Rocket rocket : repository.getAllRockets()) {

			repositoryToString.append(rocket.getNom() + " :llista de propulsors:"
					+ rocket.getAllPropulsors() + "\n");

		}
		return repositoryToString;
	}
	
	
	/**
	 * 
	 * @return string, amb tos els coets
	 * 
	 *         Metode que torna els coets que hi ha al repostori
	 */

	public StringBuilder getTotsPropulsors() {

		

		StringBuilder repositoryToString = new StringBuilder();

		for (Propulsor propulsor : repository.getAllPropulsors()) {

			repositoryToString.append("Potencia actual= "+propulsor.getPotenciaActual()+
					"Potencia Maxima= "+propulsor.getPotenciaMax()+"\n");

		}
		return repositoryToString;
	}
	
	
	
}
