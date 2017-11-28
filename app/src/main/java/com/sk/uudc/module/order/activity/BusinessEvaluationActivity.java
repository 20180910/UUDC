package com.sk.uudc.module.order.activity;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.rx.MySubscriber;
import com.github.customview.MyCheckBox;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.sk.uudc.GetSign;
import com.sk.uudc.R;
import com.sk.uudc.base.BaseActivity;
import com.sk.uudc.base.BaseObj;
import com.sk.uudc.base.MyCallBack;
import com.sk.uudc.module.my.network.request.UploadImgBody;
import com.sk.uudc.module.order.Constant;
import com.sk.uudc.module.order.adapter.AddImgAdapter;
import com.sk.uudc.module.order.event.AddImgEvent;
import com.sk.uudc.module.order.network.request.BusinessEvaluationBody;
import com.sk.uudc.network.ApiRequest;
import com.sk.uudc.tools.BitmapUtils;
import com.sk.uudc.tools.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import top.zibin.luban.Luban;

/**
 * Created by Administrator on 2017/11/14.
 */

public class BusinessEvaluationActivity extends BaseActivity {


    @BindView(R.id.iv_business_evaluation_icon)
    ImageView iv_business_evaluation_icon;
    @BindView(R.id.tv_business_evaluation_name)
    TextView tv_business_evaluation_name;
    @BindView(R.id.tv_business_evaluation_num)
    TextView tv_business_evaluation_num;
    @BindView(R.id.et_business_evaluation_content)
    MyEditText et_business_evaluation_content;
    @BindView(R.id.tv_business_evaluation_submit)
    MyTextView tv_business_evaluation_submit;
    @BindView(R.id.cb_business_evaluation_icon1)
    MyCheckBox cb_business_evaluation_icon1;
    @BindView(R.id.cb_business_evaluation_icon2)
    MyCheckBox cb_business_evaluation_icon2;
    @BindView(R.id.cb_business_evaluation_icon3)
    MyCheckBox cb_business_evaluation_icon3;
    @BindView(R.id.cb_business_evaluation_icon4)
    MyCheckBox cb_business_evaluation_icon4;
    @BindView(R.id.cb_business_evaluation_icon5)
    MyCheckBox cb_business_evaluation_icon5;
    @BindView(R.id.rv_business_evaluation_img)
    RecyclerView rv_business_evaluation_img;
    String order_id, avatar, name, scoring, content = "";

    AddImgAdapter addImgAdapter;
    private BottomSheetDialog selectPhotoDialog;
    private int selectImgIndex;

    @Override
    protected int getContentView() {
        setAppTitle("商家评价");
//        setAppTitleColor(R.color.black_33);
//        setTitleBackgroud(R.color.white);
        return R.layout.act_business_evaluation;
    }

    @Override
    protected void initView() {
        getValue();
//        cb_business_evaluation_icon1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//            }
//        });
        addImgAdapter=new AddImgAdapter(mContext,R.layout.item_tuikuan_addimg);

        addImgAdapter.setList(new ArrayList());
        rv_business_evaluation_img.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
//        rv_business_evaluation_img.addItemDecoration(getItemDivider());
        rv_business_evaluation_img.setAdapter(addImgAdapter);

    }

