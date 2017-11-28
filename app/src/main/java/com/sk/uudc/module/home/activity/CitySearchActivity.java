package com.sk.uudc.module.home.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.db.DBManager;
import com.sk.uudc.module.home.network.response.CitySearchObj;
import com.sk.uudc.tools.MyLetterListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

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

    @Override
    protected int getContentView() {
        setAppTitle("定位");
        return R.layout.act_city_search;
    }

    @Override
    protected void initView() {
        DBManager dbManager = new DBManager(this);
        dbManager.openDateBase();
        dbManager.closeDatabase();
        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
        mCityNames = getCityNames();
        database.close();
        mllv_city_search.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        initOverlay();

        setAdapter(mCityNames);
        lv_city_search.setOnItemClickListener(new CityListOnItemClick());

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

    /**
     * 城市列表点击事件(返回城市名字)
     *
     * @author sy
     */
    class CityListOnItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
            cityModel = (CitySearchObj) lv_city_search.getAdapter().getItem(pos);
//            //判断回到那个页面
//            Intent intent;
//            if(yemian==0){
//                intent=new Intent(CityList.this,PersonActivity.class);
//            }else {
//                intent=new Intent(CityList.this,EnrollFragment.class);
//            }
//            intent.putExtra("cityName",cityModel.getCityName());
//            setResult(5, intent);
//            finish();

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
            return list.size();
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

            holder.name.setText(list.get(position).getCityName());
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
    protected void initData() {

    }


    @Override
    protected void onViewClick(View v) {

    }
}
