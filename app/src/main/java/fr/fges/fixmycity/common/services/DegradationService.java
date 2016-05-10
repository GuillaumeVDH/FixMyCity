package fr.fges.fixmycity.common.services;

import java.util.List;

import fr.fges.fixmycity.common.models.Degradation;

/**
 * Created by Guillaume on 10/05/2016.
 */
public interface DegradationService {

    long addDegradation(Degradation degradation);

    boolean deleteDegradation(Degradation degradation);

    Degradation findDegradationById(long id);

    List<Degradation> findAllDegradations();

    long countNbDegradations();
}