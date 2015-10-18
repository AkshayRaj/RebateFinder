package ark.com.ibotta.data;

import java.util.List;

public class Offer {
    public static final String KEY_ID = "id";
    public static final String KEY_RETAILER_LIST = "retailers";
    public static final String KEY_NAME = "name";

    private int mId;
    private List<Integer> mRetailerList;
    private String mName;

    private Offer(int id, String name, List<Integer> retailerList){
        mId = id;
        mRetailerList = retailerList;
        mName = name;
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


    public static class OfferBuilder {
        private int OfferBuilder_mId;
        private List<Integer> OfferBuilder_mRetailerList;
        private String OfferBuilder_mName;

        public OfferBuilder(){

        }

        public Offer create(){
            return new Offer(OfferBuilder_mId,
                    OfferBuilder_mName,
                    OfferBuilder_mRetailerList);
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
    }
}
