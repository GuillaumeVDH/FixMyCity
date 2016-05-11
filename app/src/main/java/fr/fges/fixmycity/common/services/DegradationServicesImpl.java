package fr.fges.fixmycity.common.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import fr.fges.fixmycity.common.models.Degradation;
import fr.fges.fixmycity.common.persistence.DBManager;

import static com.j256.ormlite.android.apptools.OpenHelperManager.getHelper;

/**
 * Created by Guillaume on 10/05/2016.
 */
public class DegradationServicesImpl implements DegradationService {

    private DBManager mManager;

    public DegradationServicesImpl() {
        mManager = DBManager.getInstance();
    }

    @Override
    public long addDegradation(Degradation degradation) {
        return mManager.createDegradation(degradation);
    }

    @Override
    public long updateDegradation(Degradation degradation) {
        return mManager.updateDegradation(degradation);
    }

    @Override
    public void deleteDegradation(Degradation degradation) {
        mManager.removeDegradation(degradation);
    }

    @Override
    public Degradation findDegradationById(long id) {
        return mManager.getDegradationById(id);
    }

    @Override
    public List<Degradation> findAllDegradations() {
        return mManager.getAllDegradations();
    }

    @Override
    public long countNbDegradations() {
        return mManager.countNbDegradations();
    }
}
