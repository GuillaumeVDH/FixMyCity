package fr.fges.fixmycity.common.models;

/**
 * Created by Guillaume on 07/05/2016.
 */
public class Degradation {

    private String mImagePath;
    private String mReference;
    private String mDescription;

    public Degradation(String mImage, String mReference, String mDescription) {
        this.mImagePath = mImage;
        this.mReference = mReference;
        this.mDescription = mDescription;
    }

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
}
