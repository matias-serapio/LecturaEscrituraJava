package ar.edu.fiuba;

import java.util.ArrayList;

class Estudiante {
	private Integer legajo;
	private String nombreApellido;
	private ArrayList<String> materiasAprobadas;

	public Estudiante(Integer legajo, String nombreApellido) {
		this.legajo = legajo;
		this.nombreApellido = nombreApellido;
		this.materiasAprobadas = new ArrayList<>();
	}

	public int getLegajo() {
		return legajo;
	}

	public String getNombreApellido() {
		return nombreApellido;
	}

	public ArrayList<String> getMateriasAprobadas() {
		return materiasAprobadas;
	}

	public void agregarMateriaAprobada(String materia) {
		materiasAprobadas.add(materia);
	}

	@Override
	public String toString() {
		String result = legajo + " - " + nombreApellido + " - ";
		if (!materiasAprobadas.isEmpty()) {
			result += materiasAprobadas.get(0);
			for (int i = 1; i < materiasAprobadas.size(); i++) {
				result += ", " + materiasAprobadas.get(i);
			}
		}
		return result;
	}
}