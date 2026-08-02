package com.example.clientcleaningcenter.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.clientcleaningcenter.R;
import com.example.clientcleaningcenter.model.BpInstance;
import com.example.clientcleaningcenter.viewmodel.InstanceViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

public class InstanceActivity extends AppCompatActivity {

    private static final int MAXIMALER_ZEITRAUM_TAGE = 40;

    private TextView anzahlText;
    private TextView statusText;
    private TextView aktualisierungszeitText;

    private TextView kundenAuswahl;
    private TextView systemAuswahl;

    private TextView einTagSchaltflaeche;
    private TextView siebenTageSchaltflaeche;
    private TextView fuenfzehnTageSchaltflaeche;
    private TextView maximalerZeitraumSchaltflaeche;

    private TextView startdatumSchaltflaeche;
    private TextView enddatumSchaltflaeche;

    private TextView filterAnwendenSchaltflaeche;
    private TextView filterZuruecksetzenSchaltflaeche;
    private TextView filterZusammenfassungText;

    private TextView prozessTitelText;
    private TextView trefferAnzahlText;

    private TextView leerzustandTitelText;
    private TextView leerzustandBeschreibungText;

    private ImageButton aktualisierenSchaltflaeche;

    private LinearLayout zusammenfassungKarte;
    private LinearLayout filterKarte;
    private LinearLayout leerzustandContainer;
    private LinearLayout ergebnisKopfContainer;
    private LinearLayout prozessContainer;

    private InstanceViewModel instanceViewModel;

    private List<BpInstance> alleInstanzen =
            new ArrayList<>();

    private final List<BpInstance> gefilterteInstanzen =
            new ArrayList<>();

    private final List<String> kundenOptionen =
            new ArrayList<>();

    private final List<String> systemOptionen =
            new ArrayList<>();

    private String ausgewaehlterKunde = "";
    private String ausgewaehltesSystem = "";

    private int ausgewaehlteSchnelltage = 0;

    private Long ausgewaehlteStartzeit;
    private Long ausgewaehlteEndzeit;

    private boolean filterWurdeAngewendet = false;

    private final TimeZone berlinerZeitzone =
            TimeZone.getTimeZone("Europe/Berlin");

    /**
     * Startet die Activity.
     */
    @Override
    protected void onCreate(
            Bundle gespeicherterZustand
    ) {
        super.onCreate(gespeicherterZustand);
        setContentView(R.layout.activity_instance);

        ansichtenVerbinden();
        oberflaecheVorbereiten();

        instanceViewModel =
                new ViewModelProvider(this)
                        .get(InstanceViewModel.class);

        klickEreignisseEinrichten();
        viewModelBeobachten();
        startzustandAnzeigen();

        instanceViewModel.loadInstances();
    }

    /**
     * Verbindet alle XML-Elemente.
     */
    private void ansichtenVerbinden() {

        anzahlText =
                findViewById(R.id.totalCountText);

        statusText =
                findViewById(R.id.statusText);

        aktualisierungszeitText =
                findViewById(R.id.lastRefreshText);

        kundenAuswahl =
                findViewById(R.id.customerSelectButton);

        systemAuswahl =
                findViewById(R.id.systemSelectButton);

        einTagSchaltflaeche =
                findViewById(R.id.oneDayButton);

        siebenTageSchaltflaeche =
                findViewById(R.id.sevenDaysButton);

        fuenfzehnTageSchaltflaeche =
                findViewById(R.id.fifteenDaysButton);

        maximalerZeitraumSchaltflaeche =
                findViewById(R.id.maximumRangeButton);

        startdatumSchaltflaeche =
                findViewById(R.id.startDateButton);

        enddatumSchaltflaeche =
                findViewById(R.id.endDateButton);

        filterAnwendenSchaltflaeche =
                findViewById(R.id.applyFilterButton);

        filterZuruecksetzenSchaltflaeche =
                findViewById(R.id.resetFilterButton);

        filterZusammenfassungText =
                findViewById(R.id.filterSummaryText);

        prozessTitelText =
                findViewById(R.id.processTitleText);

        trefferAnzahlText =
                findViewById(R.id.resultCountText);

        leerzustandTitelText =
                findViewById(R.id.emptyStateTitleText);

        leerzustandBeschreibungText =
                findViewById(R.id.emptyStateDescriptionText);

        aktualisierenSchaltflaeche =
                findViewById(R.id.refreshButton);

        zusammenfassungKarte =
                findViewById(R.id.summaryCard);

        filterKarte =
                findViewById(R.id.filterCard);

        leerzustandContainer =
                findViewById(R.id.emptyStateContainer);

        ergebnisKopfContainer =
                findViewById(R.id.resultHeaderContainer);

        prozessContainer =
                findViewById(R.id.processContainer);
    }

    /**
     * Bereitet die Oberfläche vor.
     */
    private void oberflaecheVorbereiten() {

        zusammenfassungKarte.setBackground(
                abgerundetenHintergrundErstellen(
                        "#FFFFFF",
                        "#E2E8F0",
                        26
                )
        );

        filterKarte.setBackground(
                abgerundetenHintergrundErstellen(
                        "#FFFFFF",
                        "#E2E8F0",
                        26
                )
        );

        leerzustandContainer.setBackground(
                abgerundetenHintergrundErstellen(
                        "#FFFFFF",
                        "#E2E8F0",
                        26
                )
        );

        kundenAuswahl.setBackground(
                eingabeHintergrundErstellen()
        );

        systemAuswahl.setBackground(
                eingabeHintergrundErstellen()
        );

        startdatumSchaltflaeche.setBackground(
                eingabeHintergrundErstellen()
        );

        enddatumSchaltflaeche.setBackground(
                eingabeHintergrundErstellen()
        );

        filterAnwendenSchaltflaeche.setBackground(
                abgerundetenHintergrundErstellen(
                        "#D90429",
                        "#D90429",
                        17
                )
        );

        filterZuruecksetzenSchaltflaeche.setBackground(
                abgerundetenHintergrundErstellen(
                        "#FFFFFF",
                        "#CBD5E1",
                        17
                )
        );

        auswahlTexteAktualisieren();
        datumsTexteAktualisieren();
        schnellwahlAktualisieren();
    }

