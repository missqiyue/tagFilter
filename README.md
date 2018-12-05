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
## 效果


## 功能介绍

**1. 标签的单选/反选/多选** <br>
**2. 是否使用不限标签，选中不限，其他选项自动释放选中状态** <br>
**3. 支持多种传入数据** <br>
**4. 支持是否有文字标题** <br>
**5. 支持设置默认选中项** <br>
**6. 支持设置点击监听** <br>
### 属性
```
<declare-styleable name="TagFilterView">
        <attr name="verticalSpacing" format="dimension" />
        <attr name="horizontalSpacing" format="dimension" />
        <attr name="titleVerticalSpacing" format="dimension" />
        <attr name="isShowTitle" format="boolean" />
        <attr name="isMoreSelect" format="boolean" />
        <attr name="isBackSelect" format="boolean" />
        <attr name="isAdaptive" format="boolean" />
        <attr name="isShowUnlimited" format="boolean" />
        <attr name="isSelectUnlimited" format="boolean" />
        <attr name="unlimitedText" format="string" />
        <attr name="titleTextSize" format="integer" />
        <attr name="tagTextSize" format="integer" />
        <attr name="tagWidth" format="dimension" />
        <attr name="tagHeight" format="dimension" />
        <attr name="tagPaddingTop" format="dimension" />
        <attr name="tagPaddingLeft" format="dimension" />
        <attr name="tagPaddingRight" format="dimension" />
        <attr name="tagPaddingBottom" format="dimension" />
        <attr name="tagTitleText" format="string" />
        <attr name="titleTextColor" format="color" />
        <attr name="tagTextColor" format="color" />
        <attr name="tagBackground" format="reference" />
        <attr name="tagBackgroundSelect" format="reference" />
        <attr name="tagTextColorSelect" format="color" />
        <attr name="maxRowNum" format="integer" />
        <attr name="maxColumn" format="integer" />
    </declare-styleable>
```
## demo
```
public class MainActivity extends AppCompatActivity {
    TagFilterView tagFilterView1;
    TagFilterView tagFilterView2;
    TagFilterView tagFilterView3;
    TagFilterView tagFilterView4;
    TagFilterView tagFilterView5;
    TagFilterView tagFilterView6;
    TagFilterView tagFilterView7;
    TagFilterView tagFilterView8;
    TagFilterView tagFilterView9;
    TagFilterView tagFilterView10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagFilterView1 = (TagFilterView) findViewById(R.id.tagFilterView1);
        tagFilterView2 = (TagFilterView) findViewById(R.id.tagFilterView2);
        tagFilterView3 = (TagFilterView) findViewById(R.id.tagFilterView3);
        tagFilterView4 = (TagFilterView) findViewById(R.id.tagFilterView4);
        tagFilterView5 = (TagFilterView) findViewById(R.id.tagFilterView5);
        tagFilterView6 = (TagFilterView) findViewById(R.id.tagFilterView6);
        tagFilterView7 = (TagFilterView) findViewById(R.id.tagFilterView7);
        tagFilterView8 = (TagFilterView) findViewById(R.id.tagFilterView8);
        tagFilterView9 = (TagFilterView) findViewById(R.id.tagFilterView9);
        tagFilterView10 = (TagFilterView) findViewById(R.id.tagFilterView10);
        initData();
    }


    private void initData() {
        tagFilterView1.onCreateTag(mStingData);
        tagFilterView2.onCreateTag(mStingData);
        tagFilterView3.onCreateTag(mListString());
        tagFilterView4.onCreateTagByObject(mListT1());
        tagFilterView5.onCreateTag(mStingData);
        tagFilterView6.onCreateTag(mListString());
        tagFilterView7.onCreateTagByObject(mListT());
        tagFilterView8.onCreateTagByObject(mListT());
        tagFilterView9.onCreateTagByObject(mListT());
        tagFilterView10.onCreateTagByObject(mListT());

        tagFilterView1.setSingleSelectClickListener(new MyOnSingleSelectClickListener());
        tagFilterView2.setSingleSelectClickListener(new MyOnSingleSelectClickListener());
        tagFilterView3.setMoreSelectClickListener(new MyOnMoreSelectClickListener());
        tagFilterView4.setMoreSelectClickListener(new MyOnMoreSelectClickListener());
        tagFilterView5.setMoreSelectClickListener(new MyOnMoreSelectClickListener());
        tagFilterView6.setMoreSelectClickListener(new MyOnMoreSelectClickListener());
        tagFilterView7.setMoreSelectClickListener(new MyOnMoreSelectClickListener());
        tagFilterView8.setMoreSelectClickListener(new MyOnMoreSelectClickListener());
        tagFilterView9.setMoreSelectClickListener(new MyOnMoreSelectClickListener());
        tagFilterView10.setMoreSelectClickListener(new MyOnMoreSelectClickListener());

        /**根据下标设置选中项**/
        tagFilterView1.setSelectTagByIndex(3);

        /**根据名称设置选中项**/
        tagFilterView2.setSelectTagByName("李小璐");

        /**根据key设置选中项**/
        tagFilterView4.setSelectTagByKey("6");

        /**根据key设置选中项**/
        List<String> keyValues = new ArrayList<>();
        keyValues.add("3");
        keyValues.add("5");
        tagFilterView8.setSelectTagsByKey(keyValues);

        /**根据名称设置选中项**/
        List<String> nameValues = new ArrayList<>();
        nameValues.add("鲁班7号");
        nameValues.add("大小姐");
        tagFilterView9.setSelectTagsByName(nameValues);

        /**根据下标设置选中项**/
        List<Integer> indexValues = new ArrayList<>();
        indexValues.add(0);
        indexValues.add(4);
        tagFilterView10.setSelectTagsByIndex(indexValues);
    }

    /**
     * 单选选择回调
     */
    public class MyOnSingleSelectClickListener implements TagFilterView.OnSingleSelectClickListener {

        @Override
        public void onClicked(int position, String tag) {
            Toast.makeText(MainActivity.this, "position:" + position + " ---tag:" + tag, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 单选选择回调
     */
    public class MyOnMoreSelectClickListener implements TagFilterView.OnMoreSelectClickListener {

        @Override
        public void onClicked(int position, String tag, List<Integer> mSelectIndex, List<String> mSelectTag) {
            Toast.makeText(MainActivity.this, "position:" + position + " ---tag:" + tag + "--mSelectIndex:" + onListToString(mSelectIndex, ",") + "--mSelectTag:" + selectedTags(mSelectTag, ","), Toast.LENGTH_SHORT).show();
        }
    }

    public static String[] mStingData = new String[]{
            "苍老师",
            "结衣老师",
            "小泽",
            "川北",
            "李小璐",
            "马蓉",
    };

    //List<T>
    public static List<ComTagFilterData> mListT() {
        List<ComTagFilterData> list = new ArrayList<>();
        list.add(new ComTagFilterData("后羿", "1"));
        list.add(new ComTagFilterData("鲁班7号", "2"));
        list.add(new ComTagFilterData("伽罗", "3"));
        list.add(new ComTagFilterData("狄仁杰", "4"));
        list.add(new ComTagFilterData("李元芳", "5"));
        list.add(new ComTagFilterData("大小姐", "6"));
        return list;
    }

    public static List<ComTagFilterData> mListT1() {
        List<ComTagFilterData> list = new ArrayList<>();
        list.add(new ComTagFilterData("后羿", "1"));
        list.add(new ComTagFilterData("鲁班7号", "2"));
        list.add(new ComTagFilterData("伽罗", "3"));
        list.add(new ComTagFilterData("狄仁杰", "4"));
        list.add(new ComTagFilterData("李元芳", "5"));
        list.add(new ComTagFilterData("大小姐", "6"));
        list.add(new ComTagFilterData("不限", "7"));
        return list;
    }

    //List<String>
    public static List<String> mListString() {
        List<String> list = new ArrayList<>();
        list.add("RNG");
        list.add("IG");
        list.add("EDG");
        list.add("WE");
        list.add("SKT T1");
        list.add("LGD");
        list.add("TOP");
        list.add("RW");
        list.add("OMG");
        return list;
    }


    /**
     * List转String
     *
     * @param strings list集合
     * @param tag     切割符
     * @return
     */
    private String onListToString(List<Integer> strings, String tag) {
        if (strings == null || strings.size() == 0) {
            return "";
        }

        StringBuffer buffer = new StringBuffer();
        if (strings != null && strings.size() > 0) {
            for (Integer s : strings) {
                buffer.append(s).append(tag);
            }
        }
        String str = buffer.toString().substring(0, buffer.toString().length() - 1);
        return str;
    }

    /**
     * List转String
     *
     * @param strings list集合
     * @param tag     切割符
     * @return
     */
    private String selectedTags(List<String> strings, String tag) {
        if (strings == null || strings.size() == 0) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        if (strings != null && strings.size() > 0) {
            for (String s : strings) {
                buffer.append(s).append(tag);
            }
        }
        String str = buffer.toString().substring(0, buffer.toString().length() - 1);
        return str;
    }
}
```

