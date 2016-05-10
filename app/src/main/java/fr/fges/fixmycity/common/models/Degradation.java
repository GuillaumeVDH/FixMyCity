package fr.fges.fixmycity.common.models;

import com.orm.SugarRecord;

/**
 * Created by Guillaume on 07/05/2016.
 */
public class Degradation extends SugarRecord {

    private String mImagePath;
    private String mReference;
    private String mCategory;
    private String mDescription;

    public Degradation() {
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
}
