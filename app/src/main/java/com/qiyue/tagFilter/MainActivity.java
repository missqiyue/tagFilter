package com.qiyue.tagFilter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

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
