package projectnine.cn.com.androidndkdemo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn, login;
    private EditText username, password, code;
    private int mCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = new Java2C(MainActivity.this).getFromC();
                Toast.makeText(MainActivity.this, result + "", Toast.LENGTH_LONG).show();

            }
        });
        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        code = (EditText) findViewById(R.id.identify);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(code.getText())) {
                    mCode = Integer.parseInt(code.getText().toString());
                }

                String result= new Java2C(MainActivity.this).login(username.getText().toString(), password.getText().toString(), mCode);
                Log.e("result",result);

                int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 4, 1};
                int[] newarray = new Java2C(MainActivity.this).modifyValue(array);
                for (int i : newarray) {
                    Log.e("ddd", i + "\n");
                }

            }
        });

        ThreadLocal<Integer> threadLocal=new ThreadLocal<>();


    }
}
