package mate.kiss.mixandfind;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import static androidx.recyclerview.widget.RecyclerView.Adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::activityCallback);

    private Adapter<ColorListItemAdapter.ViewHolder> adapter;
    private List<ColorListItem> data;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences("application_data", MODE_PRIVATE);

        data = getSavedColorItems(savedInstanceState);
        data = getSavedColorItems();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new ColorListItemAdapter(data);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson serializer = new Gson();
        outState.putStringArrayList(
                Constant.ITEM_ID_SET_KEY,
                (ArrayList<String>) data.stream().map(d -> d.itemId).collect(Collectors.toList()));

        for (ColorListItem item : data) {
            outState.putString(item.itemId, serializer.toJson(item));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Gson serializer = new Gson();
        SharedPreferences.Editor editor = sp.edit();

        editor.putStringSet(
                Constant.ITEM_ID_SET_KEY,
                data.stream().map(d -> d.itemId).collect(Collectors.toSet()));
        for (ColorListItem item : data) {
            editor.putString(item.itemId, serializer.toJson(item));
        }

        editor.apply();
    }

    public void handleAddNewColorPushed(View view) {
        Intent openNewColorActivity = new Intent(this, NewColorActivity.class);
        activityResultLauncher.launch(openNewColorActivity);
    }

    private List<ColorListItem> getSavedColorItems(Bundle savedInstanceState) {
        List<ColorListItem> data = new ArrayList<>();

        if (savedInstanceState != null) {
            Gson serializer = new Gson();
            List<String> keys = savedInstanceState.getStringArrayList(Constant.ITEM_ID_SET_KEY);
            for (String key : keys) {
                data.add(serializer.fromJson(savedInstanceState.getString(key), ColorListItem.class));
            }
        }

        return data;
    }

    private List<ColorListItem> getSavedColorItems() {
        List<ColorListItem> data = new ArrayList<>();

        if (sp != null) {
            Gson serializer = new Gson();
            Set<String> keys = sp.getStringSet(Constant.ITEM_ID_SET_KEY, new HashSet<>());
            for (String key : keys) {
                data.add(serializer.fromJson(sp.getString(key, null), ColorListItem.class));
            }
        }

        return data;
    }

    private void activityCallback(ActivityResult result){
        Intent intent;
        if ((intent = result.getData()) != null && result.getResultCode() == RESULT_OK) {
            String itemName = intent.getStringExtra(Constant.COLOR_ITEM_NAME_KEY);
            String itemColorCode = intent.getStringExtra(Constant.COLOR_ITEM_COLOR_CODE_KEY);

            ColorListItem newItem = new ColorListItem(itemName, itemColorCode);
            data.add(newItem);
            adapter.notifyDataSetChanged();
        }
    }
}