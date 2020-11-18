package doankimdinh.i.sqlitebasic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private DataUser dataUser;
    private Button btnAdd, btnRemove, btnCancel;
    private EditText txtName;
    private ListView listView;
    private ArrayList nameList;
    private ArrayAdapter arrayAdapter;
    private ArrayList idList;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataUser = new DataUser(this, "userdb.sqlite", null, 1);

        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);
        btnCancel = findViewById(R.id.btnCancel);

        txtName = findViewById(R.id.txtName);
        listView = findViewById(R.id.listView);

        nameList = new ArrayList();
        idList = new ArrayList();
        getNameList();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nameList);

        listView.setAdapter(arrayAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataUser.addUser(new User(txtName.getText().toString()));
                getNameList();
                arrayAdapter.notifyDataSetChanged();
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataUser.removeUser((int)idList.get(index));
                getNameList();
                arrayAdapter.notifyDataSetChanged();
//               Toast.makeText(MainActivity.this, idList.get(index)+"Xoa thanh cong"+index, Toast.LENGTH_SHORT).show();
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                txtName.setText("doan ki dinh"+idList.get(i));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = dataUser.getUserByID((int)idList.get(index));
                user.setName(txtName.getText().toString());
                dataUser.Update(user);
                getNameList();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
    private ArrayList getNameList(){
        nameList.clear();
        idList = new ArrayList();
        for (Iterator iterator = dataUser.getAll().iterator(); iterator.hasNext(); ) {
            User next =  (User) iterator.next();
            nameList.add(next.getName());
            idList.add(next.getId());
        }
        return nameList;
    }
}