package xxx.lunch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Intents {
    public static Intent lunches(Context context, String[] lunches) {
        Intent intent = new Intent(context, TreasureChest.class);
        intent.putExtra("lunches", lunches);
        return intent;
    }

    public static String[] getLunches(Intent intent) {
        Bundle extras = intent.getExtras();
        String[] lunches = extras.getStringArray("lunches");
        return lunches;
    }
}
