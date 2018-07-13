package net.rimoto.intlphoneinput;

public class Communicator {

    private static Communicator sInstance;

    private Country mCountry;
    private OnCountrySelectListener mListener;


    public void setCountry(Country country) {
        mCountry = country;
        if (mListener != null) {
            mListener.onCountryChanged(mCountry);
        }
    }

    public void setChangeListener(OnCountrySelectListener listener) {
        mListener = listener;
    }

    private Communicator(){

        //Prevent form the reflection api.
        if (sInstance != null){
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public synchronized static Communicator getInstance(){
        if (sInstance == null){ //if there is no instance available... create new one
            sInstance = new Communicator();
        }

        return sInstance;
    }

}
