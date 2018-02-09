package com.shitu.epathmapguide.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shitu.epathmapguide.R;
import com.shitu.epathmapguide.data.MapData;

import java.util.List;


/**
 * Created by thinkpad on 2017/12/13.
 */

public class CustomerMapAdapter extends BaseQuickAdapter<MapData, BaseViewHolder> {
    public CustomerMapAdapter(int layoutResId, @Nullable List<MapData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MapData item) {
        helper.setText(R.id.tv_name, item.getName());
        Glide.with(mContext).load(item.getPicture()).crossFade().into((ImageView) helper.getView(R.id.iv_img));
    }
}
