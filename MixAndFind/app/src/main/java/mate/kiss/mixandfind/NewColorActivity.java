package mate.kiss.mixandfind;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewColorActivity extends AppCompatActivity {

    private EditText colorNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_color);

        colorNameInput = findViewById(R.id.colorNameInput);
    }

    public void handleSaveButtonPushed(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constant.COLOR_ITEM_NAME_KEY, colorNameInput.getText().toString());
        resultIntent.putExtra(Constant.COLOR_ITEM_COLOR_CODE_KEY, "#FF339964");
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}