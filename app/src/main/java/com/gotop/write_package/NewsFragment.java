package com.gotop.write_package;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.*;

public class NewsFragment extends Fragment {
	String bendibanben = "0";
	String netbanben = "0";
	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final String [] tuijianbaimingdan={"com.etong",
			"com.chinapost",//邮政
			"com.iflytek",//讯飞
			"com.fcbox",//蜂巢
			"com.moge",//格格
			"com.yungui",//云柜
			"cn.sudiyi",//速递易
			"com.unicom",//联通
			"com.tencent.mm",//微信
			"com.tencent.mobileqq",//QQ
			"cn.dnt.smjd",//实名寄递
			"cn.aiqy.js_postman",//江苏邮管局
			"com.eg.android.AlipayGphone",//支付宝
			"com.cainiao",//菜鸟
			"cn.chinapost",   //邮政
			"com.ataaw.tianyi",//天翼生活
			"com.jsmcc",//行商客户
			"com.szh",
			"cn.szh"
	};
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}


	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String net = data.getString("net");
			String bendi = data.getString("bendi");
			//Log.i("mylog", "请求结果为-->" + val);
			// TODO
			// UI界面的更新等相关操作
			//TextView twnet = (TextView)getActivity().findViewById(R.id.textView5);
			//TextView twbendi = (TextView)getActivity().findViewById(R.id.textView3);
			//twnet.setText(dateformat.format(Long.valueOf(net)));
			netbanben = net;
			//twbendi.setText(dateformat.format(Long.valueOf(bendi)));
			bendibanben = bendi;

		}
	};


	public String netbanben(){
		OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
		Request request = new Request.Builder().url("http://pda.wjems.cn:9898/baimingdan/version")
				.get().build();
		Call call = client.newCall(request);
		String net = "00";
		try {
			Response response = call.execute();
			net = response.body().string();

		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.e(this.getClass().getName(),net);
		if("00".equals(net)){
			return "2513586818209";
		}
		return new String(Base64.decode(net, android.util.Base64.DEFAULT));

	}
	public String bendibanben(){
		File f = new File("/data/apkins/package_ins_cfg_version");
		if(f.exists()){
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				String version = br.readLine();

				br.close();
				return version;
			}catch (Exception e){
				e.printStackTrace();
				return "0";
			}
		}else{
			return "0";
		}
	}
	/**
	 * 网络操作相关的子线程
	 */
	Runnable networkTask = new Runnable() {

		@Override
		public void run() {
			// TODO
			// 在这里进行 http request.网络请求相关操作
			Bundle data = new Bundle();

			data.putString("net",netbanben());
			data.putString("bendi",bendibanben());
			Message msg = new Message();

			msg.setData(data);

			handler.sendMessage(msg);



		}
	};
	@Override
	public void onStart() {
		super.onStart();
		//new Thread(networkTask).start();
	}

	Runnable gengxinTask = new Runnable() {

		@Override
		public void run() {
			// TODO
			// 在这里进行 http request.网络请求相关操作
			gengxinbanben();
			Bundle data = new Bundle();

			data.putString("net","");
			Message msg = new Message();
			handler2.sendMessage(msg);

			new Thread(networkTask).start();

		}
	};
	Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			Toast.makeText(getContext(),"更新成功！", Toast.LENGTH_LONG).show();


		}
	};

	public void gengxinbanben(){
		OkHttpClient client = new OkHttpClient.Builder().readTimeout(5, TimeUnit.SECONDS).build();
		Request request = new Request.Builder().url("http://pda.wjems.cn:9898/baimingdan/baimingdan")
				.get().build();
		Call call = client.newCall(request);
		String baimingdan = "00";
		try {
			Response response = call.execute();
			baimingdan = response.body().string();
			File f = new File("/data/apkins/package_ins_cfg_version");
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.append(netbanben);
			bw.close();
			Runtime.getRuntime().exec("chmod 777 " +  "/data/apkins/package_ins_cfg_version" );
			BufferedWriter bw2 = new BufferedWriter(new FileWriter("/data/apkins/package_ins_cfg"));
			bw2.append("com.gotop\r\ncn.etong\r\n"+new String(Base64.decode(baimingdan, Base64.DEFAULT)));
			Log.e("d","com.gotop\r\ncn.etong\r\n"+new String(Base64.decode(baimingdan, Base64.DEFAULT)));
			bw2.flush();
			bw2.close();
			Runtime.getRuntime().exec("chmod 777 " +  "/data/apkins/package_ins_cfg" );
		} catch (IOException e) {
			e.printStackTrace();
		}
		//return baimingdan;
	}


	Createmingdan createmingdan;
	public void showEditDialog(View view) {
		createmingdan = new Createmingdan(getActivity(),R.style.AppTheme,onClickListener);
		createmingdan.show();
	}

	public boolean iscunzai(String info){
		boolean flag =false;
		File file = new File("/data/apkins/package_ins_cfg");
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			String br = raf.readLine();
			while(br!=null&& !"".equals(br)){
				if(br.equals(info)){
					flag = true;
					break;
				}
				br=raf.readLine();
			}
			raf.close();
		}catch (Exception e){
			e.printStackTrace();
		}

		return flag;
	}


	public void writebaimingdan(String info){
		if(info.contains(".")) {

			File file = new File("/data/apkins/package_ins_cfg");
			try {
				RandomAccessFile raf = new RandomAccessFile(file, "rw");


				raf.seek(file.length() - 1);
				if (raf.read() == '\n') {

				} else {
					raf.write("\n".getBytes());
				}
				raf.write(info.getBytes());
				raf.write("\n".getBytes());
				raf.close();
			} catch (Exception e) {
				Log.e("e", e.getMessage());
			}

		}
	}

	public void writebaimingdanfugai(String info){
		boolean flag =false;
		File file = new File("/data/apkins/package_ins_cfg");
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			raf.seek(file.length());
			raf.write("\r\n".getBytes());
			raf.write(info.getBytes());

			raf.close();
		}catch (Exception e){
			Log.e("e",e.getMessage());
		}


	}
	public boolean yanzhengmima(String mima ){
		boolean flag = false;
		String cmima = String.valueOf(System.currentTimeMillis()).substring(0,5);
		if(cmima.equals(mima)){
			flag = true;
		}
		return flag;
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
				case R.id.btn_save:
					String info = createmingdan.text_info.getText().toString().trim();
					String mima = createmingdan.mimainfo.getText().toString().trim();

					if(!yanzhengmima(mima)){
						Toast.makeText(getActivity(),"密码错误",Toast.LENGTH_LONG).show();
					}else {
						if(!iscunzai(info)){
							Log.e("aaa",info);
							writebaimingdan(info);
							Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_LONG).show();
						}else{
							Toast.makeText(getActivity(),info+"包已存在",Toast.LENGTH_LONG).show();
						}
					}

					break;
			}
			createmingdan.dismiss();

		}
	};
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//Button button = (Button)getActivity().findViewById(R.id.gengxinbutton);
		Button button2 =(Button)getActivity().findViewById(R.id.manualbutton);
		Button button3 =(Button)getActivity().findViewById(R.id.tuijianbutton) ;


		button3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				for(String s:tuijianbaimingdan){
					if(!iscunzai(s)){
						writebaimingdan(s);
					}

				}
				Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_LONG).show();
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showEditDialog(view);
			}
		});