    /**
     * Richtet die Klick-Ereignisse ein.
     */
    private void klickEreignisseEinrichten() {

        aktualisierenSchaltflaeche.setOnClickListener(
                ansicht ->
                        instanceViewModel.loadInstances()
        );

        kundenAuswahl.setOnClickListener(
                ansicht ->
                        kundenDialogAnzeigen()
        );

        systemAuswahl.setOnClickListener(
                ansicht ->
                        systemDialogAnzeigen()
        );

        einTagSchaltflaeche.setOnClickListener(
                ansicht ->
                        schnellzeitraumAuswaehlen(1)
        );

        siebenTageSchaltflaeche.setOnClickListener(
                ansicht ->
                        schnellzeitraumAuswaehlen(7)
        );

        fuenfzehnTageSchaltflaeche.setOnClickListener(
                ansicht ->
                        schnellzeitraumAuswaehlen(15)
        );

        maximalerZeitraumSchaltflaeche.setOnClickListener(
                ansicht ->
                        schnellzeitraumAuswaehlen(
                                MAXIMALER_ZEITRAUM_TAGE
                        )
        );

        startdatumSchaltflaeche.setOnClickListener(
                ansicht ->
                        startdatumAuswaehlen()
        );

        enddatumSchaltflaeche.setOnClickListener(
                ansicht ->
                        enddatumAuswaehlen()
        );

        filterAnwendenSchaltflaeche.setOnClickListener(
                ansicht ->
                        filterPruefenUndAnwenden()
        );

        filterZuruecksetzenSchaltflaeche.setOnClickListener(
                ansicht ->
                        filterZuruecksetzen()
        );
    }

    /**
     * Beobachtet die Daten aus dem ViewModel.
     */
    private void viewModelBeobachten() {

        instanceViewModel.loading.observe(
                this,
                wirdGeladen -> {

                    boolean laden =
                            Boolean.TRUE.equals(
                                    wirdGeladen
                            );

                    aktualisierenSchaltflaeche.setEnabled(
                            !laden
                    );

                    if (laden) {
                        statusText.setText(
                                "Daten werden geladen..."
                        );
                    }
                }
        );

        instanceViewModel.instances.observe(
                this,
                instanzen -> {

                    alleInstanzen =
                            instanzen == null
                                    ? new ArrayList<>()
                                    : instanzen;

                    anzahlText.setText(
                            String.valueOf(
                                    alleInstanzen.size()
                            )
                    );

                    aktualisierungszeitText.setText(
                            "Zuletzt aktualisiert: "
                                    + aktuelleUhrzeitErmitteln()
                    );

                    statusText.setText(
                            "✓ Daten geladen"
                    );

                    filterOptionenAusDatenErstellen();

                    if (filterWurdeAngewendet) {
                        filterAnwenden();
                    } else {
                        startzustandAnzeigen();
                    }
                }
        );

        instanceViewModel.errorMessage.observe(
                this,
                meldung -> {

                    if (meldung == null
                            || meldung.trim().isEmpty()) {
                        return;
                    }

                    statusText.setText(
                            "Fehler: " + meldung
                    );

                    fehlerzustandAnzeigen(
                            meldung
                    );
                }
        );
    }

    /**
     * Erstellt die Filterwerte aus den REST-Daten.
     */
    private void filterOptionenAusDatenErstellen() {

        Set<String> kunden =
                new LinkedHashSet<>();

        Set<String> systeme =
                new LinkedHashSet<>();

        for (BpInstance instanz : alleInstanzen) {

            String kunde =
                    datenbankwertBereinigen(
                            instanz.getKundenkennung()
                    );

            String system =
                    systemNormalisieren(
                            instanz.getSystemkennung()
                    );

            eindeutigenWertHinzufuegen(
                    kunden,
                    kunde
            );

            eindeutigenWertHinzufuegen(
                    systeme,
                    system
            );
        }

        kundenOptionen.clear();
        kundenOptionen.addAll(kunden);
        kundenOptionen.sort(
                String.CASE_INSENSITIVE_ORDER
        );

        systemOptionen.clear();
        systemOptionen.addAll(systeme);
        systemOptionen.sort(
                String.CASE_INSENSITIVE_ORDER
        );

        kundenauswahlWiederherstellen();
        systemauswahlWiederherstellen();
        auswahlTexteAktualisieren();
    }

