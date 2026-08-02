package com.example.restapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.Locale;

@Entity
@Table(name = "tBPInstances")
public class BpInstance {

    @Id
    @Column(name = "cID")
    @JsonProperty("id")
    private String id;

    @Column(name = "cState")
    @JsonProperty("state")
    private Integer state;

    @Column(name = "cProcessQName")
    @JsonProperty("processQName")
    private String processQName;

    @Column(name = "cStartTime")
    @JsonProperty("startTime")
    private Long startTime;

    @Column(name = "cLastAction")
    @JsonProperty("lastAction")
    private Long lastAction;

    @Column(name = "cCreateUser")
    @JsonProperty("createUser")
    private String createUser;

    @Column(name = "cEnv")
    @JsonProperty("env")
    private String env;

    @Column(name = "cInfo1")
    @JsonProperty("info1")
    private String info1;

    @Column(name = "cInfo2")
    @JsonProperty("info2")
    private String info2;

    @Column(name = "cInfo3")
    @JsonProperty("info3")
    private String info3;

    @Column(name = "cInfo4")
    @JsonProperty("info4")
    private String info4;

    @Column(name = "cInfo5")
    @JsonProperty("info5")
    private String info5;

    @Column(name = "cInfo6")
    @JsonProperty("info6")
    private String info6;

    @Column(name = "cInfo7")
    @JsonProperty("info7")
    private String info7;

    @Column(name = "cInfo8")
    @JsonProperty("info8")
    private String info8;

    @Column(name = "cInfo9")
    @JsonProperty("info9")
    private String info9;

    @Column(name = "cInfo10")
    @JsonProperty("info10")
    private String info10;

    /*
     * Das System steht in cInfo11.
     * Erlaubte Werte: DEV, PROD und TEST.
     */
    @Column(name = "cInfo11")
    @JsonProperty("info11")
    private String info11;

    @Column(name = "cInfo12")
    @JsonProperty("info12")
    private String info12;

    @Column(name = "cInfo13")
    @JsonProperty("info13")
    private String info13;

    @Column(name = "cInfo14")
    @JsonProperty("info14")
    private String info14;

    @Column(name = "cInfo15")
    @JsonProperty("info15")
    private String info15;

    @Column(name = "cOwnerId")
    @JsonProperty("ownerId")
    private String ownerId;

    /**
     * Leerer Konstruktor für JPA.
     */
    public BpInstance() {
    }

    /**
     * Gibt die Instanz-ID zurück.
     */
    public String getId() {
        return id;
    }

    /**
     * Setzt die Instanz-ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gibt den Status zurück.
     */
    public Integer getState() {
        return state;
    }

    /**
     * Setzt den Status.
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * Gibt den Prozessnamen zurück.
     */
    public String getProcessQName() {
        return processQName;
    }

    /**
     * Setzt den Prozessnamen.
     */
    public void setProcessQName(String processQName) {
        this.processQName = processQName;
    }

    /**
     * Gibt die Startzeit zurück.
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * Setzt die Startzeit.
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * Gibt die Zeit der letzten Aktion zurück.
     */
    public Long getLastAction() {
        return lastAction;
    }

    /**
     * Setzt die Zeit der letzten Aktion.
     */
    public void setLastAction(Long lastAction) {
        this.lastAction = lastAction;
    }

    /**
     * Gibt den Ersteller zurück.
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * Setzt den Ersteller.
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * Gibt die Umgebung zurück.
     */
    public String getEnv() {
        return env;
    }

    /**
     * Setzt die Umgebung.
     */
    public void setEnv(String env) {
        this.env = env;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getInfo5() {
        return info5;
    }

    public void setInfo5(String info5) {
        this.info5 = info5;
    }

    public String getInfo6() {
        return info6;
    }

    public void setInfo6(String info6) {
        this.info6 = info6;
    }

    public String getInfo7() {
        return info7;
    }

    public void setInfo7(String info7) {
        this.info7 = info7;
    }

    public String getInfo8() {
        return info8;
    }

    public void setInfo8(String info8) {
        this.info8 = info8;
    }

    public String getInfo9() {
        return info9;
    }

    public void setInfo9(String info9) {
        this.info9 = info9;
    }

    public String getInfo10() {
        return info10;
    }

    public void setInfo10(String info10) {
        this.info10 = info10;
    }

    /**
     * Gibt den direkten Systemwert zurück.
     */
    public String getInfo11() {
        return info11;
    }

    /**
     * Setzt den direkten Systemwert.
     */
    public void setInfo11(String info11) {
        this.info11 = info11;
    }

    public String getInfo12() {
        return info12;
    }

    public void setInfo12(String info12) {
        this.info12 = info12;
    }

    public String getInfo13() {
        return info13;
    }

    public void setInfo13(String info13) {
        this.info13 = info13;
    }

    public String getInfo14() {
        return info14;
    }

    public void setInfo14(String info14) {
        this.info14 = info14;
    }

    public String getInfo15() {
        return info15;
    }

    public void setInfo15(String info15) {
        this.info15 = info15;
    }

    /**
     * Gibt den verantwortlichen Benutzer zurück.
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Setzt den verantwortlichen Benutzer.
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Sendet den Systemwert zusätzlich als "system".
     *
     * Dadurch kann Android unabhängig vom Feldnamen
     * immer auf den Systemwert zugreifen.
     */
    @Transient
    @JsonProperty("system")
    public String getSystem() {
        return systemNormalisieren(info11);
    }

    /**
     * Vereinheitlicht die Systemwerte.
     */
    private String systemNormalisieren(String wert) {

        if (wert == null) {
            return "";
        }

        String system =
                wert.trim()
                        .toUpperCase(Locale.GERMANY);

        if (system.equals("PRD")) {
            return "PROD";
        }

        if (system.equals("QA")) {
            return "TEST";
        }

        if (system.equals("DEV")
                || system.equals("PROD")
                || system.equals("TEST")) {

            return system;
        }

        return "";
    }
}