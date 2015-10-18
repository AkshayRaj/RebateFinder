package ark.com.ibotta.model;

/**
 * Created by Akshayraj on 10/18/15.
 */
public class Store {

    public static final String KEY_ID = "id";
    public static final String KEY_RETAILER_ID = "retailer_id";
    public static final String KEY_LATITUDE = "lat";
    public static final String KEY_LONGITUDE = "long";

    private int mId;
    private int mRetailerId;
    private double mLatitude;
    private double mLongitude;

    private Store(int id, int retailerId, double latitude, double longitude){
        mId = id;
        mRetailerId = retailerId;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getRetailerId() {
        return mRetailerId;
    }

    public void setRetailerId(int retailerId) {
        mRetailerId = retailerId;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public static class StoreBuilder{

        private int StoreBuilder_mId;
        private int StoreBuilder_mRetailerId;
        private double StoreBuilder_mLatitude;
        private double StoreBuilder_mLongitude;

        public StoreBuilder(){

        }

        public Store create(){
            return new Store(StoreBuilder_mId,
                    StoreBuilder_mRetailerId,
                    StoreBuilder_mLatitude,
                    StoreBuilder_mLongitude);
        }

        public int getId() {
            return StoreBuilder_mId;
        }

        public StoreBuilder setId(int mId) {
            this.StoreBuilder_mId = mId;
            return this;
        }

        public int getRetailerId() {
            return StoreBuilder_mRetailerId;
        }

        public StoreBuilder setRetailerId(int mRetailerId) {
            this.StoreBuilder_mRetailerId = mRetailerId;
            return this;
        }

        public double getLatitude() {
            return StoreBuilder_mLatitude;
        }

        public StoreBuilder setLatitude(double mLatitude) {
            this.StoreBuilder_mLatitude = mLatitude;
            return this;
        }

        public double getLongitude() {
            return StoreBuilder_mLongitude;
        }

        public StoreBuilder setLongitude(double mLongitude) {
            this.StoreBuilder_mLongitude = mLongitude;
            return this;
        }

    }
}
