package ark.com.ibotta.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
        CategoryViewHolder categoryViewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_offer_list, parent, false);
            categoryViewHolder = new CategoryViewHolder(convertView);
            convertView.setTag(categoryViewHolder);
        } else {
            categoryViewHolder = (CategoryViewHolder) convertView.getTag();
        }

        Offer offer = getItem(position);
        new PosterLoader(categoryViewHolder.offerImage).execute(offer.getImageURL());
        categoryViewHolder.offerName.setText(offer.getName());
        categoryViewHolder.offerEarningPotential.setText("REBATE: \n" + String.valueOf(offer.getEarningsPotential()));
        categoryViewHolder.offerExpiration.setText("EXPIRES: \n" + offer.getExpiration());
        return convertView;

    }

    private class CategoryViewHolder {
        ImageView offerImage;
        TextView offerEarningPotential;
        TextView offerName;
        TextView offerExpiration;

        public CategoryViewHolder(View item) {
            offerImage = (ImageView) item.findViewById(R.id.offerIcon);
            offerName = (TextView) item.findViewById(R.id.offerName);
            offerEarningPotential = (TextView) item.findViewById(R.id.offerEarningPotential);
            offerExpiration = (TextView) item.findViewById(R.id.offerExpiration);
        }
    }
}
