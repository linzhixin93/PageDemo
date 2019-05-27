#### desc

app基本组件，包含以下功能， **文档不全，可以看demo..**

* 将业务从Activity何Fragment中剥离到Page类中
* 多类型RecyclerView布局
* 网络请求策略工具

#### 页面-ui组件

> 主要是封装了一个Page类，提供goole的lifeCyclerOwner，建议配合ViewModel食用

* page使用

  * 继承 Page类，在相应生命周期中处理业务

    > onAttach -> View依附到组件的过程，可在此方法中对View进行包装
    >
    > onCreate -> 创建
    >
    > onVisible -> 可见
    >
    > onInvisible -> 不可见
    >
    > onDestroy -> 销毁
    >
    > ...

  * Page中通过getComponent获取Android系统组件的资源

  * 通过PageManager打开一个open，或者得到该page依附的fragment

  * 通过PageStack获得栈中的Page ***Page之间有突破1M限制的数据通信时可通过此类实现***

* recycler使用

  >  此包是对RecyclerView的Adapter, Holder进行了封装，抽离出Item类，此类的目的是用来装数据，以及将数据加载到holder中
  * Item

    继承Item，在bindHolder中，可以通过Holder设置页面UI，配合Adapter和Holder使用

  * LoadMoreAdapter 

    对Adapter的包装，实现上拉加载更多

#### 网络-net

> 平常在请求网络的时候，遇到比较多这样的场景
>
> 1. 不同的场景需要不同的请求策略 : 先在缓存中找，没有的话再跑网络，跑完网络再更新缓存等等
> 2. 按钮点击多次，只响应最后一次请求，前面的销毁掉
> 3. 页面退出的时候，把此页面的所有请求销毁，避免内存泄漏
> 4. 每个请求根据项目，有一些统一的参数，或者需要进行统一的加解密
>
> 此组件并不是网络加载库，是对Retrofit和okhttp的包装，目的只是在一定程度上解决这些痛点

* 使用
  * 通过ApiRepo类获得Api实例
  * 通过Request类对请求进行包装，可指定远端仓库以及本地仓库，以及请求策略
  * 通过给ApiRepo注册适配器的方式，进行统一的加解密
  * 通过DisposeHelper对请求进行管理

#### Common组件

> 对EventBus封装了一下，也有常用的view，和工具，待续...