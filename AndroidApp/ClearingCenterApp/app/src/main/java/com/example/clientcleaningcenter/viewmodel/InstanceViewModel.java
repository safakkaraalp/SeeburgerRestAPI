package com.example.clientcleaningcenter.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clientcleaningcenter.model.BpInstance;
import com.example.clientcleaningcenter.repository.InstanceRepository;

import java.util.List;

public class InstanceViewModel extends ViewModel {

    private final InstanceRepository instanceRepository = new InstanceRepository(); //Hier wird das Repository erstellt.

    public MutableLiveData<List<BpInstance>> instances = new MutableLiveData<>();            //MutableLiveData wird verwendet, weil sich diese Werte während der Laufzeit ändern.
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();                  //Diese LiveData-Objekte werden von der Activity beobachtet.
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public void loadInstances() {
        loading.setValue(true);

        instanceRepository.loadInstances(new InstanceRepository.InstanceCallback() { //Hier wird InstanceRepository aufgerufen und werden die Prozessdaten laden lassen.
            @Override
            public void onSuccess(List<BpInstance> data) { //loading wird am Anfang auf true gesetzt, weil der Ladevorgang startet.
                // Wenn die Daten erfolgreich geladen wurden oder ein Fehler passiert, wird loading wieder auf false gesetzt. Activity weißt nun dass der Prozess beendet ist.
                // Wenn es True gesetzt wäre, könnte Refresh Button nicht reagieren.
                loading.setValue(false);
                instances.setValue(data);
            }

            @Override
            public void onError(String message) {
                loading.setValue(false);
                errorMessage.setValue(message);
            }
        });
    }
}