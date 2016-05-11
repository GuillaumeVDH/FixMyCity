package fr.fges.fixmycity.common.persistence;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.fges.fixmycity.common.models.Degradation;

/**
 * Created by Guillaume
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

    public DatabaseHelper getHelper() {
        return mHelper;
    }
}
