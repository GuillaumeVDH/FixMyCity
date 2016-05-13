package fr.fges.fixmycity.common.services;

import java.util.List;

import fr.fges.fixmycity.common.models.Degradation;
import fr.fges.fixmycity.common.persistence.dao.degradationDao;

import static com.j256.ormlite.android.apptools.OpenHelperManager.getHelper;

/**
 * Created by Guillaume on 10/05/2016.
 */
public class DegradationServicesImpl implements DegradationService {

    private fr.fges.fixmycity.common.persistence.dao.degradationDao degradationDao;

    public DegradationServicesImpl() {
        degradationDao = new degradationDao();
    }

    @Override
    public long addDegradation(Degradation degradation) {
        return degradationDao.createDegradation(degradation);
    }

    @Override
    public long updateDegradation(Degradation degradation) {
        return degradationDao.updateDegradation(degradation);
    }

    @Override
    public void deleteDegradation(Degradation degradation) {
        degradationDao.removeDegradation(degradation);
    }

    @Override
    public Degradation findDegradationById(long id) {
        return degradationDao.getDegradationById(id);
    }

    @Override
    public List<Degradation> findAllDegradations() {
        return degradationDao.getAllDegradations();
    }

    @Override
    public long countNbDegradations() {
        return degradationDao.countNbDegradations();
    }
}
