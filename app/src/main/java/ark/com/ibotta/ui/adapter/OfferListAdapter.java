package ark.com.ibotta.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ark.com.ibotta.R;
import ark.com.ibotta.cloud.VolleyHelpers.PosterLoader;
import ark.com.ibotta.model.Offer;

public class OfferListAdapter extends BaseAdapter {
    private List<Offer> mOfferList;
    LayoutInflater mInflater;
    private Context mContext;

    public OfferListAdapter(Context context, List<Offer> offerList) {
        mContext = context;
        mOfferList = offerList;
        mInflater = LayoutInflater.from(mContext);
        if(mOfferList.size() == 0){
            mOfferList.add(new Offer.OfferBuilder()
                    .setId(0)
                    .setName("NO OFFERS NEARBY")
                    .setRetailerList(null)
                    .create());
        }
    }

    public void setOfferList(List<Offer> offers){
        mOfferList = offers;
    }

    @Override
    public int getCount() {
        return mOfferList.size();
    }

    @Override
    public Offer getItem(int position) {
        return mOfferList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OfferViewHolder offerViewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_list_item, parent, false);
            offerViewHolder = new OfferViewHolder(convertView);
            convertView.setTag(offerViewHolder);
        } else {
            offerViewHolder = (OfferViewHolder) convertView.getTag();
        }

        Offer offer = getItem(position);
        //new PosterLoader(offerViewHolder.offerImage).execute(offer.getImagePath());
        offerViewHolder.offerId.setText(String.valueOf(offer.getId()));
        offerViewHolder.offerName.setText(offer.getName());

        return convertView;

    }

    private class OfferViewHolder {
        ImageView offerImage;
        TextView offerId;
        TextView offerName;

        public OfferViewHolder(View item) {
            offerImage = (ImageView) item.findViewById(R.id.offerIcon);
            offerId = (TextView) item.findViewById(R.id.offerId);
            offerName = (TextView) item.findViewById(R.id.offerName);
        }
    }
}