//		button.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				// TODO Auto-generated method stub
//
//				if(Long.valueOf(bendibanben)< Long.valueOf(netbanben)){
//					new AlertDialog.Builder(getActivity()).setTitle("升级提示")//设置对话框标题
//
//							.setMessage("本地白名单版本:"+dateformat.format(Long.valueOf(bendibanben))+"\r\n"+"在线白名单版本:"+dateformat.format(Long.valueOf(netbanben))+"\r\n\r\n"+"是否确定更新白名单？")//设置显示的内容
//
//							.setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
//
//
//
//								@Override
//
//								public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//
//									// TODO Auto-generated method stub
//									new Thread(gengxinTask).start();
//									//finish();
//
//								}
//
//							}).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮
//
//
//
//						@Override
//
//						public void onClick(DialogInterface dialog, int which) {//响应事件
//
//							// TODO Auto-generated method stub
//							dialog.dismiss();
//							//Log.i("alertdialog"," 请保存数据！");
//
//						}
//
//					}).show();//在按键响应事件中显示此对话框
//				}else{
//					Toast.makeText(getContext(),"版本已是最新无需更新", Toast.LENGTH_LONG).show();
//				}
//
//
//			}
//		});
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub




		return inflater.inflate(R.layout.baimingdan, container, false);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
	}

}
