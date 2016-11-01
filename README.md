# The Android Test Demos
记录一些 Android 的简单测试Demo 以及实现。

## SCREEN ANIM
desc: 界面之前切换动画的原理实现测试。
对应代码为：anim 包。
主要内容：去掉界面间的跳转动画，在第二个界面设置与第一个界面相同的元素。界面跳转之后，对第二个界面的元素做动画，给人以还是在同一个界面的错觉。

## JNI TEST
desc: 测试 JNI 调用的简单 Demo.
对应代码： jni 包。

## TOKEN TEST
desc: 实现 APP 请求的 token 的自动刷新
对应代码：token 包 , 根目录下的 server 文件夹
介绍文章: [Rxjava+Retrofit 实现全局过期 Token 自动刷新](http://alighters.com/blog/2016/05/02/rxjava-plus-retrofitshi-xian-wang-luo-dai-li/) [RxJava+Retrofit实现全局过期token自动刷新Demo篇](http://alighters.com/blog/2016/08/22/rxjava-plus-retrofitshi-xian-zhi-demo/)

启动： 需要在 server 文件夹下, 执行 `node refresh_token.js`, 用来启动本地服务。

## INTENTSERVICE TEsT
desc: 测试当使用 IntentService 启动一个耗时的任务，通过 Service 的 stop 方法，并不会将这个耗时的任务也终止掉。
对应代码： service.test 包下

## INSTANT RUN HOT TEST
desc: 当开启 Instant Run 时，对一个方法内部的实现做简单的修改，来查看类加载的情况。（主要为类中的 $change 字段）
代码：MainActivity 中的 testInstantRunHotMode 方法。

## REACT NATIVE TEST
desc: 将当前项目集成 React Native 内容，并使用 NativeModule 来测试对 native 代码的调用。
代码: reactnative 包




