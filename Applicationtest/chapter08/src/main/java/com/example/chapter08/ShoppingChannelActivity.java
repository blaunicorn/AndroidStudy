package com.example.chapter08;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter08.adapter.GoodsAdapter;
import com.example.chapter08.database.ShoppingDBHelper;
import com.example.chapter08.entity.GoodsInfo;
import com.example.chapter08.utils.Utils;

import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements GoodsAdapter.AddCartListener {
    // 声明一个商品数据库的帮助器对象
    private ShoppingDBHelper mShoppingDBHelper;
    private GridView gv_channel;
    private TextView tv_count;
    /**
     * 用于请求值使用
     */
    private final int PERMISSION_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);

//         权限判断
//        if (Build.VERSION.SDK_INT >= 23) {
//            //申请的权限数组
//            String[] mPermissionList = new String[]{
//                    //SD卡写入权限
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    //SD卡读取权限
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    //访问电话状态
//                    Manifest.permission.READ_PHONE_STATE,
//                    //访问摄像机权限
//                    Manifest.permission.CAMERA
//            };
//            //以下int数值都是申请权限后的返回值，0表示同意（PackageManager.PERMISSION_GRANTED），-1表示拒绝（PERMISSION_DENIED）
//            //而我们为了读取到SD卡中的数据，需要点同意
//            int checkSPermission = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            int checkSPermission2 = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE);
//            int checkSPermission3 = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.READ_PHONE_STATE);
//            int checkSPermissionCAMERA = ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.CAMERA);
//
//            //判断用户是否允许了我们所需权限
//            if ((checkSPermission != PackageManager.PERMISSION_GRANTED)
//                    || (checkSPermission2 != PackageManager.PERMISSION_GRANTED)
//                    || (checkSPermission3 != PackageManager.PERMISSION_GRANTED)
//                    || (checkSPermissionCAMERA != PackageManager.PERMISSION_GRANTED)) {
//                ActivityCompat.requestPermissions(this, mPermissionList, PERMISSION_REQUEST);
//            }
//        }


        // 调用数据库帮助器
        mShoppingDBHelper = ShoppingDBHelper.getInstance(this);
        mShoppingDBHelper.openReadLink();
        mShoppingDBHelper.openWriteLink();
        mShoppingDBHelper.openWriteLink();

//        设置每个页面的标题
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("手机商城");

        tv_count = findViewById(R.id.tv_count);
        gv_channel = findViewById(R.id.gl_channel);

//        设置返回键和购物筐点击事件
        ImageView iv_back = findViewById(R.id.iv_back);
        ImageView iv_cart = findViewById(R.id.iv_cart);
        iv_back.setOnClickListener(this::clickFn);
        iv_cart.setOnClickListener(this::clickFn);

        // 从数据库查询商品信息列表，并展示
        showGoods();
        showCartInfoTotal();
    }

    private void clickFn(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
//                点击返回，关闭当前页面
                finish();
                break;
            case R.id.iv_cart:
                // 跳转到购物车页面
                Intent intent = new Intent(this, ShoppingCartActivity.class);
//                设置启动标志，避免多次返回同一个页面
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 查询购物车商品总数，并展示
        showCartInfoTotal();
    }

    // 查询购物车商品总数，并展示
    private void showCartInfoTotal() {
        int count = mShoppingDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
    }

    private void showGoods() {

//        商品条目是一个线性布局，希望设置条目的宽度是屏幕的一般，就要获取到屏幕的宽度，然后复制给view视图
//        int screenWidth = getResources().getDisplayMetrics().widthPixels;
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2, ViewGroup.LayoutParams.WRAP_CONTENT);
//        查询商品数据库中的所有商品记录
//      List<GoodsInfo> list =   mShoppingDBHelper.queryAll();
//        Log.d("wcy", "showGoods: "+list.toString());
        // 移除下面的所有子视图
//        gv_channel.removeAllViews();;
//      for (GoodsInfo goodsInfo:list) {
////          Log.d("wcy", "picPath: "+goodsInfo.picPath);
////      获取布局文件item_goods.xml的跟视图，并导入变成一个java对象
//          View view = LayoutInflater.from(this).inflate(R.layout.item_goods, null);
////          给控件设置内容。 图片、名称、价格
//          ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
//          //  在资源中导入
////          iv_thumb.setImageResource(R.drawable.phone1);
////          转成bitmap引入方式
////          Bitmap bitmap = BitmapFactory.decodeFile(goodsInfo.picPath);
////          iv_thumb.setImageBitmap(bitmap);
////          路径方式
////         iv_thumb.setImageURI(Uri.fromFile(new File("/storage/emulated/0/Android/data/com.example.chapter06/files/Download/2131165353.png")));
////          直接引用绝对路径方式
//          iv_thumb.setImageURI(Uri.parse(goodsInfo.picPath));
//          TextView tv_name = view.findViewById(R.id.tv_name);
//          tv_name.setText(goodsInfo.name);
//          TextView tv_price = view.findViewById(R.id.tv_price);
//          tv_price.setText(String.valueOf((int) goodsInfo.price));
//          //增加按钮的点击事件
//          Button btn_add = view.findViewById(R.id.btn_add);
//          //点击时，将商品名称和id传入
//          btn_add.setOnClickListener(view1 -> {
//              addToCart(goodsInfo.id,goodsInfo.name);
//          });
//
////          点击商品图片，跳转到商品页面
//          iv_thumb.setOnClickListener(v-> {
//                Intent intent = new Intent(ShoppingChannelActivity.this,ShoppingDetailActivity.class);
//                intent.putExtra("goods_id",goodsInfo.id);
//                startActivity(intent);
//          });
//          // 把布局添加到主页面
//          gv_channel.addView(view,layoutParams);
//
//      }


        // 修改为适配器方式 生成页面
        //        查询商品数据库中的所有商品记录
        List<GoodsInfo> list = mShoppingDBHelper.queryAll();
        GoodsAdapter adapter = new GoodsAdapter(this,list,this );
        gv_channel.setAdapter(adapter);


    }

    // 将指定编号的商品加入到购物车
    // 方式二 的 适配器 新增接口 调用其他页面函数
    @Override
    public void addToCart(int goodsId, String goodsName) {
        Log.d("wcy", "addToCart: 1");
        mShoppingDBHelper.insertCartInfo(goodsId);
        Log.d("wcy", "addToCart: 2");
//        更新购物车中商品的数量
        showCartInfoTotal();
        Utils.show(this, "已添加一部" + goodsName + "到购物车");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mShoppingDBHelper.closeLink();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}