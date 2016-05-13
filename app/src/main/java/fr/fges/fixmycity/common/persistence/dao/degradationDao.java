package fr.fges.fixmycity.common.persistence.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.fges.fixmycity.common.models.Degradation;
import fr.fges.fixmycity.common.persistence.DBManager;
import fr.fges.fixmycity.common.persistence.DatabaseHelper;

/**
 * Created by Guillaume
 */
public class degradationDao {

    private DBManager mManager;
    private DatabaseHelper mHelper;

    public degradationDao() {
        mManager = DBManager.getInstance();
        mHelper = mManager.getHelper();
    }

    public List<Degradation> getAllDegradations() {
        try {
            return mHelper.getDegradationDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Degradation>();
        }
    }

    public long createDegradation(Degradation degradation) {
        try {
            mHelper.getDegradationDao().create(degradation);
            return degradation.getmId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void removeDegradation(Degradation degradations) {
        try {
            mHelper.getDegradationDao().delete(degradations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeDegradations(Collection<Degradation> Degradations) {
        try {
            mHelper.getDegradationDao().delete(Degradations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long updateDegradation(Degradation degradation) {
        try {
            mHelper.getDegradationDao().update(degradation);
            return degradation.getmId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Degradation> getDegradationsByName(String name) {
        try {
            return mHelper.getDegradationDao().queryForEq("name", name);
        } catch (SQLException e) {
            e.printStackTrace();
            List<Degradation> degradationList = new ArrayList<>();
            return degradationList;
        }
    }

    public Degradation getDegradationById(long id) {
        try {
            return mHelper.getDegradationDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Degradation();
        }
    }

    public long countNbDegradations() {
        try {
            return mHelper.getDegradationDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
