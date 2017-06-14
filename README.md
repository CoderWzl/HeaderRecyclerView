# HeaderRecyclerView
自己实现一个可以添加头部尾部的RecyclerView<br>

>通过学习ListView的源码试着给RecyclerView添加头部和尾部，同样使用kotlin语言打码达到学习kotlin的目的,这里将学习一下设计模式中的代理模式。

### 目录
* 代理模式
* ListView源码浅析
    * addView()方法和setAdapter()方法
    * 代理模式在ListView中的应用
    * 利用代理模式实现RecyclerView添加头尾部
* kotlin
    * 构造器学习
    * init代码块
---
#### 代理模式
代理模式属于结构型模式（指通过代码结构实现解耦）设计模式大体分为以下三类<br>
* 构建型模式（单例模式，工厂模式...）
* 结构型模式（装饰者模式，Builder模式，代理模式...）
* 行为模式（责任链模式...）

[这里是一个设计模式的学习地址](http://www.runoob.com/design-pattern/design-pattern-tutorial.html)<br>
代理模式是用一个类代表另一个类的功能，代码中我们创建具有现有对象（被代理的对象）的对象（代理类），以便向外界提供功能接口。<br>
代理模式的主要目的是控制对一个对象的访问，以解决直接访问带来的问题。<br>
代码中代理类和被代理的类实现同一个接口，以达到重写相同方法的目的，代理类中拥有被代理类的实例，当想调用被代理类的某一方法时就可调用
代理类的相同方法，在代理类的该方法中通过被代理类的实例调用方法，当然在被代理类的实例调用方法之前可以做一些控制，这是代理模式的意义所在。

---
#### ListView源码浅析
1，addView()方法和setAdapter()方法
```java
//存储头部View
ArrayList<FixedViewInfo> mHeaderViewInfos = Lists.newArrayList();
//存储尾部View
ArrayList<FixedViewInfo> mFooterViewInfos = Lists.newArrayList();

//addHeaderView()
public void addHeaderView(View v, Object data, boolean isSelectable) {
    ...
    // Wrap the adapter if it wasn't already wrapped.
    if (mAdapter != null) {
        if (!(mAdapter instanceof HeaderViewListAdapter)) {
            wrapHeaderListAdapterInternal();
        }

        // In the case of re-adding a header view, or adding one later on,
        // we need to notify the observer.
        if (mDataSetObserver != null) {
            mDataSetObserver.onChanged();
        }
    }
}

//setAdapter
@Override
public void setAdapter(ListAdapter adapter) {
    ...
    if (mHeaderViewInfos.size() > 0|| mFooterViewInfos.size() > 0) {
        mAdapter = wrapHeaderListAdapterInternal(mHeaderViewInfos, mFooterViewInfos, adapter);
    } else {
        mAdapter = adapter;
    }
    ...
}

 /** @hide */
 protected void wrapHeaderListAdapterInternal() {
     mAdapter = wrapHeaderListAdapterInternal(mHeaderViewInfos, mFooterViewInfos, mAdapter);
 }
```










