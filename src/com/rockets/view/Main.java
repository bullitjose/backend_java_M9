package com.rockets.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.rockets.application.RocketsController;

import com.rockets.domain.*;

import com.rockets.repository.Repository;

public class Main {
	protected static RocketsController rocketsController = new RocketsController();
	protected static Repository repository;
	protected Propulsor propulsor;

	public static void main(String[] args) throws Exception {

		repository = rocketsController.createRocket("32WESSDS", 10,
				30, 80);

		repository = rocketsController.createRocket("LDSFJA32", 30,
				40, 50, 50, 30, 10);

		StringBuilder allCoets = repository.getTotsCoets();
		System.out.println("Coets: " + " \n" + allCoets + " \n");

		JFrame marco = new MarcControl(repository);

		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		marco.setVisible(true);
	}

	// marc, làmines i botons
	// ------------------------------------------------------------------------------

	static class MarcControl extends JFrame {

		JButton accelera1, accelera2, frenar1, frenar2, c1, c2;// definir els botons

		

		// threads, array de 10 Thread
		Thread[] threads = new Thread[9];

		protected Repository repository;

		protected Propulsor propulsor;

		protected MarcControl(Repository repository) {

			for (int i = 0; i < 3; i++) {// arrancar fils coet amb 3 propulsors


				// Instanciar classe contenidora de SetVelocitatController, Main
				Main clasePrincipal = new Main();

				// Instanciar la classe anidada al main, SetVelocitatController implementa
				// Runnable
				Main.SetVelocitatController r = clasePrincipal.new SetVelocitatController(
						repository.getPropulsor(i));

				
				threads[i] = new Thread(r);

				// Nom del thread es posicio a larray
				threads[i].setName("Thread " + i);
				threads[i].start();

			}
			for (int i = 3; i < 9; i++) {

				// arrancar fils coet amb 6 propulsors

				// Instanciar classe contenidora de SetVelocitatController, Main
				Main clasePrincipal = new Main();

				// Instanciar la classe anidada al main, SetVelocitatController implementa
				// Runnable
				Main.SetVelocitatController r = clasePrincipal.new SetVelocitatController(
						repository.getPropulsor(i));

				threads[i] = new Thread(r);

				// Nom del thread es posicio a larray
				threads[i].setName("Thread " + i);

				threads[i].start();

			}

			this.repository = repository;

			//setBounds(int x-coordinate, int y-coordinate, int width, int height)
			setBounds(300, 400, 650, 90);

			setTitle(
					"Panel de control Coet1 i Coet2 (Increment potencia +/- 1)");

			JPanel laminaBotones = new JPanel();
		


			// -------------boton coet1--

			accelera1 = new JButton("Coet1-Inici/accelera ");

			accelera1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					accelerar(evento);
				}
			});

			laminaBotones.add(accelera1);

			// -------------boton frena coet1--
			frenar1 = new JButton("Frena coet 1 ");
			frenar1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					frenar(evento);
				}
			});
			laminaBotones.add(frenar1);

			// -------------boton coet2--
			accelera2 = new JButton("Coet2-Inici/accelera ");
			accelera2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					accelerar(evento);
				}
			});
			laminaBotones.add(accelera2);
			// -------------boton frena coet2--
			frenar2 = new JButton("Frena coet 2 ");
			frenar2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					frenar(evento);
				}
			});
			laminaBotones.add(frenar2);

			
			add(laminaBotones,BorderLayout.CENTER);
		}

		// Metode acelerar(), en aquest cas accelerar, a=1
		public void accelerar(ActionEvent e) { // Enviamos ActionEvent para reconocer que hilo comienza

			if (e.getSource().equals(accelera1)) {// identifica el coet 1

				for (int i = 0; i < 3; i++) {
					crearThreads(i, 1);

				}
			}
			if (e.getSource().equals(accelera2)) {// identifica el coet 2

				for (int i = 3; i < 9; i++) {// Propulsors del coet 2, posicions 3 a la 8

					crearThreads(i, 2);

				}

			}

		}

		// Metode frenar(), en aquest cas, a=-1
		public void frenar(ActionEvent e) {

			if (e.getSource().equals(frenar1)) {// identifica el coet 1
				for (int i = 0; i < 3; i++) {
					crearThreads(i, -1);

				}
			}

			if (e.getSource().equals(frenar2)) {// identifica el coet 2

				for (int i = 3; i < 9; i++) {// Propulsors del coet 2, posicions 3 a la 8

					crearThreads(i, -2);
				}
			}

		}

		/**
		 * 
		 * @param i,                posició del thread a array de threads
		 * @param inputAcceleracio, + o - si es per accelerar o frenar
		 * 
		 *                          Metode que instancia diferents threads, que criden
		 *                          metode runnable, per fer la tasca d'accelerar fins
		 *                          les potencies maximes dels diferents propulsors
		 */
		private void crearThreads(int i, int inputAcceleracio) {
			// arrancar fils coet amb 3 propulsors

			// Instanciar classe contenidora de SetVelocitatController, Main
			Main clasePrincipal = new Main();

			// Instanciar la classe anidada al main, SetVelocitatController implementa
			// Runnable
			Main.SetVelocitatController r = clasePrincipal.new SetVelocitatController(
					repository.getPropulsor(i));
			// introduir acceleracio
			r.setAcceleracio(inputAcceleracio);

			// final SetVelocitatController r = new
			// Main.SetVelocitatController(repository.getPropulsor(i));
			threads[i] = new Thread(r);

			// Nom del thread es posicio a larray
			threads[i].setName("Thread " + i);
			threads[i].start();

			imprimirThreadNovaVelocitat(threads[i], i);
		}

		/**
		 * @param thread,   thread actual per imprimir informacio daquest
		 * @param i,posicio al arraylist de threads,tambe defineix a quin coet pertany
		 *                  el propulsor
		 * 
		 *                  Metode que treu per pantalla informacio del propulsor
		 */
		private void imprimirThreadNovaVelocitat(Thread thread,
				int i) {

			// int a = inputAcceleracio;

			// Imprimir informació fil
			if (i < 3) {
				System.out.printf(
						"\tCoet1--Propulsor= %s ,Potencia Actual= %d, Potencia Maxima= %d \n\n",
						thread.getName(),
						repository.getPropulsor(i)
						.getPotenciaActual(),
						repository.getPropulsor(i).getPotenciaMax());
			}
			if (i >= 3) {
				System.out.printf(
						"\tCoet2--Propulsor= %s ,Potencia Actual= %d, Potencia Maxima= %d \n\n",
						thread.getName(),
						repository.getPropulsor(i)
						.getPotenciaActual(),
						repository.getPropulsor(i).getPotenciaMax());
			}

		}

	}

	/**
	 * 
	 * Metode que implementa Runnable.
	 *
	 */
	public class SetVelocitatController implements Runnable {

		private Propulsor propulsor;
		private int acceleracio;

		public SetVelocitatController(Propulsor propulsor) {

			this.propulsor = propulsor;
			this.acceleracio = 0;

		}

		/**
		 * 
		 * @param acceleracio
		 * 
		 *                    Metode que permet canviar acceleracio en qualsevol moment
		 */
		public void setAcceleracio(int acceleracio) {
			this.acceleracio = acceleracio;
		}

		public int getAcceleracio() {
			return acceleracio;
		}

		@Override
		public void run() {

			int a = getAcceleracio();

			// inputVelocitat es negativa, frenar
			if (a < 0) {

				if (propulsor.getPotenciaActual() + a < 0) {
					// no podem baixar de 0
					propulsor.setPotenciaActual(0);
				} else {
					// restar a PotenciaActual la velocitat entrada
					propulsor.setPotenciaActual(
							propulsor.getPotenciaActual() + a);
				}

			}
			// inputVelocitat es positiva, incrementar velocitat
			if (a > 0) {

				// no es pot passar PotenciaMax
				if (propulsor.getPotenciaActual() + a > propulsor
						.getPotenciaMax()) {
					propulsor.setPotenciaActual(
							propulsor.getPotenciaMax());

				} else {
					// sumar a PotenciaActual la velocitat entrada
					propulsor.setPotenciaActual(
							propulsor.getPotenciaActual() + a);
				}

			}

		}

	}

}