    /**
     * Zeigt den Kundendialog.
     */
    private void kundenDialogAnzeigen() {

        if (kundenOptionen.isEmpty()) {

            Toast.makeText(
                    this,
                    "Keine Kunden gefunden.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String[] kunden =
                kundenOptionen.toArray(
                        new String[0]
                );

        new AlertDialog.Builder(this)
                .setTitle("Kunde auswählen")
                .setSingleChoiceItems(
                        kunden,
                        ausgewaehltenKundenindexErmitteln(),
                        (dialog, position) -> {

                            ausgewaehlterKunde =
                                    kundenOptionen.get(
                                            position
                                    );

                            auswahlTexteAktualisieren();
                            dialog.dismiss();
                        }
                )
                .setNegativeButton(
                        "Abbrechen",
                        null
                )
                .show();
    }

    /**
     * Zeigt nur DEV, PROD und TEST.
     */
    private void systemDialogAnzeigen() {

        List<String> werte =
                new ArrayList<>();

        werte.add("");
        werte.addAll(systemOptionen);

        String[] bezeichnungen =
                new String[werte.size()];

        bezeichnungen[0] =
                "Alle Systeme";

        for (int index = 1;
             index < werte.size();
             index++) {

            bezeichnungen[index] =
                    werte.get(index);
        }

        new AlertDialog.Builder(this)
                .setTitle("System auswählen")
                .setSingleChoiceItems(
                        bezeichnungen,
                        ausgewaehltenSystemindexErmitteln(
                                werte
                        ),
                        (dialog, position) -> {

                            ausgewaehltesSystem =
                                    systemNormalisieren(
                                            werte.get(position)
                                    );

                            auswahlTexteAktualisieren();
                            dialog.dismiss();
                        }
                )
                .setNegativeButton(
                        "Abbrechen",
                        null
                )
                .show();
    }

    /**
     * Wählt einen schnellen Zeitraum.
     */
    private void schnellzeitraumAuswaehlen(
            int tage
    ) {

        Calendar ende =
                berlinerKalenderErstellen();

        ausgewaehlteEndzeit =
                ende.getTimeInMillis();

        Calendar start =
                berlinerKalenderErstellen();

        start.set(
                Calendar.HOUR_OF_DAY,
                0
        );

        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);

        start.add(
                Calendar.DAY_OF_MONTH,
                -(tage - 1)
        );

        ausgewaehlteStartzeit =
                start.getTimeInMillis();

        ausgewaehlteSchnelltage = tage;

        datumsTexteAktualisieren();
        schnellwahlAktualisieren();
    }

    /**
     * Öffnet das Startdatum.
     */
    private void startdatumAuswaehlen() {

        Calendar kalender =
                berlinerKalenderErstellen();

        if (ausgewaehlteStartzeit != null) {
            kalender.setTimeInMillis(
                    ausgewaehlteStartzeit
            );
        }

        DatePickerDialog dialog =
                new DatePickerDialog(
                        this,
                        (ansicht, jahr, monat, tag) -> {

                            Calendar start =
                                    berlinerKalenderErstellen();

                            start.set(
                                    jahr,
                                    monat,
                                    tag,
                                    0,
                                    0,
                                    0
                            );

                            start.set(
                                    Calendar.MILLISECOND,
                                    0
                            );

                            ausgewaehlteStartzeit =
                                    start.getTimeInMillis();

                            if (ausgewaehlteEndzeit != null
                                    && !endzeitIstGueltig(
                                    ausgewaehlteEndzeit
                            )) {

                                ausgewaehlteEndzeit = null;
                            }

                            ausgewaehlteSchnelltage = 0;

                            datumsTexteAktualisieren();
                            schnellwahlAktualisieren();
                        },
                        kalender.get(Calendar.YEAR),
                        kalender.get(Calendar.MONTH),
                        kalender.get(
                                Calendar.DAY_OF_MONTH
                        )
                );

        dialog.getDatePicker().setMaxDate(
                System.currentTimeMillis()
        );

        dialogFarbenSetzen(dialog);
        dialog.show();
    }

    /**
     * Öffnet das Enddatum.
     */
    private void enddatumAuswaehlen() {

        if (ausgewaehlteStartzeit == null) {

            filterFehlerAnzeigen(
                    "Bitte zuerst ein Startdatum auswählen."
            );

            return;
        }

        long maximaleEndzeit =
                maximaleEndzeitBerechnen(
                        ausgewaehlteStartzeit
                );

        Calendar kalender =
                berlinerKalenderErstellen();

        kalender.setTimeInMillis(
                ausgewaehlteEndzeit != null
                        ? ausgewaehlteEndzeit
                        : maximaleEndzeit
        );

        DatePickerDialog dialog =
                new DatePickerDialog(
                        this,
                        (ansicht, jahr, monat, tag) -> {

                            Calendar ende =
                                    berlinerKalenderErstellen();

                            ende.set(
                                    jahr,
                                    monat,
                                    tag,
                                    23,
                                    59,
                                    59
                            );

                            ende.set(
                                    Calendar.MILLISECOND,
                                    999
                            );

                            long neueEndzeit =
                                    Math.min(
                                            ende.getTimeInMillis(),
                                            System.currentTimeMillis()
                                    );

                            if (!endzeitIstGueltig(
                                    neueEndzeit
                            )) {

                                filterFehlerAnzeigen(
                                        "Der Zeitraum darf maximal 40 Tage umfassen."
                                );

                                return;
                            }

                            ausgewaehlteEndzeit =
                                    neueEndzeit;

                            ausgewaehlteSchnelltage = 0;

                            datumsTexteAktualisieren();
                            schnellwahlAktualisieren();
                        },
                        kalender.get(Calendar.YEAR),
                        kalender.get(Calendar.MONTH),
                        kalender.get(
                                Calendar.DAY_OF_MONTH
                        )
                );

        dialog.getDatePicker().setMinDate(
                tagesbeginnErmitteln(
                        ausgewaehlteStartzeit
                )
        );

        dialog.getDatePicker().setMaxDate(
                maximaleEndzeit
        );

        dialogFarbenSetzen(dialog);
        dialog.show();
    }

    /**
     * Berechnet das maximale Enddatum.
     */
    private long maximaleEndzeitBerechnen(
            long startzeit
    ) {

        Calendar maximum =
                kalenderAufTagesbeginnSetzen(
                        startzeit
                );

        maximum.add(
                Calendar.DAY_OF_MONTH,
                MAXIMALER_ZEITRAUM_TAGE - 1
        );

        maximum.set(
                Calendar.HOUR_OF_DAY,
                23
        );

        maximum.set(Calendar.MINUTE, 59);
        maximum.set(Calendar.SECOND, 59);
        maximum.set(Calendar.MILLISECOND, 999);

        return Math.min(
                maximum.getTimeInMillis(),
                System.currentTimeMillis()
        );
    }

    /**
     * Prüft das Enddatum.
     */
    private boolean endzeitIstGueltig(
            long endzeit
    ) {

        if (ausgewaehlteStartzeit == null) {
            return false;
        }

        return endzeit >= ausgewaehlteStartzeit
                && endzeit
                <= maximaleEndzeitBerechnen(
                ausgewaehlteStartzeit
        )
                && endzeit
                <= System.currentTimeMillis();
    }

    /**
     * Prüft die Filter.
     */
    private void filterPruefenUndAnwenden() {

        if (ausgewaehlterKunde.isEmpty()) {

            filterFehlerAnzeigen(
                    "Bitte einen Kunden auswählen."
            );

            return;
        }

        if (ausgewaehlteStartzeit == null
                || ausgewaehlteEndzeit == null) {

            filterFehlerAnzeigen(
                    "Bitte einen Zeitraum auswählen."
            );

            return;
        }

        if (!endzeitIstGueltig(
                ausgewaehlteEndzeit
        )) {

            filterFehlerAnzeigen(
                    "Der Zeitraum darf maximal 40 Tage umfassen."
            );

            return;
        }

        filterWurdeAngewendet = true;
        filterAnwenden();
    }

    /**
     * Filtert alle Instanzen.
     */
    private void filterAnwenden() {

        gefilterteInstanzen.clear();

        for (BpInstance instanz : alleInstanzen) {

            if (!passtZumKunden(instanz)) {
                continue;
            }

            if (!passtZumSystem(instanz)) {
                continue;
            }

            if (!passtZumZeitraum(instanz)) {
                continue;
            }

            gefilterteInstanzen.add(instanz);
        }

        gefilterteInstanzen.sort(
                (erste, zweite) ->
                        Long.compare(
                                relevanteZeitErmitteln(
                                        zweite
                                ),
                                relevanteZeitErmitteln(
                                        erste
                                )
                        )
        );

        ergebnisseAnzeigen();
    }

    /**
     * Prüft den Kunden.
     */
    private boolean passtZumKunden(
            BpInstance instanz
    ) {

        String kunde =
                datenbankwertBereinigen(
                        instanz.getKundenkennung()
                );

        return kunde.equalsIgnoreCase(
                ausgewaehlterKunde
        );
    }

    /**
     * Prüft das System.
     */
    private boolean passtZumSystem(
            BpInstance instanz
    ) {

        if (ausgewaehltesSystem.isEmpty()) {
            return true;
        }

        String system =
                systemNormalisieren(
                        instanz.getSystemkennung()
                );

        return system.equalsIgnoreCase(
                systemNormalisieren(
                        ausgewaehltesSystem
                )
        );
    }

    /**
     * Prüft den Zeitraum.
     */
    private boolean passtZumZeitraum(
            BpInstance instanz
    ) {

        long zeit =
                relevanteZeitErmitteln(instanz);

        return zeit > 0
                && zeit >= ausgewaehlteStartzeit
                && zeit <= ausgewaehlteEndzeit;
    }

    /**
     * Ermittelt die relevante Prozesszeit.
     */
    private long relevanteZeitErmitteln(
            BpInstance instanz
    ) {

        Long startzeit =
                inMillisekundenUmwandeln(
                        instanz.getStartTime()
                );

        if (startzeit != null) {
            return startzeit;
        }

        Long letzteAktion =
                inMillisekundenUmwandeln(
                        instanz.getLastAction()
                );

        return letzteAktion == null
                ? 0L
                : letzteAktion;
    }

    /**
     * Zeigt die Ergebnisse.
     */
    private void ergebnisseAnzeigen() {

        leerzustandContainer.setVisibility(
                View.GONE
        );

        ergebnisKopfContainer.setVisibility(
                View.VISIBLE
        );

        prozessContainer.setVisibility(
                View.VISIBLE
        );

        StringBuilder titel =
                new StringBuilder(
                        "Prozesse für "
                                + ausgewaehlterKunde
                );

        if (!ausgewaehltesSystem.isEmpty()) {

            titel.append(" · ")
                    .append(
                            systemNormalisieren(
                                    ausgewaehltesSystem
                            )
                    );
        }

        prozessTitelText.setText(
                titel.toString()
        );

        trefferAnzahlText.setText(
                gefilterteInstanzen.size()
                        + " Treffer"
        );

        filterZusammenfassungAktualisieren();
        prozesskartenAnzeigen();
    }

    /**
     * Zeigt alle Prozesskarten.
     */
    private void prozesskartenAnzeigen() {

        prozessContainer.removeAllViews();

        if (gefilterteInstanzen.isEmpty()) {

            TextView leerText =
                    new TextView(this);

            leerText.setText(
                    "Keine passenden Prozesse gefunden."
            );

            leerText.setGravity(Gravity.CENTER);
            leerText.setTextSize(14);

            leerText.setPadding(
                    dp(20),
                    dp(30),
                    dp(20),
                    dp(30)
            );

            prozessContainer.addView(leerText);
            return;
        }

        for (BpInstance instanz
                : gefilterteInstanzen) {

            prozessContainer.addView(
                    prozesskarteErstellen(
                            instanz
                    )
            );
        }
    }

    /**
     * Erstellt eine Prozesskarte.
     */
    private View prozesskarteErstellen(
            BpInstance instanz
    ) {

        LinearLayout karte =
                new LinearLayout(this);

        karte.setOrientation(
                LinearLayout.VERTICAL
        );

        karte.setBackground(
                abgerundetenHintergrundErstellen(
                        "#FFFFFF",
                        "#D8DEE9",
                        25
                )
        );

        LinearLayout.LayoutParams kartenParameter =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        kartenParameter.setMargins(
                0,
                0,
                0,
                dp(16)
        );

        karte.setLayoutParams(kartenParameter);

        View akzentLinie =
                new View(this);

        akzentLinie.setBackgroundColor(
                Color.parseColor("#D90429")
        );

        akzentLinie.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(4)
                )
        );

