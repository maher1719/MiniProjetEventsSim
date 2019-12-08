package nalouti.raoudha.monprojetevent;

import java.util.Date;

public class EventData {
    int idEvent, duree;
    Date dateAjout;
    String userMail, lieuEvent, timeFinEvent,
            dateFin, timeDebEvent, dateDebEvent, capacite,
            description, montant, imgEvent, type, categorie, titre;

    public EventData(int duree, Date dateAjout, String userMail, String lieuEvent, String timeFinEvent, String dateFin, String timeDebEvent, String dateDebEvent, String capacite, String description, String montant, String imgEvent, String type, String categorie, String titre) {

        this.duree = duree;
        this.dateAjout = dateAjout;
        this.userMail = userMail;
        this.lieuEvent = lieuEvent;
        this.timeFinEvent = timeFinEvent;
        this.dateFin = dateFin;
        this.timeDebEvent = timeDebEvent;
        this.dateDebEvent = dateDebEvent;
        this.capacite = capacite;
        this.description = description;
        this.montant = montant;
        this.imgEvent = imgEvent;
        this.type = type;
        this.categorie = categorie;
        this.titre = titre;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getLieuEvent() {
        return lieuEvent;
    }

    public void setLieuEvent(String lieuEvent) {
        this.lieuEvent = lieuEvent;
    }

    public String getTimeFinEvent() {
        return timeFinEvent;
    }

    public void setTimeFinEvent(String timeFinEvent) {
        this.timeFinEvent = timeFinEvent;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getTimeDebEvent() {
        return timeDebEvent;
    }

    public void setTimeDebEvent(String timeDebEvent) {
        this.timeDebEvent = timeDebEvent;
    }

    public String getDateDebEvent() {
        return dateDebEvent;
    }

    public void setDateDebEvent(String dateDebEvent) {
        this.dateDebEvent = dateDebEvent;
    }

    public String getCapacite() {
        return capacite;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getImgEvent() {
        return imgEvent;
    }

    public void setImgEvent(String imgEvent) {
        this.imgEvent = imgEvent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
