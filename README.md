# Android-Railway-Ticket-System
## 参考资料

教学视频：[Android学习](https://www.bilibili.com/video/BV1m34y1h7K3/?spm_id_from=333.788&vd_source=26358c0abc7c39ff8f3329b40c3818d5)

马云gitee仓库地址：https://gitee.com/harbin-university-grade-19/android-helloworld

---

本人仓库地址：https://github.com/zechaowei/Android-Railway-Ticket-System

## 项目介绍

完成Android的基础学习，并根据视频教学内容完善项目功能；



## 项目效果

### 软件启动效果

点击APP后，出现三秒该画面；跳转到登录页面

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E8%BD%AF%E4%BB%B6%E5%90%AF%E5%8A%A8%E7%94%BB%E9%9D%A2.png" style="zoom:25%;" />

### 登录页面

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E7%99%BB%E5%BD%95%E9%A1%B5%E9%9D%A2.png" style="zoom:25%;" />

登录的用户名和密码已经“写死”，用户名：”`admin`“，密码：”`123456`“，输入后选择自动登录；

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E8%BE%93%E5%85%A5%E8%B4%A6%E5%8F%B7.png" style="zoom:25%;" />

点击登录后，跳转到主页面

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E4%B8%BB%E9%A1%B5%E9%9D%A2.png" style="zoom:25%;" />

### 登录页面介绍

首先进入是的`车票`、`订单`、`我的`三个选项页面

#### 车票功能

##### 选择车站

实现了出发城市和到达城市之间的**动画播放**，并且点击可以选择城市。

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/android-%E9%80%89%E6%8B%A9%E5%9F%8E%E5%B8%82.png" style="zoom:25%;" />

支持最右边侧面根据字母大写选择符合条件的城市。

##### 选择日期

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

##### 历史查询功能

自己并未实现，但是视频教学在最后部分有讲解，需要的观看视频即可。

##### 其他

最后一个图片只是因为自己选择的虚拟机比较大，UI设计根据视频操作来后，UI界面不美观，自行添加图片。



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





## 二维码实现

> 参考文章：[Android利用zxing生成二维码](https://blog.csdn.net/weixin_43670802/article/details/102776704?ops_request_misc=&request_id=&biz_id=102&utm_term=Android%E5%88%A9%E7%94%A8zxing%E7%94%9F%E6%88%90%E4%BA%8C%E7%BB%B4%E7%A0%81&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-0-102776704.142^v96^control&spm=1018.2226.3001.4187)

使用ZXing创建二维码

- 导入依赖
- 布局文件中绘制ImageView
- 通过BitMap类型构造ImageView的内容





#### 依赖

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

<img src="https://raw.githubusercontent.com/Anson-zechaoWei/photos_blog/main/img/202310132101719.png" style="zoom: 25%;" />