        karte.addView(akzentLinie);

        LinearLayout inhalt =
                new LinearLayout(this);

        inhalt.setOrientation(
                LinearLayout.VERTICAL
        );

        inhalt.setPadding(
                dp(14),
                dp(16),
                dp(14),
                dp(16)
        );

        TextView titelText =
                new TextView(this);

        titelText.setText(
                prozessnamenVerkuerzen(
                        instanz.getProcessQName()
                )
        );

        titelText.setTextColor(
                Color.parseColor("#101828")
        );

        titelText.setTextSize(18);

        titelText.setTypeface(
                null,
                Typeface.BOLD
        );

        inhalt.addView(titelText);

        String kundenkennung =
                datenbankwertBereinigen(
                        instanz.getKundenkennung()
                );

        String kundenanzeige =
                kundennameFuerKarte(
                        kundenkennung
                );

        /*
         * getSystemkennung liefert immer
         * DEV, PROD oder TEST.
         */
        String system =
                systemNormalisieren(
                        instanz.getSystemkennung()
                );

        LinearLayout kennzeichenZeile =
                new LinearLayout(this);

        kennzeichenZeile.setOrientation(
                LinearLayout.HORIZONTAL
        );

        kennzeichenZeile.setPadding(
                0,
                dp(12),
                0,
                dp(5)
        );

