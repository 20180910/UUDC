package com.sk.uudc.module.home.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.github.androidtools.SPUtils;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.home.network.response.CityIdObj;
import com.sk.uudc.module.home.network.response.CitySearchObj;
import com.sk.uudc.network.ApiRequest;
import com.sk.uudc.tools.MyLetterListView;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/28.
 */

public class CitySearchActivity extends BaseActivity {
    @BindView(R.id.lv_city_search)
    ListView lv_city_search;
    @BindView(R.id.mllv_city_search)
    MyLetterListView mllv_city_search;
    @BindView(R.id.tv_city_search)
    TextView tv_city_search;
    @BindView(R.id.et_city_search)
    EditText et_city_search;

    private TextView overlay;
    private BaseAdapter adapter;
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;// 存放存在的汉语拼音首字母
    private Handler handler;
    private SQLiteDatabase database;
    private ArrayList<CitySearchObj> mCityNames;
    CitySearchObj cityModel;
    ArrayList<CitySearchObj> names;
    String proName[];
    private OverlayThread overlayThread;
    private List<CitySearchObj> cityList;
    String city = "上海市";
    String city_id_xuanze="";

    @Override
    protected int getContentView() {
        setAppTitle("定位");
        return R.layout.act_city_search;
    }

    @Override
    protected void initView() {
        //  SPUtils.setPrefString(mContext, Config.city,city);
        String dingweicity = SPUtils.getPrefString(mContext, Config.dingweicity, city);
        String dingweiarea = SPUtils.getPrefString(mContext, Config.dingweiarea,"");
        tv_city_search.setText(dingweicity+dingweiarea);


        et_city_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                getAllCityAndCounty(editable.toString());
            }
        });

       /* DBManager dbManager = new DBManager(this);
        dbManager.openDateBase();
        dbManager.closeDatabase();
        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);*/



    }
    @Override
    protected void initData() {
        showProgress();
//        getAllCity();
        getAllCityAndCounty("");
    }


    private void getAllCityAndCounty(String search_term) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("search_term", search_term);
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAllCityAndCounty(map, new MyCallBack<List<CitySearchObj>>(mContext, pcfl, pl_load) {

            @Override
            public void onSuccess(List<CitySearchObj> list) {
                cityList = list;
                sortListByZiMu(cityList);
//                mCityNames = getCityNames();
//                database.close();
                mllv_city_search.setOnTouchingLetterChangedListener(new LetterListViewListener());
                alphaIndexer = new HashMap<String, Integer>();
                handler = new Handler();
                overlayThread = new OverlayThread();
                initOverlay();

                setAdapter(cityList);
                lv_city_search.setOnItemClickListener(new CityListOnItemClick());
            }
        });
    }

    private void getAllCity() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("rnd", getRnd());
        map.put("sign", GetSign.getSign(map));
        ApiRequest.getAllCity(map, new MyCallBack<List<CitySearchObj>>(mContext, pcfl, pl_load) {

            @Override
            public void onSuccess(List<CitySearchObj> list) {
                cityList = list;
                sortListByZiMu(cityList);
//                mCityNames = getCityNames();
//                database.close();
                mllv_city_search.setOnTouchingLetterChangedListener(new LetterListViewListener());
                alphaIndexer = new HashMap<String, Integer>();
                handler = new Handler();
                overlayThread = new OverlayThread();
                initOverlay();

                setAdapter(cityList);
                lv_city_search.setOnItemClickListener(new CityListOnItemClick());
            }
        });

    }

    private void sortListByZiMu(List<CitySearchObj> list) {
        //获取名字首字母-大写
        for (CitySearchObj contant : list) {
            String cahr = PinyinHelper.toHanyuPinyinStringArray(contant.getTitle().charAt(0))[0].substring(0, 1).toUpperCase();
            contant.setNameSort(cahr);
        }
        //根据首字母排序
        Collections.sort(list, new Comparator<CitySearchObj>() {
            @Override
            public int compare(CitySearchObj contant, CitySearchObj t1) {
                return contant.getNameSort().compareTo(t1.getNameSort());
            }
        });
    }

    /**
     * 从数据库获取城市数据
     *
     * @return
     */
    private ArrayList<CitySearchObj> getCityNames() {
        names = new ArrayList<CitySearchObj>();
        Cursor cursor = database.rawQuery("SELECT * FROM T_City ORDER BY NameSort", null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            CitySearchObj cityModel = new CitySearchObj();
            cityModel.setCityName(cursor.getString(cursor.getColumnIndex("CityName")));
            cityModel.setNameSort(cursor.getString(cursor.getColumnIndex("NameSort")));
            names.add(cityModel);
        }
        return names;
    }

    @OnClick(R.id.tv_city_search)
    public void onClick() {
        SPUtils.setPrefString(mContext, Config.city,SPUtils.getPrefString(mContext,Config.dingweicity,""));
        SPUtils.setPrefString(mContext, Config.area,SPUtils.getPrefString(mContext,Config.dingweiarea,""));
        SPUtils.setPrefInt(mContext, Config.city_level,Config.level4);
        city= SPUtils.getPrefString(mContext,Config.dingweicity,"");
        SPUtils.setPrefString(mContext, Config.city_id_xuanze, SPUtils.getPrefString(mContext,Config.city_id_dingwei,""));
        finish();
//      STActivity(CitySearchActivity.class);
        finish();
//        getCityId();

    }





    private void getCityId() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        String city=SPUtils.getPrefString(mContext,Config.city,"");
        String area="";
        if(SPUtils.getPrefInt(mContext,Config.city_level,0)==4){
            area=SPUtils.getPrefString(mContext,Config.area,"");
        }
        map.put("city", city);
        map.put("area", area);
        map.put("sign", GetSign.getSign(map));
        com.sk.uudc.module.home.network.ApiRequest.getCityId(map, new MyCallBack<CityIdObj>(mContext) {
            @Override
            public void onSuccess(CityIdObj obj) {
                city_id_xuanze = obj.getCity_id();
                SPUtils.setPrefString(mContext, Config.city_id_xuanze, city_id_xuanze);
                finish();
            }
        });
    }

    /**
     * 城市列表点击事件(返回城市名字)
     *
     * @author sy
     */
    class CityListOnItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
            cityModel = (CitySearchObj) lv_city_search.getAdapter().getItem(pos);
            city = cityModel.getTitle();
            //记录当前是选择的城市还是区县
            SPUtils.setPrefInt(mContext, Config.city_level, cityModel.getClass_layer());
            if(cityModel.getClass_layer()==Config.level3){//城市
                SPUtils.setPrefString(mContext, Config.city, cityModel.getTitle());
            }else{
                SPUtils.setPrefString(mContext, Config.area, cityModel.getTitle());
            }
