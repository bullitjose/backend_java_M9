package com.rockets.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

		JLabel l10, l11, l21, l20, l4;

		JTextField tf10, tf11, tf20, tf21;

		int sumaTotalPotencia1 = 0;// suma potencies totals dels propulsors del coet1
		int sumaTotalPotencia2 = 0;// suma potencies totals dels propulsors del coet2

		int potenciaObjectiu1 = 0;// Potencia objectiu del coet1
		int potenciaObjectiu2 = 0;// Potencia objectiu del coet2

		// threads, array de 10 Thread
		Thread[] threads = new Thread[9];

		protected Repository repository;

		protected Propulsor propulsor;

		private Lock cierreThread = new ReentrantLock();// variable de bloqueig de threads

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

			// setBounds(int x-coordinate, int y-coordinate, int width, int height)
			setBounds(300, 400, 850, 200);

			setTitle(
					"Panel de control Coet1 i Coet2 (Increment potencia +/- 1)");

			JPanel laminaBotones = new JPanel();
			JPanel laminaText1 = new JPanel();
			JPanel laminaText2 = new JPanel();

			// Definir i afegir labels botons entrada velocitat objectiu i velocitat inicial

			l10 = new JLabel("Introdueix: Velocitat inicial coet "
					+ repository.getNomCoet(0));

			tf10 = new JTextField(5);

			l11 = new JLabel("Velocitat objectiu ");

			tf11 = new JTextField(5);

			c1 = new JButton("Confirma potencia");// boto per confirmar entrada potencia

			c1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evento) {

					try {

						String captura10 = tf10.getText();
						int vInicial = Integer.parseInt(captura10);
						String captura11 = tf11.getText();
						int vObjectiu = Integer.parseInt(captura11);

						// Calcul de PT per assolir velocitat objectiu de vObjectiu
						// formula calcul velocitat, vObjectiu=vInicial+100(Math.sqrt(PT))
						// per calcular la potencia total necessaria, pt =
						// Math.pow(((vObjectiu-vInicial)/100), 2)

						double pt = Math.pow(
								((vObjectiu - vInicial) / 100), 2);

						// passar potenciaObjectiu a int
						potenciaObjectiu1 = (int) Math.round(pt);

						System.out.println("vinicial= " + vInicial
								+ ", vObjectiu= " + vObjectiu
								+ ", potenciaObectiu1="
								+ potenciaObjectiu1);

						// Comprovar si supera potencia maxima disponible pel coet
						if (potenciaObjectiu1 > repository
								.getRocket(0).getPotenciaTotal()) {

							l10.setText("Limit PT del coet es"
									+ repository.getRocket(0)
											.getPotenciaTotal()
									+ ",no pot arribar a "
									+ potenciaObjectiu1);

						}
						if (potenciaObjectiu1 <= repository
								.getRocket(0).getPotenciaTotal()) {

							l10.setText(
									"Potencia valida. Pots accelerar fins "
											+ potenciaObjectiu1);

							accelera1.setEnabled(true);
						}
						if (potenciaObjectiu1 <= 0) {

							l10.setText(
									"Torna a introduir noves dades");

						}

					} catch (NumberFormatException ex) {

						System.out.println(
								"Torna a introduir la potencia "
										+ ex);
					}
				}
			});

			laminaText1.add(l10);
			laminaText1.add(tf10);
			laminaText1.add(l11);
			laminaText1.add(tf11);
			laminaText1.add(c1);
			add(laminaText1, BorderLayout.NORTH);

			l20 = new JLabel("Introdueix:Velocitat inicial coet "
					+ repository.getNomCoet(1));

			tf20 = new JTextField(5);

			l21 = new JLabel("Velocitat objectiu ");

			tf21 = new JTextField(5);

			c2 = new JButton("Confirma potencia");

			c2.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent evento) {

					try {

						String captura20 = tf20.getText();
						int vInicial = Integer.parseInt(captura20);

						String captura21 = tf21.getText();
						int vObjectiu = Integer.parseInt(captura21);

						// Calcul de PT per assolir velocitat objectiu de vObjectiu
						// formula calcul velocitat, vObjectiu=vInicial+100(Math.sqrt(PT))
						// per calcular la potencia total necessaria, pt =
						// Math.pow(((vObjectiu-vInicial)/100), 2)

						double pt = Math.pow(
								((vObjectiu - vInicial) / 100), 2);

						// passar potenciaObjectiu a int
						potenciaObjectiu2 = (int) Math.round(pt);

						// Comprovar si supera potencia maxima disponible pel coet
						if (potenciaObjectiu2 > repository
								.getRocket(1).getPotenciaTotal()
								|| potenciaObjectiu2 <= 0) {

							l20.setText("Limit PT del coet es"
									+ repository.getRocket(1)
											.getPotenciaTotal()
									+ ", no pot arribar a "
									+ potenciaObjectiu2);

						}
						if (potenciaObjectiu2 <= repository
								.getRocket(1).getPotenciaTotal()) {

							l20.setText(
									"Potencia valida. Pots accelerar fins "
											+ potenciaObjectiu2);

							accelera2.setEnabled(true);
						}

					} catch (NumberFormatException ex) {

						System.out.println(
								"Torna a introduir la potencia "
										+ ex);
					}
				}
			});

			laminaText2.add(l20);
			laminaText2.add(tf20);
			laminaText2.add(l21);
			laminaText2.add(tf21);
			laminaText2.add(c2);
			add(laminaText2, BorderLayout.CENTER);

			// -------------boton coet1--

			accelera1 = new JButton("Coet1-Inici/accelera ");

			accelera1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {

					if (potenciaObjectiu1 <= 0) {

						l10.setText("Introdueix potencia valida.");

					} else {

						accelerar(evento);
					}
				}
			});
			accelera1.setEnabled(false);
			laminaBotones.add(accelera1);

			// -------------boton frena coet1--
			frenar1 = new JButton("Frena coet 1 ");
			frenar1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					frenar(evento);
				}
			});
			frenar1.setEnabled(false);
			laminaBotones.add(frenar1);

			// -------------boton coet2--
			accelera2 = new JButton("Coet2-Inici/accelera ");
			accelera2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					accelerar(evento);
				}
			});
			accelera2.setEnabled(false);
			laminaBotones.add(accelera2);
			// -------------boton frena coet2--
			frenar2 = new JButton("Frena coet 2 ");
			frenar2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					frenar(evento);
				}
			});
			frenar2.setEnabled(false);
			laminaBotones.add(frenar2);

			add(laminaBotones, BorderLayout.SOUTH);
		}

		// Metode acelerar(), en aquest cas accelerar, a=1

		public void accelerar(ActionEvent e) { // Enviamos ActionEvent para reconocer que hilo comienza

			try {// try del InterruptedException per Thread.sleep(50)

				int a = 1;// en aquest cas acceleracio +1

				int inici = 0;// inici dels bucles pels diferents propulsors dun coet

				int fi = 0;// fi dels bucles pels diferents propulsors dun coet

				boolean control1 = true;

				boolean control2 = true;

				if (e.getSource().equals(accelera1)) {// identifica el coet 1

					inici = 0;

					fi = 3;

					while (control1) {

						for (int i = inici; i < fi; i++) {// recorrer tots els propulsors
							cierreThread.lock();// variable de bloqueig de threads,fins unlock
							// no permet entrar un nou thread
							try {
								// si la suma de les potencies del coets es == potencia objectiu surt
								if (sumaTotalPotencia1 == potenciaObjectiu1) {

									control1 = false;

									break;
								}

								if (sumaTotalPotencia1 <= potenciaObjectiu1) {

									if (repository.getPropulsor(i)
											.getPotenciaActual() <= repository
													.getPropulsor(i)
													.getPotenciaMax()) {

										// llencar tasca runnable si el coet no esta ple, isPle(false)
										if (!repository
												.getPropulsor(i)
												.isPle()) {

											crearThreads(i, a);

											System.out.println("Nom "
													+ threads[i]
															.getName()
													+ ". P Actual= "
													+ repository
															.getPropulsor(
																	i)
															.getPotenciaActual()
													+ " P Max.= "
													+ repository
															.getPropulsor(
																	i)
															.getPotenciaMax()
													+ " sumatotal= "
													+ sumaTotalPotencia1);
											// Controlar si un propulsor ja ha arribat al seu maxim,
											// ficar setPle(true)

											if (repository
													.getPropulsor(i)
													.getPotenciaActual() == repository
															.getPropulsor(
																	i)
															.getPotenciaMax()) {

												repository
														.getPropulsor(
																i)
														.setPle(true);
											}
										}

										Thread.sleep(50);
									}
								}

							} finally {

								cierreThread.unlock();// variable de desbloqueig de threads,fi unlock
								// permet entrar un nou thread
							}

						}
					}

					// Desactivar boto accelera1, activart boto frenar1

					sumaTotalPotencia1 = 0;

					accelera1.setEnabled(false);

					frenar1.setEnabled(true);
				}

				if (e.getSource().equals(accelera2)) {// identifica el coet 2

					inici = 3;

					fi = 9;

					while (control2) {

						for (int i = inici; i < fi; i++) {

							cierreThread.lock();// variable de bloqueig de threads,fins unlock
							// no permet entrar un nou thread

							try {
								// si la suma de les potencies del coets es == potencia objectiu surt
								if (sumaTotalPotencia2 == potenciaObjectiu2) {
									control2 = false;
									break;
								}
								if (sumaTotalPotencia2 <= potenciaObjectiu2) {
									if (repository.getPropulsor(i)
											.getPotenciaActual() <= repository
													.getPropulsor(i)
													.getPotenciaMax()) {
										// llencar tasca runnable si el coet no esta ple, isPle(false)
										if (!repository
												.getPropulsor(i)
												.isPle()) {

											crearThreads(i, a);

											System.out.println("Nom "
													+ threads[i]
															.getName()
													+ ". P Actual= "
													+ repository
															.getPropulsor(
																	i)
															.getPotenciaActual()
													+ " P Max.= "
													+ repository
															.getPropulsor(
																	i)
															.getPotenciaMax()
													+ " sumatotal= "
													+ sumaTotalPotencia2);
											// Controlar si un propulsor ja ha arribat al seu maxim,
											// ficar setPle(true)

											if (repository
													.getPropulsor(i)
													.getPotenciaActual() == repository
															.getPropulsor(
																	i)
															.getPotenciaMax()) {

												repository
														.getPropulsor(
																i)
														.setPle(true);
											}
										}

										Thread.sleep(50);
									}
								}

							} finally {

								cierreThread.unlock();// variable desbloqueig de threads,fi unlock
								// permet entrar un nou thread
							}

						}
					}

					// Desactivar boto accelerar2, activar boto frenar2
					sumaTotalPotencia2 = 0;

					frenar2.setEnabled(true);

					accelera2.setEnabled(false);
				}

			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}

		}

		// Metode frenar(), en aquest cas, a=-1
		public void frenar(ActionEvent e) {

			try {// try del InterruptedException per Thread.sleep(50)

				int a = -1;// en aquest cas acceleracio -1

				int inici = 0;// inici dels bucles pels diferents propulsors dun coet

				int fi = 0;// fi dels bucles pels diferents propulsors dun coet

				boolean control1 = true;

				boolean control2 = true;

				if (e.getSource().equals(frenar1)) {// 5 // identifica el coet 1

					inici = 0;

					fi = 3;

					while (control1) {// 4

						for (int i = inici; i < fi; i++) {// 3// recorrer tots els propulsors

							cierreThread.lock();// variable de bloqueig de threads,fins unlock
							// no permet entrar un nou thread

							try {
								// si els tres propulsors estan buits sortir
								if (repository.getPropulsor(0)
										.isBuit()
										&& repository.getPropulsor(1)
												.isBuit()
										&& repository.getPropulsor(2)
												.isBuit()) {

									sumaTotalPotencia1 = 0;

									control1 = false;

									break;
								}

								if (repository.getPropulsor(i)
										.getPotenciaActual() >= 0) {// 2

									// llencar tasca runnable si el coet no esta ple, isPle(false)
									if (!repository.getPropulsor(i)
											.isBuit()) {// 1

										crearThreads(i, -1);

										System.out.println("Nom "
												+ threads[i].getName()
												+ ". P Actual= "
												+ repository
														.getPropulsor(
																i)
														.getPotenciaActual()
												+ " P Max.= "
												+ repository
														.getPropulsor(
																i)
														.getPotenciaMax());

										// Controlar si un propulsor ja ha arribat al seu minim,
										// ficar setBuit(true)

										if (repository.getPropulsor(i)
												.getPotenciaActual() == 0) {

											repository.getPropulsor(i)
													.setBuit(true);
										}
									} // 1

									Thread.sleep(50);

								} // 2

							} finally {

								cierreThread.unlock();// variable de desbloqueig de threads,fi unlock
								// permet entrar un nou thread
							}

						} // 3
					} // 4
						// Desactivar boto accelera1 i frena1

					frenar1.setEnabled(false);

					accelera1.setEnabled(false);

				} // 5 el if

				if (e.getSource().equals(frenar2)) {// identifica el coet 2

					inici = 3;

					fi = 9;

					while (control2) {

						for (int i = inici; i < fi; i++) {// recorrer tots els propulsors

							cierreThread.lock();// variable de bloqueig de threads,fins unlock
							// no permet entrar un nou thread

							try {
								// si els tres propulsors estan buits sortir
								if (repository.getPropulsor(3)
										.isBuit()
										&& repository.getPropulsor(4)
												.isBuit()
										&& repository.getPropulsor(5)
												.isBuit()
										&& repository.getPropulsor(6)
												.isBuit()
										&& repository.getPropulsor(7)
												.isBuit()
										&& repository.getPropulsor(8)
												.isBuit()) {

									sumaTotalPotencia2 = 0;

									control2 = false;

									break;
								}

								if (repository.getPropulsor(i)
										.getPotenciaActual() >= 0) {

									// llencar tasca runnable si el coet no esta ple, isPle(false)
									if (!repository.getPropulsor(i)
											.isBuit()) {

										crearThreads(i, -1);

										System.out.println("Nom "
												+ threads[i].getName()
												+ ". P Actual= "
												+ repository
														.getPropulsor(
																i)
														.getPotenciaActual()
												+ " P Max.= "
												+ repository
														.getPropulsor(
																i)
														.getPotenciaMax());

										// Controlar si un propulsor ja ha arribat al seu minim,
										// ficar setBuit(true)

										if (repository.getPropulsor(i)
												.getPotenciaActual() == 0) {

											repository.getPropulsor(i)
													.setBuit(true);
										}
									}

									Thread.sleep(50);

								}

							} finally {

								cierreThread.unlock();// variable de desbloqueig de threads,fi unlock
								// permet entrar un nou thread
							}

						}
					}
					// Desactivar boto accelera2 i frena2

					frenar2.setEnabled(false);

					accelera2.setEnabled(false);
				}

			} catch (

			InterruptedException e1) {

				e1.printStackTrace();
			}

		}

		/**
		 * 
		 * @param i,posició         del thread a array de threads
		 * @param inputAcceleracio, + o - si es per accelerar o frenar
		 * 
		 *                          Metode que instancia diferents threads, que criden
		 *                          metode runnable, per fer la tasca d'accelerar fins
		 *                          les potencies maximes dels diferents propulsors
		 */
		private void crearThreads(int i, int inputAcceleracio) {

			// Instanciar classe contenidora de SetVelocitatController, Main
			Main clasePrincipal = new Main();

			// Instanciar la classe anidada al main, SetVelocitatController implementa
			// Runnable
			Main.SetVelocitatController r = clasePrincipal.new SetVelocitatController(
					repository.getPropulsor(i));

			// introduir acceleracio
			r.setAcceleracio(inputAcceleracio);

			// Main.SetVelocitatController(repository.getPropulsor(i));
			threads[i] = new Thread(r);

			// Nom del thread es posicio a larray
			threads[i].setName("Thread " + i);

			threads[i].start();

			if (repository.getPropulsor(i).getPotenciaActual() > 0) {

				if (i < 3) {

					sumaTotalPotencia1 = sumaTotalPotencia1
							+ inputAcceleracio;
				}

				if (i >= 3) {

					sumaTotalPotencia2 = sumaTotalPotencia2
							+ inputAcceleracio;
				}
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
			if (acceleracio > 0) {// tractar accelerar

				if (propulsor.getPotenciaActual() <= propulsor
						.getPotenciaMax()) {
					propulsor.setPotenciaActual(
							propulsor.getPotenciaActual()
									+ acceleracio);

				}
			}
			if (acceleracio < 0) {// tractar frenar
				if (propulsor.getPotenciaActual() >= 0) {

					propulsor.setPotenciaActual(
							propulsor.getPotenciaActual()
									+ acceleracio);

				}
			}
			if (acceleracio == 0) {// tractar acceleracio 0

			}
		}

	}

}