        kennzeichenZeile.addView(
                informationsKennzeichenErstellen(
                        "Kunde: " + kundenanzeige,
                        "#FEE2E2",
                        "#B91C1C",
                        "#FCA5A5"
                )
        );

        kennzeichenZeile.addView(
                informationsKennzeichenErstellen(
                        "System: " + system,
                        "#DBEAFE",
                        "#1D4ED8",
                        "#93C5FD"
                )
        );

        inhalt.addView(kennzeichenZeile);

        LinearLayout uebersicht =
                abschnittErstellen(
                        "Übersicht"
                );

        tabellenzeileHinzufuegen(
                uebersicht,
                "Startzeit",
                zeitFormatieren(
                        instanz.getStartTime()
                )
        );

        tabellenzeileHinzufuegen(
                uebersicht,
                "Letzte Aktion",
                zeitFormatieren(
                        instanz.getLastAction()
                )
        );

        tabellenzeileHinzufuegen(
                uebersicht,
                "Erstellt von",
                instanz.getCreateUser()
        );

        tabellenzeileHinzufuegen(
                uebersicht,
                "Verantwortlich",
                instanz.getOwnerId()
        );

        inhalt.addView(uebersicht);

        LinearLayout details =
                abschnittErstellen(
                        "Technische Details"
                );

        details.setVisibility(View.GONE);

        tabellenzeileHinzufuegen(
                details,
                "Instanz-ID",
                instanz.getId()
        );

        tabellenzeileHinzufuegen(
                details,
                "Prozesspfad",
                instanz.getInfo3()
        );

        tabellenzeileHinzufuegen(
                details,
                "Zusatzinfo",
                instanz.getInfo6()
        );

        TextView detailsSchaltflaeche =
                new TextView(this);

        detailsSchaltflaeche.setText(
                "Details anzeigen  ▾"
        );

        detailsSchaltflaeche.setTextColor(
                Color.parseColor("#2563EB")
        );

        detailsSchaltflaeche.setTextSize(14);

        detailsSchaltflaeche.setTypeface(
                null,
                Typeface.BOLD
        );

        detailsSchaltflaeche.setGravity(
                Gravity.CENTER
        );

        detailsSchaltflaeche.setPadding(
                dp(10),
                dp(14),
                dp(10),
                dp(8)
        );

        detailsSchaltflaeche.setOnClickListener(
                ansicht -> {

                    boolean sichtbar =
                            details.getVisibility()
                                    == View.VISIBLE;

                    details.setVisibility(
                            sichtbar
                                    ? View.GONE
                                    : View.VISIBLE
                    );

                    detailsSchaltflaeche.setText(
                            sichtbar
                                    ? "Details anzeigen  ▾"
                                    : "Details ausblenden  ▴"
                    );
                }
        );

        inhalt.addView(detailsSchaltflaeche);
        inhalt.addView(details);

        karte.addView(inhalt);

