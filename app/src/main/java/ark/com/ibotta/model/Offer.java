package ark.com.ibotta.model;

import java.util.List;

public class Offer implements Comparable{
    public static final String KEY_ID = "id";
    public static final String KEY_RETAILER_LIST = "retailers";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE_URL = "url";
    public static final String KEY_EARNINGS_POTENTIAL = "earnings_potential";
    public static final String KEY_EXPIRATION = "expiration";

    private int mId;
    private List<Integer> mRetailerList;
    private String mName;
    private String mImageURL;
    private double mEarningsPotential;
    private String mExpiration;

    private Offer(int id, String name, List<Integer> retailerList, String imageURL,
                  double earningsPotential, String expiration){
        mId = id;
        mRetailerList = retailerList;
        mName = name;
        mImageURL = imageURL;
        mEarningsPotential = earningsPotential;
        mExpiration = expiration;
    }

    public List<Integer> getRetailerList() {
        return mRetailerList;
    }

    public void setRetailerList(List<Integer> retailerList) {
        mRetailerList = retailerList;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }

    public double getEarningsPotential() {
        return mEarningsPotential;
    }

    public void setEarningsPotential(double mEarningsPotential) {
        this.mEarningsPotential = mEarningsPotential;
    }

    //user gets Offer in descending sorted order based on EarningsPotential
    @Override
    public int compareTo(Object another) {
            double earningPotential=((Offer)another).getEarningsPotential();
            /* For Descending order*/
            return (int) (earningPotential-this.getEarningsPotential());
    }

    public String getExpiration() {
        return mExpiration;
    }

    public void setExpiration(String mExpiration) {
        this.mExpiration = mExpiration;
    }

    public static class OfferBuilder {
        private int OfferBuilder_mId;
        private List<Integer> OfferBuilder_mRetailerList;
        private String OfferBuilder_mName;
        private String OfferBuilder_mImageURL;
        private double OfferBuilder_mEarningsPotential;
        private String OfferBuilder_mExpiration;

        public OfferBuilder(){

        }

        public Offer create(){
            return new Offer(OfferBuilder_mId,
                    OfferBuilder_mName,
                    OfferBuilder_mRetailerList,
                    OfferBuilder_mImageURL,
                    OfferBuilder_mEarningsPotential,
                    OfferBuilder_mExpiration);
        }

        public List<Integer> getRetailerList() {
            return OfferBuilder_mRetailerList;
        }

        public OfferBuilder setRetailerList(List<Integer> offerBuilder_mRetailerList) {
            OfferBuilder_mRetailerList = offerBuilder_mRetailerList;
            return this;
        }

        public int getId() {
            return OfferBuilder_mId;
        }

        public OfferBuilder setId(int offerBuilder_mId) {
            OfferBuilder_mId = offerBuilder_mId;
            return this;
        }

        public String getName() {
            return OfferBuilder_mName;
        }

        public OfferBuilder setName(String offerBuilder_mName) {
            OfferBuilder_mName = offerBuilder_mName;
            return this;
        }

        public String getImageURL() {
            return OfferBuilder_mImageURL;
        }

        public OfferBuilder setImageURL(String offerBuilder_mImageURL) {
            OfferBuilder_mImageURL = offerBuilder_mImageURL;
            return this;
        }

        public double getEarningsPotential() {
            return OfferBuilder_mEarningsPotential;
        }

        public OfferBuilder setEarningsPotential(double offerBuilder_mEarningsPotential) {
            OfferBuilder_mEarningsPotential = offerBuilder_mEarningsPotential;
            return this;
        }

        public String getOfferBuilder_mExpiration() {
            return OfferBuilder_mExpiration;
        }

        public OfferBuilder setExpiration(String offerBuilder_mExpiration) {
            OfferBuilder_mExpiration = offerBuilder_mExpiration;
            return this;
        }

    }
}
