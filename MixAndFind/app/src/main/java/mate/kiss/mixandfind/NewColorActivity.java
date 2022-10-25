package mate.kiss.mixandfind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class NewColorActivity extends AppCompatActivity {

    private TextView colorPreview;
    private EditText colorNameInput;
    private SensorManager mSensorMgr;
    private ShakeDetector shakeDetector;

    private int currentColor = Color.rgb(255,255,255);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_color);

        colorNameInput = findViewById(R.id.colorNameInput);
        colorPreview = findViewById(R.id.colorPreview);

        if (savedInstanceState != null) {
            colorPreview.setBackgroundColor(savedInstanceState.getInt(Constant.NEW_COLOR_PREVIEW_KEY));
        }

        mSensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(() -> mixColors());

        Sensor accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            mSensorMgr.registerListener(shakeDetector, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.NEW_COLOR_PREVIEW_KEY, ((ColorDrawable)colorPreview.getBackground()).getColor());
    }

    public void handleSaveButtonPushed(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constant.COLOR_ITEM_NAME_KEY, colorNameInput.getText().toString());
        resultIntent.putExtra(Constant.COLOR_ITEM_COLOR_CODE_KEY, currentColor);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private void mixColors() {
        SeekBar redBar = findViewById(R.id.redBar);
        SeekBar greenBar = findViewById(R.id.greenBar);
        SeekBar blueBar = findViewById(R.id.blueBar);

        currentColor = Color.rgb(
                redBar.getProgress(),
                greenBar.getProgress(),
                blueBar.getProgress());

        colorPreview.setBackgroundColor(currentColor);
    }
}