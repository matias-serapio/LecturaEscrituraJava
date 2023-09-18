package ar.edu.fiuba;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		HashMap<Integer, Estudiante> estudiantes = new HashMap<>();
		Scanner scanner = new Scanner(System.in);
		boolean salir = false;

		try {
			BufferedReader br = new BufferedReader(new FileReader("aprobaciones.txt"));
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] partes = linea.split(",");
				if (partes.length != 3) {
					throw new IllegalArgumentException("Formato de línea incorrecto: " + linea);
				}
				int legajo = Integer.parseInt(partes[0]);
				String nombreApellido = partes[1];
				String materia = partes[2];

				if (!estudiantes.containsKey(legajo)) {
					Estudiante estudiante = new Estudiante(legajo, nombreApellido);
					estudiantes.put(legajo, estudiante);
				}

				estudiantes.get(legajo).agregarMateriaAprobada(materia);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("El archivo aprobaciones.txt no se encontró.");
		} catch (IOException e) {
			System.out.println("Error al leer el archivo.");
		} catch (NumberFormatException e) {
			System.out.println("Error al convertir legajo a número.");
		} catch (IllegalArgumentException e) {
			System.out.println("Error en el formato de línea.");
		}

		while (!salir) {
			System.out.println("\nMenú:");
			System.out.println("1. Buscar estudiante por legajo");
			System.out.println("2. Salir");
			System.out.print("Seleccione una opción: ");

			try {
				int opcion = scanner.nextInt();
				scanner.nextLine(); // Consumir la línea en blanco después de nextInt()

				switch (opcion) {
				case 1:
					boolean respuestaValida = false;
					boolean legajoValido = false;

					while (!legajoValido) {
						try {
							System.out.print("Ingrese el número de legajo a buscar: ");
							int legajoBuscado = scanner.nextInt();
							scanner.nextLine(); // Consumir la línea en blanco después de nextInt()

							Estudiante estudianteEncontrado = estudiantes.get(legajoBuscado);
							if (estudianteEncontrado != null) {
								System.out.println("Estudiante encontrado:");
								System.out.println(estudianteEncontrado);

								respuestaValida = false; // Reiniciar respuestaValida

								while (!respuestaValida) {
									try {
										System.out.println("¿Desea agregar una materia aprobada?");

										String respuesta = scanner.nextLine();

										if (respuesta.equalsIgnoreCase("SI") || respuesta.equalsIgnoreCase("S")) {

											System.out.print("Ingrese el nombre y apellido del nuevo estudiante: ");
											String nuevoNombreApellido = scanner.nextLine();
											System.out.print("Ingrese la materia aprobada para el nuevo estudiante: ");
											String nuevaMateria = scanner.nextLine();

											Estudiante nuevoEstudiante = new Estudiante(legajoBuscado,
													nuevoNombreApellido);
											nuevoEstudiante.agregarMateriaAprobada(nuevaMateria);
											estudiantes.put(legajoBuscado, nuevoEstudiante);
											System.out.println("Nuevo estudiante agregado con éxito.");
											respuestaValida = true;

											try (FileWriter fw = new FileWriter("aprobaciones.txt", true)) {
												fw.write(legajoBuscado + "," + nuevoNombreApellido + "," + nuevaMateria
														+ "\n");
												fw.close();
												System.out.println("Nuevo estudiante agregado al archivo.");
											} catch (IOException e) {
												System.out.println("Error al escribir en el archivo.");
											}
										} else if (respuesta.equalsIgnoreCase("NO")
												|| respuesta.equalsIgnoreCase("N")) {

											respuestaValida = true;
										} else {
											throw new IllegalArgumentException(
													"Respuesta no válida. Por favor, responda con 'Sí' o 'No'.");
										}

									} catch (IllegalArgumentException e) {
										System.out.println("Respuesta Incorrecta.");
									}
								}
							} else {
								System.out.println("Estudiante no encontrado.");

								respuestaValida = false; // Reiniciar respuestaValida

								while (!respuestaValida) {
									try {
										System.out.println("¿Desea agregar una materia aprobada?");

										String respuesta = scanner.nextLine();

										if (respuesta.equalsIgnoreCase("SI") || respuesta.equalsIgnoreCase("S")) {

											System.out.print("Ingrese el nombre y apellido del nuevo estudiante: ");
											String nuevoNombreApellido = scanner.nextLine();
											System.out.print("Ingrese la materia aprobada para el nuevo estudiante: ");
											String nuevaMateria = scanner.nextLine();

											Estudiante nuevoEstudiante = new Estudiante(legajoBuscado,
													nuevoNombreApellido);
											nuevoEstudiante.agregarMateriaAprobada(nuevaMateria);
											estudiantes.put(legajoBuscado, nuevoEstudiante);
											System.out.println("Nuevo estudiante agregado con éxito.");
											respuestaValida = true;

											try (FileWriter fw = new FileWriter("aprobaciones.txt", true)) {
												fw.write(legajoBuscado + "," + nuevoNombreApellido + "," + nuevaMateria
														+ "\n");
												fw.close();
												System.out.println("Nuevo estudiante agregado al archivo.");
											} catch (IOException e) {
												System.out.println("Error al escribir en el archivo.");
											}
										} else if (respuesta.equalsIgnoreCase("NO")
												|| respuesta.equalsIgnoreCase("N")) {

											respuestaValida = true;
										} else {
											throw new IllegalArgumentException(
													"Respuesta no válida. Por favor, responda con 'Sí' o 'No'.");
										}
									} catch (IllegalArgumentException e) {
										System.out.println("Respuesta Incorrecta.");
									}
								}
							}
							legajoValido = true;
						} catch (InputMismatchException e) {
							System.out.println("Error al ingresar el legajo. Se esperaba un número entero.");
							scanner.nextLine(); // Limpiar el buffer del teclado
						}
					}
					break;
				case 2:
					salir = true;
					break;
				default:
					System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Error al ingresar la opción. Se esperaba un número entero.");
				scanner.nextLine(); // Limpiar el buffer del teclado
			}
		}

		System.out.println("Programa finalizado.");
		scanner.close();
	}
}
