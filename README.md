# HeaderRecyclerView
自己实现一个可以添加头部尾部的RecyclerView<br>

>通过学习ListView的源码试着给RecyclerView添加头部和尾部，同样使用kotlin语言打码达到学习kotlin的目的,这里将学习一下设计模式中的代理模式。

### 目录
* 代理模式
* ListView源码浅析
    * addView()方法和setAdapter()方法
    * 代理模式在ListView中的应用
* kotlin构造器学习
    * 主构造函数
    * 次构造函数
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

/** @hide */
protected HeaderViewListAdapter wrapHeaderListAdapterInternal(
        ArrayList<ListView.FixedViewInfo> headerViewInfos,
        ArrayList<ListView.FixedViewInfo> footerViewInfos,
        ListAdapter adapter) {
    return new HeaderViewListAdapter(headerViewInfos, footerViewInfos, adapter);
}
```


通过上面的源码可以看出无论是setAdapter还是addHeaderView,addFooterView都会新建一个HeaderViewListAdapter并且将头尾部信息和adapter传如其中。<br>
这个HeaderViewListAdapter就是我们自己写的Adapter的代理。看HeaderViewListAdapter的源码
```java
public class HeaderViewListAdapter implements WrapperListAdapter, Filterable
```
类的声明继承了WrapperListAdapter（父类也是Adapter）主要看adapter中必须重写的几个重要方法
###### getCount()
```java
public int getCount() {
    if (mAdapter != null) {
        return getFootersCount() + getHeadersCount() + mAdapter.getCount();
    } else {
        return getFootersCount() + getHeadersCount();
    }
}
```
这里获取的count是头尾部View加上我们自定义adapter中的count。
###### getItem(int position)
```java
public Object getItem(int position) {
    // Header (negative positions will throw an IndexOutOfBoundsException)
    int numHeaders = getHeadersCount();
    if (position < numHeaders) {
        return mHeaderViewInfos.get(position).data;
    }

    // Adapter
    final int adjPosition = position - numHeaders;
    int adapterCount = 0;
    if (mAdapter != null) {
        adapterCount = mAdapter.getCount();
        if (adjPosition < adapterCount) {
            return mAdapter.getItem(adjPosition);
        }
    }

    // Footer (off-limits positions will throw an IndexOutOfBoundsException)
    return mFooterViewInfos.get(adjPosition - adapterCount).data;
}
```
getItem的时候判断position是否小于headerView的列表大小，小于的话return mHeaderViewInfos.get(position).data;
adjPosition < adapterCount 就return mAdapter.getItem(adjPosition);否则 return mFooterViewInfos.get(adjPosition - adapterCount).data;<br>
###### getView()
```java
public View getView(int position, View convertView, ViewGroup parent) {
    // Header (negative positions will throw an IndexOutOfBoundsException)
    int numHeaders = getHeadersCount();
    if (position < numHeaders) {
        return mHeaderViewInfos.get(position).view;
    }

    // Adapter
    final int adjPosition = position - numHeaders;
    int adapterCount = 0;
    if (mAdapter != null) {
        adapterCount = mAdapter.getCount();
        if (adjPosition < adapterCount) {
            return mAdapter.getView(adjPosition, convertView, parent);
        }
    }

    // Footer (off-limits positions will throw an IndexOutOfBoundsException)
    return mFooterViewInfos.get(adjPosition - adapterCount).view;
}
```
在getView()方法中根据position的位置返回头部的View，adapter中的view，尾部的view。<br>
###### 总结：
HeaderViewListAdapter实际就是一个代理在mAdapter对外提供功能时加以控制。

---
#### kotlin
>当Kotlin中的类需要构造函数时，可以有一个主构造函数和多个次构造函数，可以没有次构造函数。

###### 主构造函数
主构造函数在类名后。
```kotlin
class Person(name: String) {

}
```
当主构造函数有注解或者可见性修饰符，需加 constructor 关键字。
```kotlin
class Person public @Inject constructor(name: String){

}
```
主构造函数不能包含任何的代码。初始化的代码可以放到以 init 关键字作为前缀的初始化块中：
```kotlin
class Test(name: String){

    init{
        print(name)
        ...
    }
}
```
当在主函数中声明变量后，可以当做全局变量使用
```kotlin
class Test(val name: String){

    fun printName(){
        print(name)
        ...
    }
}
```
注：<br>
1、函数的声明可以是val也可以是var<br>
2、当不在主构造函数中声明又想当全局变量使用，可在类中声明，主函数中声明是简化了其写法。
```kotlin
class Test(val name: String){

    val name: String = name

    fun printName(){
        print(name)
        ...
    }
}
```
当不在主函数中声明时，只能在初始化块以及属性声明中使用
###### 次构造函数
```kotlin
class Person {

    constructor() {

    }

    constructor(name: String):this() {

    }

    constructor(name: String, age: Int) : this(name) {

    }
}

class Person(){

    constructor(name: String):this() {

    }

    constructor(name: String, age: Int) : this(name) {

    }

}
```
3、当没有主构造参数时，创建次构造函数
```kotlin
//正确使用：
class Customer{

    constructor(name: String) {

    }

    constructor(name: String, age: Int) : this(name) {

    }

}
//错误使用：
class Customer{
    //没有主构造函数，使用委托this()错误
    constructor(name: String) : this() {

    }

    constructor(name: String, age: Int) : this(name) {

    }

}

```






