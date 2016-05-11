package fr.fges.fixmycity.common.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import fr.fges.fixmycity.common.Constants;
import fr.fges.fixmycity.common.models.Degradation;

/**
 * Created by Guillaume
 */
public class DatabaseHelper  extends OrmLiteSqliteOpenHelper {

    /**
     * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
     */
    private Dao<Degradation, Long> degradationDao;

    public DatabaseHelper(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Degradation.class);
        } catch (SQLException e) {
            Log.e( "Can't create database", e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Degradation.class, true);
            onCreate(database);
        } catch (SQLException e) {
            Log.e("Impossible to drop db", e.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns an instance of the data access object
     * @return
     * @throws SQLException
     */
    public Dao<Degradation, Long> getDegradationDao() throws SQLException {
        if(degradationDao == null) {
            degradationDao = getDao(Degradation.class);
        }
        return degradationDao;
    }
}
