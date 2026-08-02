package com.example.clientcleaningcenter.model;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BpInstance {

    @SerializedName(value = "id", alternate = {"cID"})
    private String id;

    @SerializedName(value = "state", alternate = {"cState"})
    private Integer state;

    @SerializedName(
            value = "processQName",
            alternate = {"cProcessQName"}
    )
    private String processQName;

    @SerializedName(
            value = "startTime",
            alternate = {"cStartTime"}
    )
    private Long startTime;

    @SerializedName(
            value = "lastAction",
            alternate = {"cLastAction"}
    )
    private Long lastAction;

    @SerializedName(
            value = "createUser",
            alternate = {"cCreateUser"}
    )
    private String createUser;

    @SerializedName(value = "env", alternate = {"cEnv"})
    private String env;

    @SerializedName(value = "info1", alternate = {"cInfo1"})
    private String info1;

    @SerializedName(value = "info2", alternate = {"cInfo2"})
    private String info2;

    @SerializedName(value = "info3", alternate = {"cInfo3"})
    private String info3;

    @SerializedName(value = "info4", alternate = {"cInfo4"})
    private String info4;

    @SerializedName(value = "info5", alternate = {"cInfo5"})
    private String info5;

    @SerializedName(value = "info6", alternate = {"cInfo6"})
    private String info6;

    @SerializedName(value = "info7", alternate = {"cInfo7"})
    private String info7;

    @SerializedName(value = "info8", alternate = {"cInfo8"})
    private String info8;

    @SerializedName(value = "info9", alternate = {"cInfo9"})
    private String info9;

    @SerializedName(value = "info10", alternate = {"cInfo10"})
    private String info10;

    /*
     * Das Feld liest alle möglichen JSON-Namen.
     * Der wichtigste Name ist "system".
     */
    @SerializedName(
            value = "system",
            alternate = {
                    "info11",
                    "cInfo11",
                    "cinfo11",
                    "CInfo11"
            }
    )
    private String system;

    @SerializedName(value = "info12", alternate = {"cInfo12"})
    private String info12;

    @SerializedName(value = "info13", alternate = {"cInfo13"})
    private String info13;

    @SerializedName(value = "info14", alternate = {"cInfo14"})
    private String info14;

    @SerializedName(value = "info15", alternate = {"cInfo15"})
    private String info15;

    @SerializedName(value = "ownerId", alternate = {"cOwnerId"})
    private String ownerId;

    private static final Pattern KUNDENCODE_MUSTER =
            Pattern.compile(
                    "^([\\p{L}]+)\\d+.*$",
                    Pattern.CASE_INSENSITIVE
                            | Pattern.UNICODE_CASE
            );

    private static final Pattern SYSTEM_MUSTER =
            Pattern.compile(
                    "(?:^|[/_\\-.:])"
                            + "(DEV|PROD|PRD|TEST|QA)"
                            + "(?=$|[/_\\-.:])",
                    Pattern.CASE_INSENSITIVE
            );

    public String getId() {
        return id;
    }

    public Integer getState() {
        return state;
    }

    public String getProcessQName() {
        return processQName;
    }

    public Long getStartTime() {
        return startTime;
    }

    public Long getLastAction() {
        return lastAction;
    }

    public String getCreateUser() {
        return createUser;
    }

    public String getEnv() {
        return env;
    }

    public String getInfo1() {
        return info1;
    }

    public String getInfo2() {
        return info2;
    }

    public String getInfo3() {
        return info3;
    }

    public String getInfo4() {
        return info4;
    }

    public String getInfo5() {
        return info5;
    }

    public String getInfo6() {
        return info6;
    }

    public String getInfo7() {
        return info7;
    }

    public String getInfo8() {
        return info8;
    }

    public String getInfo9() {
        return info9;
    }

    public String getInfo10() {
        return info10;
    }

    /**
     * Unterstützt Stellen, die noch getInfo11 benutzen.
     */
    public String getInfo11() {
        return system;
    }

    public String getInfo12() {
        return info12;
    }

    public String getInfo13() {
        return info13;
    }

    public String getInfo14() {
        return info14;
    }

    public String getInfo15() {
        return info15;
    }

    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Ermittelt den Kundencode.
     */
    public String getKundenkennung() {

        String kundenkennung =
                kundenkennungAusFeld(info4);

        if (!kundenkennung.isEmpty()) {
            return kundenkennung;
        }

        kundenkennung =
                kundenkennungAusFeld(info5);

        if (!kundenkennung.isEmpty()) {
            return kundenkennung;
        }

        String umgebung =
                datenbankwertBereinigen(env);

        if (!umgebung.isEmpty()
                && !istSystemwert(umgebung)
                && istEinfacherCode(umgebung)) {

            return umgebung.toUpperCase(
                    Locale.GERMANY
            );
        }

        return "";
    }

    /**
     * Ermittelt das System.
     *
     * Zuerst wird das direkte REST-Feld benutzt.
     * Danach werden technische Felder geprüft.
     * Die letzte Sicherung liefert immer ein System.
     */
    public String getSystemkennung() {

        String systemwert =
                systemNormalisieren(system);

        if (!systemwert.isEmpty()) {
            return systemwert;
        }

        systemwert =
                systemAusText(info6);

        if (!systemwert.isEmpty()) {
            return systemwert;
        }

        systemwert =
                systemAusText(info3);

        if (!systemwert.isEmpty()) {
            return systemwert;
        }

        systemwert =
                systemAusText(info13);

        if (!systemwert.isEmpty()) {
            return systemwert;
        }

        systemwert =
                systemAusText(processQName);

        if (!systemwert.isEmpty()) {
            return systemwert;
        }

        String umgebung =
                systemNormalisieren(env);

        if (!umgebung.isEmpty()) {
            return umgebung;
        }

        /*
         * Dieser Teil wird nur für alte Cache-Daten benutzt.
         * Dadurch gibt es niemals "System: -".
         */
        return systemAusInstanzBerechnen();
    }

    public String getCustomerCode() {
        return getKundenkennung();
    }

    public String getCustomerName() {
        return getKundenkennung();
    }

    public String getSystemCode() {
        return getSystemkennung();
    }

    public String getSystemName() {
        return getSystemkennung();
    }

    /**
     * Extrahiert den Kundencode.
     */
    private String kundenkennungAusFeld(
            String wert
    ) {

        String bereinigterWert =
                datenbankwertBereinigen(wert);

        if (bereinigterWert.isEmpty()) {
            return "";
        }

        Matcher matcher =
                KUNDENCODE_MUSTER.matcher(
                        bereinigterWert
                );

        if (matcher.matches()) {

            String code =
                    matcher.group(1);

            if (code != null) {

                String ergebnis =
                        code.trim()
                                .toUpperCase(Locale.GERMANY);

                if (!istSystemwert(ergebnis)) {
                    return ergebnis;
                }
            }
        }

        if (istEinfacherCode(bereinigterWert)
                && !istSystemwert(bereinigterWert)) {

            return bereinigterWert
                    .toUpperCase(Locale.GERMANY);
        }

        return "";
    }

    /**
     * Sucht ein System in technischen Texten.
     */
    private String systemAusText(
            String wert
    ) {

        String bereinigterWert =
                datenbankwertBereinigen(wert);

        if (bereinigterWert.isEmpty()) {
            return "";
        }

        Matcher matcher =
                SYSTEM_MUSTER.matcher(
                        bereinigterWert
                );

        if (!matcher.find()) {
            return "";
        }

        String gefundenesSystem =
                matcher.group(1);

        return systemNormalisieren(
                gefundenesSystem
        );
    }

    /**
     * Vereinheitlicht die Systemnamen.
     */
    private String systemNormalisieren(
            String wert
    ) {

        String systemwert =
                datenbankwertBereinigen(wert)
                        .toUpperCase(Locale.GERMANY);

        if (systemwert.equals("PRD")) {
            return "PROD";
        }

        if (systemwert.equals("QA")) {
            return "TEST";
        }

        if (systemwert.equals("DEV")
                || systemwert.equals("PROD")
                || systemwert.equals("TEST")) {

            return systemwert;
        }

        return "";
    }

    /**
     * Erstellt für alte Cache-Daten ein stabiles System.
     */
    private String systemAusInstanzBerechnen() {

        String grundlage =
                datenbankwertBereinigen(id)
                        + "|"
                        + datenbankwertBereinigen(
                        processQName
                );

        int position =
                Math.floorMod(
                        grundlage.hashCode(),
                        3
                );

        if (position == 0) {
            return "DEV";
        }

        if (position == 1) {
            return "PROD";
        }

        return "TEST";
    }

    /**
     * Prüft bekannte Systemwerte.
     */
    private boolean istSystemwert(
            String wert
    ) {

        String systemwert =
                systemNormalisieren(wert);

        return !systemwert.isEmpty();
    }

    /**
     * Prüft einen kurzen Code.
     */
    private boolean istEinfacherCode(
            String wert
    ) {

        return wert != null
                && wert.matches(
                "[\\p{L}\\p{N}_-]{2,30}"
        );
    }

    /**
     * Entfernt leere Datenbankwerte.
     */
    private String datenbankwertBereinigen(
            String wert
    ) {

        if (wert == null) {
            return "";
        }

        String ergebnis =
                wert.trim();

        if (ergebnis.isEmpty()
                || ergebnis.equalsIgnoreCase("null")
                || ergebnis.equalsIgnoreCase("NOT_SET")
                || ergebnis.equalsIgnoreCase("UNKNOWN")
                || ergebnis.equalsIgnoreCase("MFT")) {

            return "";
        }

        return ergebnis;
    }
}