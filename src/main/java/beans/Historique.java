package beans;

import java.sql.Date;

public class Historique {
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getScoreTotal() {
		return scoreTotal;
	}
	public void setScoreTotal(int scoreTotal) {
		this.scoreTotal = scoreTotal;
	}
	public int getNbPartie() {
		return nbPartie;
	}
	public void setNbPartie(int nbPartie) {
		this.nbPartie = nbPartie;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	String nom ;
	int scoreTotal ;
	int nbPartie ;
	Date date ;
}
