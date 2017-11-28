package com.sk.uudc.module.my.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.github.androidtools.DateUtils;
import com.github.androidtools.SPUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.customview.MyImageView;
import com.sk.uudc.Config;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.ApiRequest;
import com.sk.uudc.module.my.network.request.EditUserInfoBody;
import com.sk.uudc.module.my.network.request.UploadImgBody;
import com.sk.uudc.tools.BitmapUtils;
import com.sk.uudc.tools.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import top.zibin.luban.Luban;

/**
 * Created by Administrator on 2017/11/7.
 */

public class MyDataActivity extends BaseActivity {
    @BindView(R.id.im_my_data_icon)
    MyImageView im_my_data_icon;
    @BindView(R.id.ll_my_data_icon)
    LinearLayout ll_my_data_icon;
    @BindView(R.id.tv_my_data_name)
    TextView tv_my_data_name;
    @BindView(R.id.ll_my_data_name)
    LinearLayout ll_my_data_name;
    @BindView(R.id.tv_my_data_nickname)
    TextView tv_my_data_nickname;
    @BindView(R.id.ll_my_data_nickname)
    LinearLayout ll_my_data_nickname;
    @BindView(R.id.tv_my_data_sex)
    TextView tv_my_data_sex;
    @BindView(R.id.ll_my_data_sex)
    LinearLayout ll_my_data_sex;
    @BindView(R.id.tv_my_data_birthday)
    TextView tv_my_data_birthday;
    @BindView(R.id.ll_my_data_birthday)
    LinearLayout ll_my_data_birthday;
    @BindView(R.id.ll_my_data_xuigaimima)
    LinearLayout ll_my_data_xuigaimima;
    private BottomSheetDialog sexDialog,selectPhotoDialog;
    String name,nickname,sex,birthday,avatar;
    private String imgUrl="";

    @Override
    protected int getContentView() {
        setAppTitle("我的资料");
        setAppTitleColor(this.getResources().getColor(R.color.gray_33));
        setTitleBackgroud(R.color.white);
        return R.layout.act_my_data;
    }

    @Override
    protected void initView() {
        name= SPUtils.getPrefString(mContext, Config.name,"");
        nickname= SPUtils.getPrefString(mContext, Config.nick_name,"");
        sex= SPUtils.getPrefString(mContext, Config.sex,"");
        birthday= SPUtils.getPrefString(mContext, Config.birthday,"");
        avatar= SPUtils.getPrefString(mContext, Config.avatar,"");
        if (avatar != null) {
            Glide.with(mContext).
                    load(avatar).
                    error(R.drawable.people).
                    into(im_my_data_icon);
        }


        //TextUtils.isEmpty(phone)
        if (TextUtils.isEmpty(name)) {
            tv_my_data_name.setText("设置");
        }else {
            tv_my_data_name.setText(name);
        }
        if (TextUtils.isEmpty(nickname)) {
            tv_my_data_nickname.setText("设置");
        }else {
            tv_my_data_nickname.setText(nickname);
        }
        if (TextUtils.isEmpty(sex)) {
            tv_my_data_sex.setText("设置");
        }else {
            tv_my_data_sex.setText(sex);
        }
        if (TextUtils.isEmpty(birthday)) {
            tv_my_data_birthday.setText("设置");
        }else {
            tv_my_data_birthday.setText(birthday);
        }


        
        
        

    }

    @Override
    protected void initData() {

    }



