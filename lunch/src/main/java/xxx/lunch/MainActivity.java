package xxx.lunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private List<Lunch> lunches = new ArrayList<Lunch>();
    private ListView listView;
    private LunchAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayAdapter = new LunchAdapter(this, lunches);

        lunches.add(new Lunch("正宗排骨"));
        lunches.add(new Lunch("湘園"));
        lunches.add(new Lunch("林媽雞排"));
        lunches.add(new Lunch("九色鹽酥雞"));
        lunches.add(new Lunch("愛買"));
        lunches.add(new Lunch("吳記水餃"));
        lunches.add(new Lunch("丹丹"));
        lunches.add(new Lunch("流氓炒飯"));
        lunches.add(new Lunch("米羅大飯店"));
        lunches.add(new Lunch("阿雪炒飯"));

        final EditText text = (EditText) findViewById(R.id.input);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView lunchView = (CheckedTextView) view;
                Lunch item = arrayAdapter.getItem(position);
                item.setChecked(lunchView.isChecked());
            }
        });
        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)
                    && (text.getText() != null && text.getText().length() != 0)) {

                    Toast toast = Toast.makeText(MainActivity.this, "Add " + text.getText(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    lunches.add(new Lunch(text.getText().toString()));
                    arrayAdapter.notifyDataSetChanged();
                    System.out.println(lunches);
                    text.getText().clear();
                }

                return false;
            }
        });
    }

    public void gotoTreasureChest(View view) {
        String[] lunches = arrayAdapter.getCheckedString();
        if (lunches.length == 0) {
            Toast toast = Toast.makeText(this, "請選擇一個以上", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Intent intent = Intents.lunches(getApplicationContext(), lunches);
            startActivity(intent);
        }
    }
}
