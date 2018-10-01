package com.gotop.write_package;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ReadFragment extends Fragment {
	private List<Info> mlistInfo = new ArrayList<Info>();
	ListView listView;
	//Map<String,String> info = new HashMap<String,String>();
	List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = (ListView)getActivity().findViewById(R.id.listView);
		setInfo();
		SimpleAdapter adapter = new SimpleAdapter(getContext(),listItems,R.layout.ruanjianitem,new String[]{"title"},new int[]{R.id.title1});

		//SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.items, new String[]{"title","image"},new int[]{R.id.title,R.id.image});


	}

	@Override
	public void onStart() {
		super.onStart();


	}
	public void setInfo() {
		mlistInfo.clear();

	}
		Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);


			Toast.makeText(getContext(),"更新成功！", Toast.LENGTH_LONG).show();


		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		return inflater.inflate(R.layout.ruanjianmain, container, false);
	}

	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
	}

}
