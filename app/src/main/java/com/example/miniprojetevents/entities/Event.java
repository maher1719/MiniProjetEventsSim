package com.example.miniprojetevents.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "Event")
public class Event implements Serializable {

    @SerializedName("idEvent")
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;



    @SerializedName("titre")
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("categorie")
    @ColumnInfo(name = "categorie")
    private String categorie;

    @SerializedName("type")
    @ColumnInfo(name = "type")
    private String type;

    @SerializedName("imgEvent")
    @ColumnInfo(name = "imgEvent")
    private String imgEvent;

    @SerializedName("montant")
    @ColumnInfo(name = "montant")
    private String montant;

    @SerializedName("capacite")
    @ColumnInfo(name = "capacite")
    private String capacite;


    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longtitude")
    private double longtitude;

    /*@SerializedName("dateDebEvent")
    @ColumnInfo(name = "dateDebEvent")
    private Date dateDebEvent;

    @SerializedName("timeDebEvent")
    @ColumnInfo(name = "timeDebEvent")
    private Time timeDebEvent;

    @SerializedName("dateFin")
    @ColumnInfo(name = "dateFin")
    private Date dateFin;

    @SerializedName("timeFinEvent")
    @ColumnInfo(name = "timeFinEvent")
    private Time timeFinEvent;*/


    @SerializedName("duree")
    @ColumnInfo(name = "duree")
    private String duree;



    @SerializedName("lieuEvent")
    @ColumnInfo(name = "lieuEvent")
    private String lieuEvent;

    @SerializedName("userMail")
    @ColumnInfo(name = "userMail")
    private String userMail;

    /*@SerializedName("deteAjout")
    @ColumnInfo(name = "dateAjout")
    private Date dateAjout;*/



    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String description;

    public Event(String title, String categorie, String type, String imgEvent, String montant, String capacite, String duree, String lieuEvent, String userMail, String description) {
        this.title = title;
        this.categorie = categorie;
        this.type = type;
        this.imgEvent = imgEvent;
        this.montant = montant;
        this.capacite = capacite;
        this.duree = duree;
        this.lieuEvent = lieuEvent;
        this.userMail = userMail;
        this.description = description;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgEvent() {
        return imgEvent;
    }

    public void setImgEvent(String imgEvent) {
        this.imgEvent = imgEvent;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getCapacite() {
        return capacite;
    }

    public void setCapacite(String capacite) {
        this.capacite = capacite;
    }

    /*public Date getDateDebEvent() {
        return dateDebEvent;
    }

    public void setDateDebEvent(Date dateDebEvent) {
        this.dateDebEvent = dateDebEvent;
    }

    public Time getTimeDebEvent() {
        return timeDebEvent;
    }

    public void setTimeDebEvent(Time timeDebEvent) {
        this.timeDebEvent = timeDebEvent;
    }*/

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    /*public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Time getTimeFinEvent() {
        return timeFinEvent;
    }

    public void setTimeFinEvent(Time timeFinEvent) {
        this.timeFinEvent = timeFinEvent;
    }*/

    public String getLieuEvent() {
        return lieuEvent;
    }

    public void setLieuEvent(String lieuEvent) {
        this.lieuEvent = lieuEvent;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    /*public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }*/

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", categorie='" + categorie + '\'' +
                ", type='" + type + '\'' +
                ", imgEvent='" + imgEvent + '\'' +
                ", montant='" + montant + '\'' +
                ", capacite='" + capacite + '\'' +
                ", duree='" + duree + '\'' +
                ", lieuEvent='" + lieuEvent + '\'' +
                ", userMail='" + userMail + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
