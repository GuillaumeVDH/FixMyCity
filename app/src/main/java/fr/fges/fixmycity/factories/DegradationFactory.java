package fr.fges.fixmycity.factories;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fr.fges.fixmycity.common.models.Degradation;

/**
 * Created by Guillaume on 07/05/2016.
 */
public class DegradationFactory {

    private static DegradationFactory mInstance = null;


    private List<Degradation> mDegradationList = new ArrayList<>();


    String mDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
            "Curabitur orci odio, tincidunt et tincidunt nec, mattis ac mauris. Vivamus vel " +
            "lorem in turpis fringilla bibendum. In gravida ipsum sed odio malesuada, quis " +
            "ultricies sem consequat. Nulla at felis neque. Suspendisse potenti. Interdum et " +
            "malesuada fames ac ante ipsum primis in faucibus. Proin pulvinar tristique urna, " +
            "eget vehicula nisl luctus in. Duis laoreet venenatis purus quis accumsan. Aliquam " +
            "volutpat, nunc id sollicitudin facilisis, lorem dolor ultrices velit, et convallis " +
            "metus massa nec erat. Nullam finibus semper odio, id egestas sapien. Suspendisse " +
            "eget lobortis lectus, eu finibus magna. Maecenas lacinia dolor vel sem sodales, " +
            "scelerisque condimentum nisi luctus. Aenean ut urna vel risus ultrices tempus. " +
            "Donec malesuada sem a varius sollicitudin. Suspendisse ac lobortis nulla." +
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ";

    public DegradationFactory() {
        mDegradationList.add(new Degradation(null, "ABB7RF1", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF2", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF3", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF4", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF5", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF6", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF7", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF8", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF9", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF10", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF11", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF12", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF13", mDescription));
        mDegradationList.add(new Degradation(null, "ABB7RF14", mDescription));

        this.initPhotosToList();
    }

    public void addDegradation(Degradation degradation) {
        if(degradation != null)
            this.mDegradationList.add(degradation);
        else
            System.out.println("Can't add degradation (null object)"); //TODO
    }

    private void initPhotosToList() {
        File storageDir = new File(Environment.getExternalStorageDirectory()+"/FixMyCity/pictures/");
        File[] photos = storageDir.listFiles();
        if(photos != null) {
            int start = 0;
            int end = photos.length;
            for(Degradation degradation : mDegradationList) {
                double r = Math.random() * (end - start);

                File photo = photos[(int) r];
                if(photo.exists()){
                    degradation.setmImagePath(photo.getAbsolutePath());
                }
            }
        }
    }

    public List<Degradation> getmDegradationList() {
        return mDegradationList;
    }

    public void setmDegradationList(List<Degradation> mDegradationList) {
        this.mDegradationList = mDegradationList;
    }


    public static DegradationFactory getInstance(){
        if(mInstance == null)
        {
            mInstance = new DegradationFactory();
        }
        return mInstance;
    }
}
