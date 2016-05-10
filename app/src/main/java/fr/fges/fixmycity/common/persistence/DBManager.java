package fr.fges.fixmycity.common.persistence;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.fges.fixmycity.common.models.Degradation;

/**
 * Created by Guillaume on 10/05/2016.
 */
public class DBManager {

    private DatabaseHelper mHelper;
    private static DBManager mInstance;

    public static void Init(Context context) {
        if (mInstance == null)
            mInstance = new DBManager(context);
    }

    public static DBManager getInstance() {
        return mInstance;
    }

    private DBManager(Context context) {
        mHelper = new DatabaseHelper(context);
    }

    private DatabaseHelper getHelper() {
        return mHelper;
    }


    /** Methods [Degradation] **/

    public List<Degradation> getAllDegradations() {
        try {
            return getHelper().getDegradationDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Degradation>();
        }
    }

    public long createDegradation(Degradation degradation) {
        try {
            getHelper().getDegradationDao().create(degradation);
            return degradation.getmId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void removeDegradation(Degradation degradations) {
        try {
            getHelper().getDegradationDao().delete(degradations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeDegradations(Collection<Degradation> Degradations) {
        try {
            getHelper().getDegradationDao().delete(Degradations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long updateDegradation(Degradation degradation) {
        try {
            getHelper().getDegradationDao().update(degradation);
            return degradation.getmId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Degradation> getDegradationsByName(String name) {
        try {
            return getHelper().getDegradationDao().queryForEq("name", name);
        } catch (SQLException e) {
            e.printStackTrace();
            List<Degradation> degradationList = new ArrayList<>();
            return degradationList;
        }
    }

    public Degradation getDegradationById(long id) {
        try {
            return getHelper().getDegradationDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Degradation();
        }
    }

    public long countNbDegradations() {
        try {
            return getHelper().getDegradationDao().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
