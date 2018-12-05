# 说明
## 引用
```
allprojects {
    repositories {
        google()
        jcenter()
        maven { url "https://jitpack.io" }
    }
}

implementation 'com.github.missqiyue:tagFilter:v1.0'

<com.qiyue.tagfilter.TagFilterView
        android:id="@+id/tagFilterView1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:horizontalSpacing="10dp"
        app:isMoreSelect="false"
        app:tagHeight="30dp"
        app:tagTextSize="13"
        app:verticalSpacing="10dp" />

```
## 功能介绍

**1. 标签的单选/反选/多选** <br>
**2. 是否使用不限标签，选中不限，其他选项自动释放选中状态** <br>
**3. 支持多种传入数据** <br>
