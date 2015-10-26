package ark.com.ibotta.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ark.com.ibotta.R;
/*

*/

public class CategoryListAdapter extends BaseAdapter{
    private List<String> mCategoryList;
    LayoutInflater mInflater;
    private Context mContext;

    public CategoryListAdapter(Context context, List<String> categoryList) {
        mContext = context;
        mCategoryList = categoryList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setCategoryList(List<String> categories){
        mCategoryList = categories;
    }

    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public String getItem(int position) {
        return mCategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryViewHolder categoryViewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_category, parent, false);
            categoryViewHolder = new CategoryViewHolder(convertView);
            convertView.setTag(categoryViewHolder);
        } else {
            categoryViewHolder = (CategoryViewHolder) convertView.getTag();
        }

        String name = getItem(position);
        categoryViewHolder.categoryName.setText(name);
        categoryViewHolder.categoryCheckbox.setChecked(false);
        return convertView;

    }

    private class CategoryViewHolder {
        TextView categoryName;
        CheckBox categoryCheckbox;

        public CategoryViewHolder(View item) {
            categoryName = (TextView) item.findViewById(R.id.item_category_textview);
            categoryCheckbox = (CheckBox) item.findViewById(R.id.item_category_checkbox);
        }
    }
}
