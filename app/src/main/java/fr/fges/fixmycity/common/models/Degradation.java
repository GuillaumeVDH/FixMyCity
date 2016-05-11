package fr.fges.fixmycity.common.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Guillaume on 07/05/2016.
 */
@DatabaseTable(tableName = "degradations")
public class Degradation {

    @DatabaseField(generatedId = true, columnName = "degradation_id")
    private long mId;

    @DatabaseField()
    private String mReference;
    @DatabaseField
    private String mCategory;
    @DatabaseField
    private String mDescription;
    @DatabaseField
    private String mImagePath;

    public Degradation() {
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmImagePath() {
        return mImagePath;
    }

    public void setmImagePath(String mImage) {
        this.mImagePath = mImage;
    }

    public String getmReference() {
        return mReference;
    }

    public void setmReference(String mReference) {
        this.mReference = mReference;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    @Override
    public String toString() {
        return "Degradation{" +
                "mId=" + mId +
                ", mReference='" + mReference + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mImagePath='" + mImagePath + '\'' +
                '}';
    }
}
