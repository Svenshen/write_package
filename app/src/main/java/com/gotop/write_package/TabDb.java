package com.gotop.write_package;

/**
 * Created by Administrator on 2017/11/22.
 */

public class TabDb {
    public static String[] getTabsTxt(){
        String[] tabs={"白名单  ","软件商城"};
        return tabs;
    }
    public static int[] getTabsImg(){
        int[] ids={R.drawable.foot_news_normal,R.drawable.foot_read_normal,R.drawable.foot_vdio_normal,R.drawable.foot_fond_normal,R.drawable.foot_out_normal};
        return ids;
    }
    public static int[] getTabsImgLight(){
        int[] ids={R.drawable.foot_news_light,R.drawable.foot_read_light,R.drawable.foot_vdio_light,R.drawable.foot_found_light,R.drawable.foot_out_light};
        return ids;
    }
    public static Class[] getFragments(){
        Class[] clz={NewsFragment.class,ReadFragment.class};
        return clz;
    }
}