    @OnClick({R.id.ll_my_data_icon, R.id.ll_my_data_name, R.id.ll_my_data_nickname, R.id.ll_my_data_sex, R.id.ll_my_data_birthday, R.id.ll_my_data_xuigaimima})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.ll_my_data_icon:
                showSelectPhotoDialog();
                break;
            case R.id.ll_my_data_name:
                Intent name=new Intent();
                name.putExtra("type","name");
                STActivityForResult(name,EditNameActivity.class,001);
                break;
            case R.id.ll_my_data_nickname:
                Intent nickname=new Intent();
                nickname.putExtra("type","nickname");
                STActivityForResult(nickname,EditNameActivity.class,002);
                break;
            case R.id.ll_my_data_sex:
                selectSex();
                break;
            case R.id.ll_my_data_birthday:
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        birthday= DateUtils.dateToString(date,"yyyy/MM/dd");
                        editUserInfo("birthday");



//                        tv_info_birthday.setText(birthday);
                    }
                }).setRange(1950, Calendar.getInstance().get(Calendar.YEAR)).setType(new boolean[]{true, true, true, false, false, false}).build();
                pvTime.show();
                break;
            case R.id.ll_my_data_xuigaimima:
                STActivity(UpdatePWDActivity.class);
                break;
        }
    }


    private void selectSex() {
        if (sexDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_sex,null);
            sexView.findViewById(R.id.tv_sex_nan).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    sex ="男";
                    editUserInfo("sex");
                }
            });
            sexView.findViewById(R.id.tv_sex_nv).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {

                    sex ="女";
                    editUserInfo("sex");

                }
            });
            sexView.findViewById(R.id.tv_sex_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    sexDialog.dismiss();
                }
            });
            sexDialog = new BottomSheetDialog(mContext);
            sexDialog.setCanceledOnTouchOutside(true);
            sexDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            sexDialog.setContentView(sexView);
        }
        sexDialog.show();
    }

    private void editUserInfo(String type) {
        Map<String,String>map=new HashMap<String,String>();
        map.put("rnd",getRnd());
        map.put("sign",GetSign.getSign(map));
        EditUserInfoBody body=new EditUserInfoBody();
        body.setUser_id(getUserId());


        if (type.equals("sex")) {
            body.setSex(sex);
        }else {
            body.setBirthday(birthday);

        }
        ApiRequest.editUserInfo(map, body, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                if (type.equals("sex")) {
                    sexDialog.dismiss();
                    tv_my_data_sex.setText(sex);
                }else {
                    tv_my_data_birthday.setText(birthday);




                }//  showMsg(obj.getMsg());
                showMsg(obj.getMsg());


            }
        });









    }

    private void showSelectPhotoDialog() {
        if (selectPhotoDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_select_photo,null);
            sexView.findViewById(R.id.tv_select_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    selectPhoto();
                }
            });
            sexView.findViewById(R.id.tv_take_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    takePhoto();
                }
            });
            sexView.findViewById(R.id.tv_photo_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                }
            });
            selectPhotoDialog = new BottomSheetDialog(mContext);
            selectPhotoDialog.setCanceledOnTouchOutside(true);
            selectPhotoDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            selectPhotoDialog.setContentView(sexView);
        }
        selectPhotoDialog.show();
    }
    //选择相册
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3000);
    }
    private String path = Environment.getExternalStorageDirectory() +
            File.separator + Environment.DIRECTORY_DCIM + File.separator;
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }
    Uri photoUri;
    private String imgSaveName="";
    //拍照
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                String fileName = getPhotoFileName() + ".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imgSaveName = path + fileName;
                photoUri = Uri.fromFile(new File(imgSaveName));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, 2000);
            }
        }
    }
    private void uploadImg() {
        showLoading();
        Log.i("========","========"+imgSaveName);
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String newPath= ImageUtils.filePath;
                ImageUtils.makeFolder(newPath);
                FileInputStream fis = null;
                try {
                    List<File> files = Luban.with(mContext).load(imgSaveName).get();
                    String imgStr = BitmapUtils.bitmapToString2(files.get(0));
                    subscriber.onNext(imgStr);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

            }
            @Override
            public void onMyNext(String baseImg) {
                UploadImgBody body=new UploadImgBody();
                body.setFile(baseImg);
                String rnd = getRnd();
                Map<String,String> map=new HashMap<String,String>();
                map.put("rnd",rnd);
                map.put("sign", GetSign.getSign(map));
                com.sk.uudc.network. ApiRequest.uploadImg(map,body, new MyCallBack<BaseObj>(mContext,true) {
                    @Override
                    public void onSuccess(BaseObj obj) {
                        imgUrl = obj.getImg();
                        Glide.with(mContext).load(imgSaveName).error(R.drawable.people).into(im_my_data_icon);
                        updateInfoForImg();
                    }
                });
            }
            @Override
            public void onMyError(Throwable e) {
                super.onMyError(e);
                dismissLoading();
                showToastS("图片处理失败");
            }
        });
    }
    private void updateInfoForImg() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("avatar",imgUrl);
        map.put("sign",GetSign.getSign(map));
        ApiRequest.updateUserImg(map, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                if(!TextUtils.isEmpty(imgUrl)){
                    SPUtils.setPrefString(mContext,Config.avatar,imgUrl);
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        Log.d("======",data.getStringExtra("name")+"zzzz");

        switch (requestCode){
            case 2000:
                uploadImg();
                break;
            case 3000:
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    imgSaveName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    uploadImg();
                }
                break;
            case 001:
                tv_my_data_name.setText(data.getStringExtra("name"));
                Log.d("======",data.getStringExtra("name"));
                break;
            case 002:
                tv_my_data_nickname.setText(data.getStringExtra("name"));
                break;
        }
    }
}
