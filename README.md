# Android-Railway-Ticket-System
## 参考资料

> 教学视频：[Android学习](https://www.bilibili.com/video/BV1m34y1h7K3/?spm_id_from=333.788&vd_source=26358c0abc7c39ff8f3329b40c3818d5)
>
> 马云gitee仓库地址：https://gitee.com/harbin-university-grade-19/android-helloworld

---

> 本人仓库地址：https://github.com/zechaowei/Android-Railway-Ticket-System

## 项目介绍

完成Android的基础学习，并根据视频教学内容完善项目功能；



## 项目效果

### 软件启动效果

点击APP后，出现三秒该画面；跳转到登录页面

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E8%BD%AF%E4%BB%B6%E5%90%AF%E5%8A%A8%E7%94%BB%E9%9D%A2.png" style="zoom:25%;" />

### 登录页面

登陆页面的三个图片自行选择

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingRight="16dp"
    android:paddingLeft="16dp">

    <ImageView
        android:id="@+id/people"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:src="@drawable/icon_people"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="30dp"/>

    <!--设置用户栏-->
    <EditText
        android:id="@+id/username"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_below="@id/people"
        android:layout_marginTop="80dp"
        android:hint="用户名"
        android:textSize="20sp"
        android:textColor="#FFAD33"
        android:maxLines="1"
        android:drawableLeft="@drawable/user"/>
    <!--密码栏-->
    <EditText
        android:id="@+id/password"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:layout_below="@id/username"
        android:layout_marginTop="40dp"
        android:hint="密码"
        android:inputType="textPassword"
        android:textSize="20sp"
        android:textColor="#FFAD33"
        android:maxLines="1"
        android:drawableLeft="@drawable/password"/>


    <!--登录按钮-->
    <Button
        android:id="@+id/btnLogin"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/password"
        android:layout_marginTop="80dp"
        android:text="登录"
        android:background="@drawable/btn_press_blue"
        android:textColor="#FFFFFF"
        android:textSize="25sp"/>

    <!--自动登录-->
    <CheckBox
        android:id="@+id/autoLogin"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/btnLogin"
        android:layout_marginTop="30dp"
        android:text="自动登录"
        android:textSize="15sp" />

    <!--忘记密码-->
    <TextView
        android:id="@+id/tvForgetPassword"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="忘记密码?"
        android:textColor="#0000FF"
        android:textSize="15sp"
        android:layout_alignRight="@+id/btnLogin"
        android:layout_alignBaseline="@+id/autoLogin" />

</RelativeLayout>
```

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/202310161526706.png" alt="image-20231016152630652" style="zoom:33%;" /><img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/202310161529683.png" style="zoom:25%;" />



登录的用户名和密码已经“写死”，用户名：”`admin`“，密码：”`123456`“，输入后选择自动登录；

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E8%BE%93%E5%85%A5%E8%B4%A6%E5%8F%B7.png" style="zoom:25%;" />

点击登录后，跳转到主页面

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E4%B8%BB%E9%A1%B5%E9%9D%A2.png" style="zoom:25%;" />

### 登录页面介绍

首先进入是的`车票`、`订单`、`我的`三个选项页面

#### 车票功能

##### 选择车站

首先设定布局文件

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ticket.StationActivity">

    <ListView
        android:id="@+id/station_list"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:background="#E8E8E8"
        android:layout_weight="1"
        android:cacheColorHint="#00000000"
        android:scrollbars="none" />

    <com.example.railwayticketingsystem.ticket.LetterListView
        android:id="@+id/letterListView"
        android:layout_height="match_parent"
        android:layout_width="30dp">

    </com.example.railwayticketingsystem.ticket.LetterListView>

</LinearLayout>
```

接下来实现java部分的代码：

```java
public class StationActivity extends AppCompatActivity {
    private BaseAdapter adapter;
    private ListView mStationListView;
    private TextView overlay;
    private LetterListView letterListView;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections;
    private Handler handler;
    private OverlayThread overlayThread;
    private ArrayList<Station> stations;

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题行
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_station);
        mStationListView = findViewById(R.id.station_list);
        LetterListView letterListView = findViewById(R.id.letterListView);
        //当字母有变化了，监听一下
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());

        //车站的数据
        stations = StationUtils.getAllStations(this);
        
        
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();

        initOverlay();

      	//显示一堆车站
        adapter = new ListAdapter(this, stations);
        mStationListView.setAdapter(adapter);

        mStationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Station station = stations.get(position);
                String name = station.getStation_name();
                Toast.makeText(StationActivity.this, "name: "+name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("name",name);
                setResult(300,intent);
                finish();
            }
        });
    }

    //点击右侧的字母，自动动定位到左侧  字母开始的车站
    private class LetterListViewListener implements
            LetterListView.OnTouchingLetterChangedListener {

        //选中的字母
        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                mStationListView.setSelection(position);//定位字母对应的开始车站的位置
                overlay.setText(sections[position]);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                handler.postDelayed(overlayThread, 1500);
            }
        }
    }

    private class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private List<Station> list;

        public ListAdapter(Context context, List<Station> list) {

            this.inflater = LayoutInflater.from(context);
            this.list = list;
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = list.get(i).getSort_order();
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? list.get(i - 1)
                        .getSort_order() : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = list.get(i).getSort_order();
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
                convertView = inflater
                        .inflate(R.layout.station_list_item, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(list.get(position).getStation_name());
            String currentStr = list.get(position).getSort_order();
            String previewStr = (position - 1) >= 0 ? list.get(position - 1) .getSort_order() : " ";
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

    // 初始化汉语拼音首字母弹出提示框(TextView)
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.station_overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);

    }

    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = getIntent();
            intent.putExtra("name", "");
            setResult(200, intent);

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
```

实现了出发城市和到达城市之间的**动画播放**，并且点击可以选择城市。

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E9%80%89%E6%8B%A9%E5%9F%8E%E5%B8%82.png" style="zoom:25%;" />

支持最右边侧面根据字母大写选择符合条件的城市。

##### 选择日期

```java
private void selectTime() {
    String srtTvFromStartTime = tvFromStartTime.getText().toString();
    //2023/10/1
    srtTvFromStartTime = srtTvFromStartTime.split(" ")[0];
    String[] time = srtTvFromStartTime.split("/");


    //选择出发时间
    DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

            Date selectDate = new Date(year - 1900, month, dayOfMonth);
            String weekday = DateUtils.formatDateTime(getActivity(), selectDate.getTime(), DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY);
            Toast.makeText(getActivity(), "weekday:" + weekday, Toast.LENGTH_SHORT).show();
            Log.d("weekday", weekday);

            //选择日期后，调用此事件
            tvFromStartTime.setText("" + year + "/" + (month + 1) + "/" + dayOfMonth + " " + weekday);
        }
    }, Integer.parseInt(time[0]),
            Integer.parseInt(time[1]) - 1,//月份从0开始
            Integer.parseInt(time[2]));

    dialog.show();
}
```



<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E9%80%89%E6%8B%A9%E6%97%A5%E6%9C%9F.png" style="zoom:25%;" />

选择完以后，在出发日期一栏显示选择的结果，并且Toast提示选择的日期：

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E9%80%89%E6%8B%A9%E6%97%A5%E6%9C%9F%E5%B1%95%E7%A4%BA.png" style="zoom:25%;" />

##### 查询功能

点击查询，直接跳转到如下页面，其中的数据都是“写死”的，无法动态修改。`预定`按钮可以点击，点击后跳转到下一个页面

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E6%9F%A5%E8%AF%A21.png" style="zoom:25%;" />

点击`预定`按钮，跳转到如下页面：

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E6%9F%A5%E8%AF%A22.png" style="zoom:25%;" />

该页面内容依旧“写死”，并且可以选择添加到乘车人，可以添加成功：

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/image-20231001192837548.png" alt="image-20231001192837548" style="zoom: 50%;" />

选择需要添加到乘车人员，其中数据利用for循环随机生成的数据，暂时没有动态实现选择。点击`添加联系人`按钮即可跳转至上一个页面，并且可以显示选择的结果：

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E6%9F%A5%E8%AF%A24.png" style="zoom:25%;" />

点击提交，即可跳转到主页面。

⚠️：<font color = red>**这里并未实现订单主页面的功能，该项目中并未实现该页面，需要读者后续自己实现，不过视频中有讲解其UI设计。**</font>







##### 其他

最后一个图片只是因为自己选择的虚拟机比较大，UI设计根据视频操作来后，UI界面不美观，自行添加图片。

---





#### 订单功能

只是实现这个页面，并没有设计UI以及具体功能，视频中只是提了几句，需要读者自己实现；

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E8%AE%A2%E5%8D%95.png" style="zoom:25%;" />

这里自己也只是实现该页面，没有后续操作。



#### 我的功能

页面展示：

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E6%88%91%E7%9A%84.png" style="zoom:25%;" />

视频教学中提到了我的联系人设计，其中`我的账户`功能和`我的联系人`功能部分及其相似，视频中并没有过多的介绍，读者可以参考`我的联系人`部分实现`我的账户`页面的功能。

同时`我的密码`页面视频中也并未实现，也只是提及UI页面设计以及如何实现，需要读者自己实现

`退出登录`功能：实现了登录逻辑的基本需要，在视频前中期有详细讲过。



##### 我的联系人

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E6%88%91%E7%9A%84%E8%81%94%E7%B3%BB%E4%BA%BA.png" style="zoom:25%;" />

每个联系人点击进去后都可以修改，并且利用for循环随机写入20个人的数据。点击张三0，跳转到如下页面：

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/andorid-%E5%BC%A0%E4%B8%890.png" style="zoom:25%;" />

这里实现了左上角的返回按钮的点击，以及右上角的删除人员的部分功能实现，只是现实图标如何操作，并没有真正删除张三0；初次之外，可以修改`姓名`、`乘客类型`和`电话`三个选项。在讲解该功能结束后，提及到`我的密码`功能如何实现。

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E4%BF%AE%E6%94%B9%E5%A7%93%E5%90%8D.png" style="zoom:25%;" />

接下来修改乘客类型：

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E4%BF%AE%E6%94%B9%E4%B9%98%E5%AE%A2%E7%B1%BB%E5%9E%8B.png" style="zoom:25%;" />

教学视频中并未实现修改电话号码的功能，但是读者可以根据修改姓名功能实现该功能。

除此之外，点击保存按钮，并不会正真意义上的保存，只能点击。







## 动画的实现

```java
private void startTranslate() {
    int duration = 500;
    //启动一个动画
    TranslateAnimation animationLeft = new TranslateAnimation(0, 320, 0, 0);
    //动画的速度
    animationLeft.setInterpolator(new DecelerateInterpolator());
    //动画持续的时间
    animationLeft.setDuration(duration);
    //出发城市，向右移动
    tvStationFrom.startAnimation(animationLeft);

    //启动一个动画
    TranslateAnimation animationRight = new TranslateAnimation(0, -320, 0, 0);
    //动画的速度
    animationRight.setInterpolator(new DecelerateInterpolator());
    //动画持续的时间
    animationRight.setDuration(duration);

    animationRight.setAnimationListener(new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        //当动画执行完成，需要交换出发城市、到达城市
        @Override
        public void onAnimationEnd(Animation animation) {
            CharSequence temp = tvStationFrom.getText();
            tvStationFrom.setText(tvStationTo.getText());
            tvStationTo.setText(temp);
        }
    });

    //到达城市，向左移动
    tvStationTo.startAnimation(animationRight);
}
```

并在`onClick`函数中调用该方法：

```java
@Override
public void onClick(View view) {
    //交换站点
    if (view.getId() == R.id.ivWf) {
        startTranslate();		//调用动画函数
    } else if (view.getId() == R.id.tvFromStartTime) {
        selectTime();
    } else if (view.getId() == R.id.tvStationFrom || view.getId() == R.id.tvStationTo) {
        Intent intent = new Intent(getActivity(), StationActivity.class);
        getActivity().startActivityForResult(intent, view.getId());
    } else if (view.getId() == R.id.button1) {
        Intent intent = new Intent(getActivity(), TicketBuy2Activity.class);
        getActivity().startActivity(intent);

        //将查询的出发城市、到达城市记录到SQLite数据库
        logStation();
    }
}
```





## 二维码实现

> 参考文章：[Android利用zxing生成二维码](https://blog.csdn.net/weixin_43670802/article/details/102776704?ops_request_misc=&request_id=&biz_id=102&utm_term=Android%E5%88%A9%E7%94%A8zxing%E7%94%9F%E6%88%90%E4%BA%8C%E7%BB%B4%E7%A0%81&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-102776704.142^v96^control&spm=1018.2226.3001.4187)

使用ZXing创建二维码

- 导入依赖
- 布局文件中绘制ImageView
- 通过BitMap类型构造ImageView的内容





添加依赖：https://mvnrepository.com/artifact/com.google.zxing/core/3.5.0

```
// https://mvnrepository.com/artifact/com.google.zxing/core
implementation 'com.google.zxing:core:3.5.0'
```



OrderActivity代码

```java
public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ImageView qrCode = findViewById(R.id.qrCode);

        Bitmap bitmap = createQRCodeImage("请支付100元",280,280);
        qrCode.setImageBitmap(bitmap);  //将内存中的数据渲染到ImageView中
    }
    
    public Bitmap createQRCodeImage(String txt, int w, int h)
    {
        Bitmap bitmap = null;

        try
        {
            //判断URL合法性
            if (txt == null || "".equals(txt) || txt.length() < 1)
            {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(txt, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < h; y++)
            {
                for (int x = 0; x < w; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * w + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * w + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            //显示到我们的ImageView上面
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }
}
```

布局文件

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".OrderActivity"
    android:background="#FFFFFF">

    <TextView
        android:id="@+id/tv_pay"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="请支付车票价格"
        android:textSize="40sp"
        android:gravity="center"
        android:layout_centerHorizontal="true"/>


    <ImageView
        android:id="@+id/qrCodeBackground"
        android:layout_width="320dp"
        android:layout_height="320dp"
        android:background="@drawable/qrcode_background"
        android:layout_alignParentBottom="true"
        android:layout_marginBottom="80dp"
        android:gravity="center"
        android:layout_centerHorizontal="true" />

    <ImageView
        android:id="@+id/qrCode"
        android:layout_width="280dp"
        android:layout_height="280dp"
        android:layout_alignParentBottom="true"
        android:layout_marginBottom="100dp"
        android:gravity="center"
        android:layout_centerHorizontal="true" />


</RelativeLayout>
```

渲染文件 使支付背景为圆角蓝色正方形

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <solid android:color="@color/blue"/>
    <corners android:radius="20dp"/>
</shape>
```

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/202310161507975.png" alt="image-20231016150758937" style="zoom:33%;" />





## 查询历史功能

- [x] 功能是否完成

```java
private void queryStationHistory() {
    String fileName = "my_station";
    MyStationDataBaseOpenHelper helper = new MyStationDataBaseOpenHelper(getActivity(), fileName, null, currentVersion);
    SQLiteDatabase readDB = helper.getReadableDatabase();
    Cursor c = readDB.rawQuery("select id, station_from, station_to from station_log order by id desc", null);

    int row = 1;
    while (c.moveToNext()) {
        @SuppressLint("Range") String from = c.getString(c.getColumnIndex("station_from"));
        @SuppressLint("Range") String to = c.getString(c.getColumnIndex("station_to"));
        if (row == 1) {
            tvft1.setText(from + "---" + to);
        } else {
            tvft2.setText(from + "---" + to);
        }
        if (row >= 2){
            break;
        }
        row++;
    }
    c.close();
    readDB.close();
    helper.close();
}
```

注意：需要在全局调用onResume函数，否则点击查询的时候，历史查询不会及时更新（此部分需要了解android的生命周期）

```java
@Override
public void onResume() {
    super.onResume();
    Log.d("TicketFragment", "=======onResume=========");
    queryStationHistory();
}
```

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/202310161500867.png" style="zoom:25%;" />



当点击“天津”和“昂昂溪”后，点击查询后，退出后续界面，可以在查询历史页面看见之前点击的查询界面。























