package mate.kiss.mixandfind;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.ActionMode;
import android.widget.TextView;

import java.util.function.Function;

public class ShakeDetector implements SensorEventListener {
    private long mLastShakeTime = System.currentTimeMillis();
    private Runnable mixColors;

    public ShakeDetector(Runnable mixColors) {
        this.mixColors = mixColors;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            if ((curTime - mLastShakeTime) > Constant.MIN_TIME_BETWEEN_SHAKES_MILLISECS) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                double acceleration = Math.sqrt(Math.pow(x, 2) +
                        Math.pow(y, 2) +
                        Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;

                Log.d(Constant.APP_NAME, "Acceleration is " + acceleration + "m/s^2");

                if (acceleration > Constant.SHAKE_THRESHOLD) {
                    mLastShakeTime = curTime;
                    mixColors.run();
                    Log.d(Constant.APP_NAME, "Shake, Rattle, and Roll");
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