    private void getValue() {
        order_id = getIntent().getStringExtra(Constant.IParam.orderId);
        avatar = getIntent().getStringExtra(Constant.IParam.shangjiaImg);
        name = getIntent().getStringExtra(Constant.IParam.shangjiaName);
        Glide.with(mContext).load(avatar).error(R.color.c_press).into(iv_business_evaluation_icon);
        tv_business_evaluation_name.setText(name);
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(AddImgEvent.class, new MySubscriber<AddImgEvent>() {
            @Override
            public void onMyNext(AddImgEvent event) {
                selectImgIndex = event.selectImgIndex;
                showSelectPhotoDialog();
            }
        });
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.cb_business_evaluation_icon1,
            R.id.cb_business_evaluation_icon2,
            R.id.cb_business_evaluation_icon3,
            R.id.cb_business_evaluation_icon4,
            R.id.cb_business_evaluation_icon5,
            R.id.tv_business_evaluation_submit
    })
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.cb_business_evaluation_icon1:
                if (cb_business_evaluation_icon1.isChecked()) {
                    tv_business_evaluation_num.setText("1.0");
                    scoring = "1.0";


                } else {
                    cb_business_evaluation_icon2.setChecked(false);
                    cb_business_evaluation_icon3.setChecked(false);
                    cb_business_evaluation_icon4.setChecked(false);
                    cb_business_evaluation_icon5.setChecked(false);
                    tv_business_evaluation_num.setText("0.0");
                    scoring = "0.0";

                }

                break;
            case R.id.cb_business_evaluation_icon2:
                if (cb_business_evaluation_icon2.isChecked()) {
                    cb_business_evaluation_icon1.setChecked(true);
                    cb_business_evaluation_icon3.setChecked(false);
                    cb_business_evaluation_icon4.setChecked(false);
                    cb_business_evaluation_icon5.setChecked(false);
                    tv_business_evaluation_num.setText("2.0");
                    scoring = "2.0";

                } else {
                    tv_business_evaluation_num.setText("1.0");
                    cb_business_evaluation_icon3.setChecked(false);
                    cb_business_evaluation_icon4.setChecked(false);
                    cb_business_evaluation_icon5.setChecked(false);
                    scoring = "1.0";
                }
                break;
            case R.id.cb_business_evaluation_icon3:
                if (cb_business_evaluation_icon3.isChecked()) {
                    cb_business_evaluation_icon1.setChecked(true);
                    cb_business_evaluation_icon2.setChecked(true);
                    cb_business_evaluation_icon4.setChecked(false);
                    cb_business_evaluation_icon5.setChecked(false);
                    tv_business_evaluation_num.setText("3.0");
                    scoring = "3.0";
                } else {
                    cb_business_evaluation_icon4.setChecked(false);
                    cb_business_evaluation_icon5.setChecked(false);
                    tv_business_evaluation_num.setText("2.0");
                    scoring = "2.0";

                }
                break;
            case R.id.cb_business_evaluation_icon4:
                if (cb_business_evaluation_icon4.isChecked()) {
                    cb_business_evaluation_icon1.setChecked(true);
                    cb_business_evaluation_icon2.setChecked(true);
                    cb_business_evaluation_icon3.setChecked(true);
                    cb_business_evaluation_icon5.setChecked(false);
                    tv_business_evaluation_num.setText("4.0");
                    scoring = "4.0";
                } else {
                    cb_business_evaluation_icon5.setChecked(false);
                    tv_business_evaluation_num.setText("3.0");
                    scoring = "3.0";

                }
                break;
            case R.id.cb_business_evaluation_icon5:
                if (cb_business_evaluation_icon5.isChecked()) {
                    cb_business_evaluation_icon1.setChecked(true);
                    cb_business_evaluation_icon2.setChecked(true);
                    cb_business_evaluation_icon3.setChecked(true);
                    cb_business_evaluation_icon4.setChecked(true);
                    tv_business_evaluation_num.setText("5.0");
                    scoring = "5.0";
                } else {
                    tv_business_evaluation_num.setText("4.0");
                    scoring = "4.0";
                }
                break;
            case R.id.tv_business_evaluation_submit:
                content = getSStr(et_business_evaluation_content);
                if (TextUtils.isEmpty(scoring)||scoring.equals("0.0")) {
                    showMsg("你还没有给商家评分！");
                    return;
                }



                showProgress();
                postPublishComment();


                break;
        }
    }

    private void postPublishComment() {
        Map<String,String>map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("sign",GetSign.getSign(map));
        BusinessEvaluationBody body=new BusinessEvaluationBody();
        body.setContent(content);
        body.setOrder_id(order_id);
        body.setScoring(scoring);
        List< BusinessEvaluationBody.ImgBean> imgBean=new ArrayList<>();
        if(notEmpty(addImgAdapter.getList())){
            for (int i = 0; i < addImgAdapter.getList().size(); i++) {
                BusinessEvaluationBody.ImgBean bean=new BusinessEvaluationBody.ImgBean();
                bean.setImg((String)addImgAdapter.getList().get(i));
                imgBean.add(bean);
            }
        }
        body.setImg(imgBean);
        com.sk.uudc.module.order.network.ApiRequest.postPublishComment(map, body, new MyCallBack<BaseObj>(mContext) {
            @Override
            public void onSuccess(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();

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
                UploadImgBody item=new UploadImgBody();
                item.setFile(baseImg);
                String rnd = getRnd();
                Map<String,String> map=new HashMap<String,String>();
                map.put("rnd",rnd);
                map.put("sign", GetSign.getSign(map));
                ApiRequest.uploadImg(map,item, new MyCallBack<BaseObj>(mContext) {
                    @Override
                    public void onSuccess(BaseObj obj) {
                        if(selectImgIndex ==-1){
                            if(isEmpty(addImgAdapter.getList())){
                                List<String> list=new ArrayList<String>();
                                list.add(obj.getImg());
                                addImgAdapter.setList(list);
                            }else{
                                addImgAdapter.getList().add(obj.getImg());
                            }
                        }else{
                            addImgAdapter.getList().set(selectImgIndex,obj.getImg());
                        }
                        addImgAdapter.notifyDataSetChanged();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
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
        }
    }
}