        return karte;
    }

    /**
     * Wandelt den Kundencode nur für Karten um.
     */
    private String kundennameFuerKarte(
            String kundenkennung
    ) {

        if (kundenkennung == null) {
            return "-";
        }

        switch (
                kundenkennung.trim()
                        .toUpperCase(Locale.GERMANY)
        ) {
            case "CHR":
                return "Christ";

            case "THA":
                return "Thalia";

            case "TRA":
                return "Transgourmet";

            case "WUER":
                return "Würth";

            default:
                return kundenkennung;
        }
    }

    /**
     * Erstellt ein Kennzeichen.
     */
    private TextView informationsKennzeichenErstellen(
            String text,
            String fuellfarbe,
            String textfarbe,
            String randfarbe
    ) {

        TextView kennzeichen =
                new TextView(this);

        kennzeichen.setText(text);

        kennzeichen.setTextColor(
                Color.parseColor(textfarbe)
        );

        kennzeichen.setTextSize(12);

        kennzeichen.setTypeface(
                null,
                Typeface.BOLD
        );

        kennzeichen.setGravity(
                Gravity.CENTER
        );

        kennzeichen.setPadding(
                dp(8),
                dp(8),
                dp(8),
                dp(8)
        );

        kennzeichen.setBackground(
                abgerundetenHintergrundErstellen(
                        fuellfarbe,
                        randfarbe,
                        18
                )
        );

        LinearLayout.LayoutParams parameter =
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                );

        parameter.setMargins(
                0,
                0,
                dp(7),
                0
        );

        kennzeichen.setLayoutParams(parameter);

        return kennzeichen;
    }

    /**
     * Erstellt einen Kartenabschnitt.
     */
    private LinearLayout abschnittErstellen(
            String titel
    ) {

        LinearLayout abschnitt =
                new LinearLayout(this);

        abschnitt.setOrientation(
                LinearLayout.VERTICAL
        );

        abschnitt.setPadding(
                dp(12),
                dp(12),
                dp(12),
                dp(12)
        );

        abschnitt.setBackground(
                abgerundetenHintergrundErstellen(
                        "#F8FAFC",
                        "#E2E8F0",
                        18
                )
        );

        LinearLayout.LayoutParams parameter =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        parameter.setMargins(
                0,
                dp(14),
                0,
                0
        );

        abschnitt.setLayoutParams(parameter);

        TextView titelText =
                new TextView(this);

        titelText.setText(titel);
        titelText.setTextSize(14);

        titelText.setTextColor(
                Color.parseColor("#101828")
        );

        titelText.setTypeface(
                null,
                Typeface.BOLD
        );

        titelText.setPadding(
                0,
                0,
                0,
                dp(8)
        );

        abschnitt.addView(titelText);

        return abschnitt;
    }

    /**
     * Fügt eine Tabellenzeile hinzu.
     */
    private void tabellenzeileHinzufuegen(
            LinearLayout eltern,
            String bezeichnung,
            String wert
    ) {

        if (wert == null
                || wert.trim().isEmpty()
                || wert.equals("-")
                || wert.equalsIgnoreCase("null")) {

            return;
        }

        LinearLayout zeile =
                new LinearLayout(this);

        zeile.setOrientation(
                LinearLayout.HORIZONTAL
        );

        zeile.setPadding(
                dp(10),
                dp(10),
                dp(10),
                dp(10)
        );

        zeile.setBackground(
                abgerundetenHintergrundErstellen(
                        "#FFFFFF",
                        "#E2E8F0",
                        12
                )
        );

        LinearLayout.LayoutParams zeilenParameter =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        zeilenParameter.setMargins(
                0,
                0,
                0,
                dp(7)
        );

        zeile.setLayoutParams(
                zeilenParameter
        );

        TextView bezeichnungText =
                new TextView(this);

        bezeichnungText.setText(bezeichnung);

        bezeichnungText.setTextColor(
                Color.parseColor("#667085")
        );

        bezeichnungText.setTextSize(13);

        bezeichnungText.setTypeface(
                null,
                Typeface.BOLD
        );

        bezeichnungText.setLayoutParams(
                new LinearLayout.LayoutParams(
                        dp(115),
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        TextView wertText =
                new TextView(this);

        wertText.setText(wert);

        wertText.setTextColor(
                Color.parseColor("#101828")
        );

        wertText.setTextSize(13);
        wertText.setMaxLines(3);

        wertText.setEllipsize(
                TextUtils.TruncateAt.END
        );

        wertText.setLayoutParams(
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        zeile.addView(bezeichnungText);
        zeile.addView(wertText);

        eltern.addView(zeile);
    }

    /**
     * Aktualisiert die Zusammenfassung.
     */
    private void filterZusammenfassungAktualisieren() {

        String systemtext =
                ausgewaehltesSystem.isEmpty()
                        ? "Alle Systeme"
                        : ausgewaehltesSystem;

        filterZusammenfassungText.setText(
                ausgewaehlterKunde
                        + " · "
                        + systemtext
                        + "\n"
                        + zeitFormatieren(
                        ausgewaehlteStartzeit
                )
                        + " – "
                        + zeitFormatieren(
                        ausgewaehlteEndzeit
                )
                        + "\n"
                        + gefilterteInstanzen.size()
                        + " passende Datensätze"
        );
    }

    /**
     * Setzt die Filter zurück.
     */
    private void filterZuruecksetzen() {

        ausgewaehlterKunde = "";
        ausgewaehltesSystem = "";

        ausgewaehlteSchnelltage = 0;

        ausgewaehlteStartzeit = null;
        ausgewaehlteEndzeit = null;

        filterWurdeAngewendet = false;

        gefilterteInstanzen.clear();

        auswahlTexteAktualisieren();
        datumsTexteAktualisieren();
        schnellwahlAktualisieren();

        filterZusammenfassungText.setText(
                "Bitte Kunde und Zeitraum auswählen."
        );

        startzustandAnzeigen();
    }

    /**
     * Aktualisiert Kunde und System.
     */
    private void auswahlTexteAktualisieren() {

        kundenAuswahl.setText(
                ausgewaehlterKunde.isEmpty()
                        ? "Kunde auswählen"
                        : ausgewaehlterKunde
        );

        systemAuswahl.setText(
                ausgewaehltesSystem.isEmpty()
                        ? "Alle Systeme"
                        : ausgewaehltesSystem
        );
    }

    /**
     * Aktualisiert die Datumsfelder.
     */
    private void datumsTexteAktualisieren() {

        startdatumSchaltflaeche.setText(
                ausgewaehlteStartzeit == null
                        ? "Startdatum"
                        : "Von: "
                        + zeitFormatieren(
                        ausgewaehlteStartzeit
                )
        );

        enddatumSchaltflaeche.setText(
                ausgewaehlteEndzeit == null
                        ? "Enddatum"
                        : "Bis: "
                        + zeitFormatieren(
                        ausgewaehlteEndzeit
                )
        );
    }

    /**
     * Aktualisiert die Schnellwahl.
     */
    private void schnellwahlAktualisieren() {

        schnellwahlGestalten(
                einTagSchaltflaeche,
                ausgewaehlteSchnelltage == 1
        );

        schnellwahlGestalten(
                siebenTageSchaltflaeche,
                ausgewaehlteSchnelltage == 7
        );

        schnellwahlGestalten(
                fuenfzehnTageSchaltflaeche,
                ausgewaehlteSchnelltage == 15
        );

        schnellwahlGestalten(
                maximalerZeitraumSchaltflaeche,
                ausgewaehlteSchnelltage
                        == MAXIMALER_ZEITRAUM_TAGE
        );
    }

    /**
     * Gestaltet die Schnellwahl.
     */
    private void schnellwahlGestalten(
            TextView schaltflaeche,
            boolean ausgewaehlt
    ) {

        schaltflaeche.setTextColor(
                ausgewaehlt
                        ? Color.WHITE
                        : Color.parseColor("#344054")
        );

        schaltflaeche.setBackground(
                abgerundetenHintergrundErstellen(
                        ausgewaehlt
                                ? "#2563EB"
                                : "#F8FAFC",
                        ausgewaehlt
                                ? "#2563EB"
                                : "#CBD5E1",
                        20
                )
        );
    }

    /**
     * Stellt den Kunden wieder her.
     */
    private void kundenauswahlWiederherstellen() {

        if (ausgewaehlterKunde.isEmpty()) {
            return;
        }

        for (String kunde : kundenOptionen) {

            if (kunde.equalsIgnoreCase(
                    ausgewaehlterKunde
            )) {

                ausgewaehlterKunde = kunde;
                return;
            }
        }

        ausgewaehlterKunde = "";
    }

    /**
     * Stellt das System wieder her.
     */
    private void systemauswahlWiederherstellen() {

        if (ausgewaehltesSystem.isEmpty()) {
            return;
        }

        String normalisiertesSystem =
                systemNormalisieren(
                        ausgewaehltesSystem
                );

        for (String system : systemOptionen) {

            if (system.equalsIgnoreCase(
                    normalisiertesSystem
            )) {

                ausgewaehltesSystem = system;
                return;
            }
        }

        ausgewaehltesSystem = "";
    }

    /**
     * Ermittelt den Kundenindex.
     */
    private int ausgewaehltenKundenindexErmitteln() {

        for (int index = 0;
             index < kundenOptionen.size();
             index++) {

            if (kundenOptionen.get(index)
                    .equalsIgnoreCase(
                            ausgewaehlterKunde
                    )) {

                return index;
            }
        }

        return -1;
    }

    /**
     * Ermittelt den Systemindex.
     */
    private int ausgewaehltenSystemindexErmitteln(
            List<String> systeme
    ) {

        for (int index = 0;
             index < systeme.size();
             index++) {

            if (systemNormalisieren(
                    systeme.get(index)
            ).equalsIgnoreCase(
                    systemNormalisieren(
                            ausgewaehltesSystem
                    )
            )) {

                return index;
            }
        }

        return 0;
    }

    /**
     * Zeigt den Startzustand.
     */
    private void startzustandAnzeigen() {

        ergebnisKopfContainer.setVisibility(
                View.GONE
        );

        prozessContainer.setVisibility(
                View.GONE
        );

        prozessContainer.removeAllViews();

        leerzustandContainer.setVisibility(
                View.VISIBLE
        );

        leerzustandTitelText.setText(
                "Noch keine Prozesse ausgewählt"
        );

        leerzustandBeschreibungText.setText(
                "Wählen Sie einen Kunden und einen Zeitraum aus."
        );
    }

    /**
     * Zeigt einen Ladefehler.
     */
    private void fehlerzustandAnzeigen(
            String meldung
    ) {

        ergebnisKopfContainer.setVisibility(
                View.GONE
        );

        prozessContainer.setVisibility(
                View.GONE
        );

        leerzustandContainer.setVisibility(
                View.VISIBLE
        );

        leerzustandTitelText.setText(
                "Daten konnten nicht geladen werden"
        );

        leerzustandBeschreibungText.setText(
                meldung
        );
    }

    /**
     * Zeigt einen Filterfehler.
     */
    private void filterFehlerAnzeigen(
            String meldung
    ) {

        filterZusammenfassungText.setText(
                meldung
        );

        Toast.makeText(
                this,
                meldung,
                Toast.LENGTH_SHORT
        ).show();
    }

    /**
     * Fügt einen Wert nur einmal hinzu.
     */
    private void eindeutigenWertHinzufuegen(
            Set<String> werte,
            String neuerWert
    ) {

        if (neuerWert == null
                || neuerWert.trim().isEmpty()) {

            return;
        }

        for (String wert : werte) {

            if (wert.equalsIgnoreCase(
                    neuerWert
            )) {

                return;
            }
        }

        werte.add(neuerWert);
    }

    /**
     * Vereinheitlicht Systemwerte.
     */
    private String systemNormalisieren(
            String wert
    ) {

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

    /**
     * Entfernt leere Werte.
     */
    private String datenbankwertBereinigen(
            String wert
    ) {

        if (wert == null) {
            return "";
        }

        String ergebnis = wert.trim();

        if (ergebnis.isEmpty()
                || ergebnis.equalsIgnoreCase("null")
                || ergebnis.equalsIgnoreCase("NOT_SET")
                || ergebnis.equalsIgnoreCase("UNKNOWN")) {

            return "";
        }

        return ergebnis;
    }

    /**
     * Kürzt den Prozessnamen.
     */
    private String prozessnamenVerkuerzen(
            String prozessname
    ) {

        if (prozessname == null
                || prozessname.trim().isEmpty()) {

            return "Unbekannter Prozess";
        }

        String ergebnis =
                prozessname.trim();

        if (ergebnis.contains("}")) {

            ergebnis =
                    ergebnis.substring(
                            ergebnis.lastIndexOf("}") + 1
                    );
        }

        if (ergebnis.length() > 70) {

            return ergebnis.substring(
                    0,
                    70
            ) + "...";
        }

        return ergebnis;
    }

    /**
     * Wandelt Sekunden in Millisekunden um.
     */
    private Long inMillisekundenUmwandeln(
            Object zeitstempel
    ) {

        if (zeitstempel == null) {
            return null;
        }

        try {

            long wert =
                    zeitstempel instanceof Number
                            ? ((Number) zeitstempel)
                            .longValue()
                            : Long.parseLong(
                            zeitstempel.toString()
                    );

            if (wert <= 0) {
                return null;
            }

            if (String.valueOf(
                    Math.abs(wert)
            ).length() <= 10) {

                wert *= 1000;
            }

            return wert;

        } catch (Exception ausnahme) {

            return null;
        }
    }

    /**
     * Formatiert einen Zeitwert.
     */
    private String zeitFormatieren(
            Object zeitstempel
    ) {

        Long zeit =
                inMillisekundenUmwandeln(
                        zeitstempel
                );

        if (zeit == null) {
            return "-";
        }

        SimpleDateFormat format =
                new SimpleDateFormat(
                        "dd.MM.yyyy HH:mm",
                        Locale.GERMANY
                );

        format.setTimeZone(
                berlinerZeitzone
        );

        return format.format(
                new Date(zeit)
        );
    }

    /**
     * Erstellt einen Berliner Kalender.
     */
    private Calendar berlinerKalenderErstellen() {

        return Calendar.getInstance(
                berlinerZeitzone
        );
    }

    /**
     * Setzt einen Kalender auf Tagesbeginn.
     */
    private Calendar kalenderAufTagesbeginnSetzen(
            long zeit
    ) {

        Calendar kalender =
                berlinerKalenderErstellen();

        kalender.setTimeInMillis(zeit);

        kalender.set(
                Calendar.HOUR_OF_DAY,
                0
        );

        kalender.set(Calendar.MINUTE, 0);
        kalender.set(Calendar.SECOND, 0);
        kalender.set(Calendar.MILLISECOND, 0);

        return kalender;
    }

    /**
     * Gibt den Tagesbeginn zurück.
     */
    private long tagesbeginnErmitteln(
            long zeit
    ) {

        return kalenderAufTagesbeginnSetzen(
                zeit
        ).getTimeInMillis();
    }

    /**
     * Setzt Dialogfarben.
     */
    private void dialogFarbenSetzen(
            DatePickerDialog dialog
    ) {

        dialog.setOnShowListener(
                ignoriert -> {

                    dialog.getButton(
                            DialogInterface.BUTTON_POSITIVE
                    ).setTextColor(
                            Color.parseColor("#D90429")
                    );

                    dialog.getButton(
                            DialogInterface.BUTTON_NEGATIVE
                    ).setTextColor(
                            Color.parseColor("#667085")
                    );
                }
        );
    }

    /**
     * Gibt die aktuelle Uhrzeit zurück.
     */
    private String aktuelleUhrzeitErmitteln() {

        SimpleDateFormat format =
                new SimpleDateFormat(
                        "HH:mm",
                        Locale.GERMANY
                );

        format.setTimeZone(
                berlinerZeitzone
        );

        return format.format(
                new Date()
        );
    }

    /**
     * Erstellt den Eingabehintergrund.
     */
    private GradientDrawable eingabeHintergrundErstellen() {

        return abgerundetenHintergrundErstellen(
                "#F8FAFC",
                "#CBD5E1",
                17
        );
    }

    /**
     * Erstellt einen abgerundeten Hintergrund.
     */
    private GradientDrawable abgerundetenHintergrundErstellen(
            String fuellfarbe,
            String randfarbe,
            int radiusDp
    ) {

        GradientDrawable hintergrund =
                new GradientDrawable();

        hintergrund.setColor(
                Color.parseColor(fuellfarbe)
        );

        hintergrund.setCornerRadius(
                dp(radiusDp)
        );

        hintergrund.setStroke(
                dp(1),
                Color.parseColor(randfarbe)
        );

        return hintergrund;
    }

    /**
     * Wandelt dp in Pixel um.
     */
    private int dp(
            int wert
    ) {

        return (int) (
                wert
                        * getResources()
                        .getDisplayMetrics()
                        .density
        );
    }
}