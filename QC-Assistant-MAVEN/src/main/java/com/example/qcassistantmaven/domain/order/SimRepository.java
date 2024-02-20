package com.example.qcassistantmaven.domain.order;

import com.example.qcassistantmaven.domain.item.sim.SerializedSIM;

import java.util.ArrayList;
import java.util.Collection;

public class SimRepository {

    private Collection<SerializedSIM> sims;

    public SimRepository(){
        this.sims = new ArrayList<>();
    }

    public void addSim(SerializedSIM serializedSIM) {
        sims.add(serializedSIM);
    }

    public Collection<SerializedSIM> getSims() {
        return sims;
    }

    public SimRepository setSims(Collection<SerializedSIM> sims) {
        this.sims = sims;
        return this;
    }

    public boolean isEmpty() {
        return this.sims.isEmpty();
    }
}
