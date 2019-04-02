package com.lanjing.qgcodedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView codeImageView;
    private Button showBtn;

    //创建两个Bitmap,一个放二维码，一个放logo
    Bitmap codeBmp, logoBmp;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showBtn.setOnClickListener(this);
        //给图片添加长按点击事件
        codeImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /*
                * 给button添加长按事件，触发时调用openCode()方法打开二维码,如果是文本则显示文本，是可访问地址，则会弹出提示问你是否继续访问
                * */
                openCode();
                return true;
            }
        });
    }

    //初始化控件
    private void initView() {
        codeImageView = (ImageView) findViewById(R.id.code);
        showBtn = (Button) findViewById(R.id.showBtn);
    }


    @Override
    public void onClick(View v) {

        /*
        * 按钮点击显示二维码
        * */
        //加一个测试地址，之后我们的二维码就是用这个地址来生成。扫描后直接进入百度,这个地址也可以用于动态，或者是你从服务器中得到的地址
        url = "https://www.baidu.com";
        //这里的logo是系统自带的，通过BitmapFactory加载进来
        logoBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.demo);
        //通过ZXing框架将地址和logo图片加到里面，生成二维码之后赋值给codeBmp;
        codeBmp = ZXingUtilsTest.createQRImage(this, url, logoBmp);
        //最后将得到的codeBmp设置为ImageView的ImageBitmap这样图片就会显示出来了
        codeImageView.setImageBitmap(codeBmp);
    }


    private void openCode() {//这个方法其实和我们之前的页面跳转比较像  intent携带信息访问权限，这时活动产生一个新的任务，然后开始执行这个任务
        Intent intent = new Intent(Intent.ACTION_VIEW,
                (Uri.parse(url))
        ).addCategory(Intent.CATEGORY_BROWSABLE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
