package com.gzw.mp.fragments;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.gzw.mp.R;
import com.gzw.mp.activities.NewUserComeActivity;
import com.gzw.mp.adapter.FindBannerAdapter;
import com.gzw.mp.adapter.FindTopicAdapter;
import com.gzw.mp.base.BaseFragment;
import com.gzw.mp.bean.FindBean;
import com.gzw.mp.utils.APIUtils;
import com.gzw.mp.utils.JsonParser;
import com.gzw.mp.utils.UIHelper;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends BaseFragment {
	List<FindBean> list =new ArrayList<>();
	List<FindBean.Data> datas =new ArrayList<>();
	@ViewInject(R.id.viewPager_find_banner)
	ViewPager banner;
	@ViewInject(R.id.gridView_topic)
	GridView topic;
	List<FindBean.Data> bannerData;
	public List<ImageView> views=new ArrayList<>();

	private Handler handler =new Handler(){
		@Override
		public void handleMessage(Message msg) {
			banner.setCurrentItem(banner.getCurrentItem()+1);
			sendEmptyMessageDelayed(1, 300);
		}
	};

	@Override
	protected void initView() {
		final String link = APIUtils.getFindLink();
		UIHelper.getStringFromNet(link, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				list =JsonParser.getFindList(responseInfo.result);
				List<FindBean.Data> topicData =new ArrayList<>();
				topicData=list.get(1).getData();
				FindTopicAdapter topicAdapter =new FindTopicAdapter(getActivity(),topicData);
				topic.setAdapter(topicAdapter);

				bannerData=new ArrayList<>();
				bannerData=list.get(0).getData();
				initImages();
				FindBannerAdapter bannerAdapter =new FindBannerAdapter(views);
				banner.setAdapter(bannerAdapter);
				handler.sendEmptyMessageDelayed(1,1000);
			}

			@Override
			public void onFailure(HttpException e, String s) {

			}
		});
	}

	//TODO 需要异步加载
	private void initImages() {
		for(int i=0;i<bannerData.size();i++){
			ImageView imageView =new ImageView(getActivity());
			ImageLoader.getInstance().displayImage(bannerData.get(i).getPicture(),imageView);
			views.add(imageView);
		}
	}

	@Override
	protected void initAction() {

	}

	@OnClick(R.id.image_new_user)
	public void newUser(View view){
		Intent intent =new Intent(getActivity(),NewUserComeActivity.class);
		getActivity().startActivity(intent);
	}

	@Override
	public int getViewId() {
		return R.layout.fragment_find;
	}
}
