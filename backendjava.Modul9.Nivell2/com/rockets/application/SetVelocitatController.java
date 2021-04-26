package com.rockets.application;

import com.rockets.domain.Propulsor;


public class SetVelocitatController implements Runnable {

	
	
	private Propulsor propulsor;
	private int acceleracio;

	
	  public SetVelocitatController(Propulsor propulsor) {
	 
	  this.propulsor=propulsor;
	  
	  }
	 
	public void setAcceleracio(int acceleracio) {
		this.acceleracio=acceleracio;
	}
		

	public int getAcceleracio() {
		return acceleracio;
	}

	@Override
	public void run() {
		
		int a=getAcceleracio();
		
		//inputVelocitat es negativa, frenar
		if(a<0) {
			
			System.out.println("velocitat negativa, v= "+a);
			
			if(propulsor.getPotenciaActual()+a<0) {
				//no baixar podem baixar de 0
				propulsor.setPotenciaActual(0);
			}else{
				//restar a PotenciaActual la velocitat entrada
				propulsor.setPotenciaActual(propulsor.getPotenciaActual()+a);
			}
			
		}
		//inputVelocitat es positiva, incrementar velocitat
		if(a>0) {
			
			System.out.println("velocitat positiva, v= "+a);
			
			//no es pot passar PotenciaMax
			if(propulsor.getPotenciaActual()+a>propulsor.getPotenciaMax()) {
				propulsor.setPotenciaActual(propulsor.getPotenciaMax());
				
			}else {
				//sumar a PotenciaActual la velocitat entrada
				propulsor.setPotenciaActual(propulsor.getPotenciaActual()+a);
			}
			
		}
	}

	

	
}
