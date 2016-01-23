package xxx.lunch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

@SuppressWarnings("unchecked")
public class TreasureChest extends Activity {

    private TextView result;
    private ImageView box;
    private boolean boxOpen = false;
    private String[] lunches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treasure_chest);

        lunches = Intents.getLunches(getIntent());
        result = (TextView) findViewById(R.id.result_text);
        box = (ImageView) findViewById(R.id.box_image);
        View layout = findViewById(R.id.treasure_chest_layout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boxOpen = !boxOpen;
                if (boxOpen) {
                    box.setImageResource(R.drawable.box_open);
                } else {
                    box.setImageResource(R.drawable.box_close);
                }
                showText(boxOpen);
            }
        });

        box.setImageResource(R.drawable.box_close);
    }

    String randomLunch() {
        int rnd = new Random().nextInt(lunches.length);
        return lunches[rnd];
    }

    void showText(boolean show) {
        if (show) {
            result.setText(randomLunch());
        } else {
            result.setText("");
        }
    }
}
