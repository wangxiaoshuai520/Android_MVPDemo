# Android_MVPDemo
参照http://www.jcodecraeer.com/a/anzhuokaifa/2017/1020/8625.html?1508484926,进行MVP项目实践
### 为什么用MVP架构
其实我们日常开发中的Activity，Fragment和XML界面就相当于是一个 MVC 的架构模式，Activity中不仅要处理各种 UI 操作还要请求数据以及解析。

这种开发方式的缺点就是业务量大的时候一个Activity 文件分分钟飙到上千行代码，想要改一处业务逻辑光是去找就要费半天劲，而且有点地方逻辑处理是一样的无奈是不同的 Activity 就没办法很好的写成通用方法。

### 那 MVP 为啥好用呢？

MVP 模式将Activity 中的业务逻辑全部分离出来，让Activity 只做 UI 逻辑的处理，所有跟Android API无关的业务逻辑由 Presenter 层来完成。

将业务处理分离出来后最明显的好处就是管理方便，但是缺点就是增加了代码量。

### MVP 理论知识
在MVP 架构中跟MVC类似的是同样也分为三层。

Activity 和Fragment 视为View层，负责处理 UI。

Presenter 为业务处理层，既能调用UI逻辑，又能请求数据，该层为纯Java类，不涉及任何Android API。

Model 层中包含着具体的数据请求，数据源。

三层之间调用顺序为view->presenter->model，为了调用安全着想不可反向调用！不可跨级调用！

那Model 层如何反馈给Presenter 层的呢？Presenter 又是如何操控View 层呢？看图！

### MVP架构调用关系

上图中说明了低层的不会直接给上一层做反馈，而是通过 View 、 Callback 为上级做出了反馈，这样就解决了请求数据与更新界面的异步操作。上图中 View 和 Callback 都是以接口的形式存在的，其中 View 是经典 MVP 架构中定义的，Callback 是我自己加的。

View 中定义了 Activity 的具体操作，主要是些将请求到的数据在界面中更新之类的。

Callback 中定义了请求数据时反馈的各种状态：成功、失败、异常等。

乞丐版MVP架构模式的代码实现
下面我们用 MVP 模式构造一个简易模拟请求网络的小程序。效果图如下：

成功加载到数据

加载数据失败

数据获取异常

因为是模拟网络数据请求，所以有三个请求数据的按钮分别对应成功、失败、异常三种不同的反馈状态。

下面是Demo中的Java文件目录：

Java文件目录

Callback接口
Callback 接口是Model层给Presenter层反馈请求信息的传递载体，所以需要在Callback中定义数据请求的各种反馈状态：
```
public interface MvpCallback {
   /**
     * 数据请求成功
     * @param data 请求到的数据
     */
    void onSuccess(String data);
    /**
     *  使用网络API接口请求方式时，虽然已经请求成功但是由
     *  于{@code msg}的原因无法正常返回数据。
     */
    void onFailure(String msg);
     /**
     * 请求数据失败，指在请求网络API接口请求方式时，出现无法联网、
     * 缺少权限，内存泄露等原因导致无法连接到请求数据源。
     */
    void onError();
    /**
     * 当请求数据结束时，无论请求结果是成功，失败或是抛出异常都会执行此方法给用户做处理，通常做网络
     * 请求时可以在此处隐藏“正在加载”的等待控件
     */
    void onComplete();
}
Model 类
Model 类中定了具体的网络请求操作。为模拟真实的网络请求，利用postDelayed方法模拟耗时操作，通过判断请求参数反馈不同的请求状态：

public class MvpModel {
    /**
     * 获取网络接口数据
     * @param param 请求参数
     * @param callback 数据回调接口
     */
    public static void getNetData(final String param, final MvpCallback callback){
        // 利用postDelayed方法模拟网络请求数据的耗时操作
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (param){
                    case "normal":
                        callback.onSuccess("根据参数"+param+"的请求网络数据成功");
                        break;
                    case "failure":
                        callback.onFailure("请求失败：参数有误");
                        break;
                    case "error":
                        callback.onError();
                        break;
                }
                callback.onComplete();
            }
        },2000);
    }
}
View 接口
View接口是Activity与Presenter层的中间层，它的作用是根据具体业务的需要，为Presenter提供调用Activity中具体UI逻辑操作的方法。

public interface MvpView {
    /**
     * 显示正在加载进度框
     */
    void showLoading();
    /**
     * 隐藏正在加载进度框
     */
    void hideLoading();
    /**
     * 当数据请求成功后，调用此接口显示数据
     * @param data 数据源
     */
    void showData(String data);
    /**
     * 当数据请求失败后，调用此接口提示
     * @param msg 失败原因
     */
    void showFailureMessage(String msg);
    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage();
}
Presenter类
Presenter类是具体的逻辑业务处理类，该类为纯Java类，不包含任何Android API，负责请求数据，并对数据请求的反馈进行处理。

Presenter类的构造方法中有一个View接口的参数，是为了能够通过View接口通知Activity进行更新界面等操作。

public class MvpPresenter {
    // View接口
    private MvpView mView;
    public MvpPresenter(MvpView view){
        this.mView = view;
    }
    /**
     * 获取网络数据
     * @param params 参数
     */
    public void getData(String params){
        //显示正在加载进度条
        mView.showLoading();
        // 调用Model请求数据
        MvpModel.getNetData(params, new MvpCallback() {
            @Override
            public void onSuccess(String data) {
                //调用view接口显示数据
                mView.showData(data);
            }
            @Override
            public void onFailure(String msg) {
                //调用view接口提示失败信息
                mView.showFailureMessage(msg);
            }
            @Override
            public void onError() {
                //调用view接口提示请求异常
                mView.showErrorMessage();
            }
            @Override
            public void onComplete() {
                // 隐藏正在加载进度条
                mView.hideLoading();
            }
        });
    }
}
```
xml布局文件
没什么好说的，直接上代码：
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp"
    android:orientation="vertical"
    tools:context="com.jessewu.mvpdemo.MainActivity">
    <TextView
        android:id="@+id/text"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:text="点击按钮获取网络数据"/>
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="获取数据【成功】"
        android:onClick="getData"
        />
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="获取数据【失败】"
        android:onClick="getDataForFailure"
        />
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="获取数据【异常】"
        android:onClick="getDataForError"
        />
</LinearLayout>
```
Activity
在Activity代码中需要强调的是如果想要调用Presenter就要先实现Presenter需要的对应的View接口。
```
public class MainActivity extends AppCompatActivity implements MvpView  {
    //进度条
    ProgressDialog progressDialog;
    TextView text;
    MvpPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.text);
        // 初始化进度条
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载数据");
        //初始化Presenter
        presenter = new MvpPresenter(this);
    }
    // button 点击事件调用方法
    public void getData(View view){
        presenter.getData("normal");
    }
    // button 点击事件调用方法
    public void getDataForFailure(View view){
        presenter.getData("failure");
    }
    // button 点击事件调用方法
    public void getDataForError(View view){
        presenter.getData("error");
    }
    @Override
    public void showLoading() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }
    @Override
    public void hideLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    @Override
    public void showData(String data) {
        text.setText(data);
    }
    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        text.setText(msg);
    }
    @Override
    public void showErrorMessage() {
        Toast.makeText(this, "网络请求数据出现异常", Toast.LENGTH_SHORT).show();
        text.setText("网络请求数据出现异常");
    }
}
```
至此，已经完整的实现了一个简易的MVP架构。
注意！以上代码中还存在很大的问题，不能用到实际开发中，下面会讨论目前存在的问题以及如何优化。
