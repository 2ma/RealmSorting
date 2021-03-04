package hu.am2.realmsorting;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    SomethingAdapter adapter;
    String start = "abophcdefghjkcdilZeRógüRöáaűUzúőfréäYÄÉaWŐÁxdfŰÓbwÜkdÖLÚ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SomethingAdapter();
        recyclerView.setAdapter(adapter);
        final List<Something> somethings = new ArrayList<>();
        for (int i = 0; i < start.length(); i++) {
            Something something = new Something();
            something.setUuid("id" + i);
            something.setSomeName(start.charAt(i) + "_name" + i);
            somethings.add(something);
        }


        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Realm realm = Realm.getDefaultInstance();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(Something.class);
                        realm.insertOrUpdate(somethings);
                    }
                });
                realm.close();
            }
        });
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Something> result = realm.where(Something.class).sort("someName", Sort.ASCENDING).findAllAsync();
        result.addChangeListener(new RealmChangeListener<RealmResults<Something>>() {
            @Override
            public void onChange(RealmResults<Something> somethings) {
                adapter.setSomethingList(somethings);
            }
        });
    }
}