//            getCityId();
            city_id_xuanze = cityModel.getId();
            SPUtils.setPrefString(mContext, Config.city_id_xuanze, city_id_xuanze);
            finish();
        }

    }


    /**
     * 为ListView设置适配器
     *
     * @param list
     */
    private void setAdapter(List<CitySearchObj> list) {
        if (list != null) {
            adapter = new ListAdapter(this, list);
            lv_city_search.setAdapter(adapter);
        }

    }


    /**
     * ListViewAdapter
     *
     * @author sy
     */
    private class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<CitySearchObj> list;

        public ListAdapter(Context context, List<CitySearchObj> list) {

            this.inflater = LayoutInflater.from(context);
            this.list = list;
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                // getAlpha(list.get(i));
                String currentStr = list.get(i).getNameSort();
                // 上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? list.get(i - 1).getNameSort() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = list.get(i).getNameSort();
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }

        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_city_search, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(list.get(position).getTitle());
            String currentStr = list.get(position).getNameSort();
            String previewStr = (position - 1) >= 0 ? list.get(position - 1).getNameSort() : " ";
            if (!previewStr.equals(currentStr)) {
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(currentStr);
            } else {
                holder.alpha.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView alpha;
            TextView name;
        }

    }

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private class OverlayThread implements Runnable {

        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    private class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                lv_city_search.setSelection(position);
                overlay.setText(sections[position]);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1500);
            }
        }

    }


    @Override
    protected void onViewClick(View v) {

    }
}
