package tips.listviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by qiuyu on 16-10-6.
 */

public class DemoAdapter extends ArrayAdapter<ListItemBean> {

    private int mResource;//ListView子项布局

    public DemoAdapter(Context context, int resource, List<ListItemBean> objects) {
        super(context, resource, objects);
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //取出数据源中的数据对象
        ListItemBean bean = getItem(position);

        View view;//ListView中子项的布局
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(mResource, null);
            viewHolder = new ViewHolder();

            viewHolder.mTextViewTitle = (TextView) view.findViewById(R.id.tv_item_title);
            viewHolder.mTextViewContent = (TextView) view.findViewById(R.id.tv_item_content);
            viewHolder.mImageViewIcon = (ImageView) view.findViewById(R.id.iv_item_icon);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.mTextViewTitle.setText(bean.getTitle());
        viewHolder.mTextViewContent.setText(bean.getContent());
        viewHolder.mImageViewIcon.setImageResource(bean.getImageId());

        return view;
    }

    class ViewHolder {

        TextView mTextViewTitle;
        TextView mTextViewContent;
        ImageView mImageViewIcon;
    }
}
