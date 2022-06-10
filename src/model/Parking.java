package model;

public class Parking {
	private int codeParking;
	private String nomParking;
	private int capaciteParking;
	private String rueParking;
	private String arrondissementParking;
	private int nombrePlaceVide;
	
	public Parking() {
		super();
	}

	public Parking(int codeParking, String nomParking, int capaciteParking, String rueParking,
			String arrondissementParking, int nombrePlaceVide) {
		super();
		this.codeParking = codeParking;
		this.nomParking = nomParking;
		this.capaciteParking = capaciteParking;
		this.rueParking = rueParking;
		this.arrondissementParking = arrondissementParking;
		this.nombrePlaceVide = nombrePlaceVide;
	}

	public Parking(String nomParking, int capaciteParking, String rueParking, String arrondissementParking, int nombrePlaceVide) {
		super();
		this.nomParking = nomParking;
		this.capaciteParking = capaciteParking;
		this.rueParking = rueParking;
		this.arrondissementParking = arrondissementParking;
		this.nombrePlaceVide = nombrePlaceVide;
	}

	public int getCodeParking() {
		return codeParking;
	}

	public void setCodeParking(int codeParking) {
		this.codeParking = codeParking;
	}

	public String getNomParking() {
		return nomParking;
	}

	public void setNomParking(String nomParking) {
		this.nomParking = nomParking;
	}

	public int getCapaciteParking() {
		return capaciteParking;
	}

	public void setCapaciteParking(int capaciteParking) {
		this.capaciteParking = capaciteParking;
	}

	public String getRueParking() {
		return rueParking;
	}

	public void setRueParking(String rueParking) {
		this.rueParking = rueParking;
	}

	public String getArrondissementParking() {
		return arrondissementParking;
	}

	public void setArrondissementParking(String arrondissementParking) {
		this.arrondissementParking = arrondissementParking;
	}

	public int getNombrePlaceVide() {
		return nombrePlaceVide;
	}

	public void setNombrePlaceVide(int nombrePlaceVide) {
		this.nombrePlaceVide = nombrePlaceVide;
	}
	
}
