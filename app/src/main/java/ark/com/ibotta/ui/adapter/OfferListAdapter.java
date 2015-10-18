package ark.com.ibotta.ui.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ark.com.ibotta.R;

public class OfferListAdapter {
    private List<Offer> mOfferList;
    LayoutInflater mInflater;
    private Context mContext;

    public OfferListAdapter(Context context, List<Offer> offerList) {
        mContext = context;
        mOfferList = offerList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setOfferList(List<Movie> trips){
        mOfferList = trips;
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

//        new PosterLoader(mViewHolder.moviePoster).execute(Movie.POSTER_URL + movie.getPosterPath());
        offerViewHolder.offerId.setText(offer.getId);
        offerViewHolder.offerName.setText(offer.getName);

        return convertView;

    }

    private class OfferViewHolder {
        TextView offerId;
        TextView offerName;

        public OfferViewHolder(View item) {
            offerId = (TextView) item.findViewById(R.id.offerId);
            offerName = (TextView) item.findViewById(R.id.offerName);
        }
    }
}
