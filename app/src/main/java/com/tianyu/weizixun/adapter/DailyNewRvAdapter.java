package com.tianyu.weizixun.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.bean.DailyNewsBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyNewRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<DailyNewsBean.StoriesBean> datas;
    private ArrayList<DailyNewsBean.TopStoriesBean> bannerDatas;
    private final LayoutInflater from;

    public DailyNewRvAdapter(Context context, ArrayList<DailyNewsBean.StoriesBean> datas, ArrayList<DailyNewsBean.TopStoriesBean> bannerDatas) {
        this.context = context;
        this.datas = datas;
        this.bannerDatas = bannerDatas;
        from = LayoutInflater.from(context);
    }

    private int VIEW_TYPE_ONE = 1;
    private int VIEW_TYPE_TWO = 2;
    private int VIEW_TYPE_THREE = 3;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ONE) {
            View view = from.inflate(R.layout.layout_daily_banner, parent, false);
            return new ViewHolderOne(view);
        } else if (viewType == VIEW_TYPE_TWO) {
            View view = from.inflate(R.layout.layout_daily_title, parent, false);
            return new ViewHolderTwo(view);
        } else {
            View view = from.inflate(R.layout.layout_daily_item, parent, false);
            return new ViewHolderThree(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        if (viewType == VIEW_TYPE_ONE) {
            ArrayList<String> titles = new ArrayList<>();
            ArrayList<String> images = new ArrayList<>();
            for (int i = 0; i < bannerDatas.size(); i++) {
                titles.add(bannerDatas.get(i).getTitle());
                images.add(bannerDatas.get(i).getImage());
            }
            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            viewHolderOne.dailyBanner
                    .setBannerTitles(titles)
                    .setImages(images)
                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context).load(path).into(imageView);
                        }
                    })
                    .start();
        } else if (viewType == VIEW_TYPE_TWO) {

        } else {
            ViewHolderThree viewHolderThree = (ViewHolderThree) holder;
            viewHolderThree.tvDailyTitle.setText(datas.get(position - 2).getTitle());
            //得到的images是个集合
            List<String> images = datas.get(position - 2).getImages();
            for (String image : images) {
                Glide.with(context)
                        .load(image)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(viewHolderThree.ivDailyImg);
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_ONE;
        } else if (position == 1) {
            return VIEW_TYPE_TWO;
        } else {
            return VIEW_TYPE_THREE;
        }
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        @BindView(R.id.daily_banner)
        Banner dailyBanner;

        ViewHolderOne(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        @BindView(R.id.daily_title)
        TextView dailyTitle;

        ViewHolderTwo(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderThree extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_daily_img)
        ImageView ivDailyImg;
        @BindView(R.id.tv_daily_title)
        TextView tvDailyTitle;

        ViewHolderThree(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
