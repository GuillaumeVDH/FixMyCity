package fr.fges.fixmycity.common.services;

import java.util.List;
import java.util.Set;

import fr.fges.fixmycity.common.models.Degradation;

/**
 * Created by Guillaume on 10/05/2016.
 */
public class DegradationServicesImpl implements DegradationService {


    @Override
    public long addDegradation(Degradation degradation) {
        return degradation.save();
    }

    @Override
    public boolean deleteDegradation(Degradation degradation) {
        return degradation.delete();
    }

    @Override
    public Degradation findDegradationById(long id) {
        return Degradation.findById(Degradation.class, id);
    }

    @Override
    public List<Degradation> findAllDegradations() {
        return Degradation.listAll(Degradation.class);
    }

    @Override
    public long countNbDegradations() {
        return Degradation.count(Degradation.class, null, null);
    }
}
