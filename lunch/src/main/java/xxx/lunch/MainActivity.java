package xxx.lunch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private final List<Lunch> lunches = new ArrayList<Lunch>();
    private ListView listView;
    private LunchAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayAdapter = new LunchAdapter(this, lunches);

        loadLunches();

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
                    saveLunches();
                }

                return false;
            }
        });


        // init checked state
        for (int i = 0; i < lunches.size(); i++) {
            Lunch lunch = lunches.get(i);
            listView.setItemChecked(i, lunch.isChecked());
        }
    }

    @Override
    protected void onStop() {
        saveLunches();
        super.onStop();
    }

    void loadLunches() {
        try {
            FileInputStream in = openFileInput("lunches_v1.csv");
            InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
            List<Lunch> lunchesPersist = Csv.parse(reader, Lunch.class);
            lunches.addAll(lunchesPersist);
        } catch (Throwable e) {
            Log.e("lunch", "loadLunches", e);
        }
    }

    void saveLunches() {
        try {
            FileOutputStream out = openFileOutput("lunches_v1.csv", MODE_PRIVATE);
            Csv.writeTo(lunches, Lunch.class, out);
        } catch (FileNotFoundException ignored) {
        } catch (Throwable e) {
            Log.e("lunch", "saveLunches", e);
        }
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
