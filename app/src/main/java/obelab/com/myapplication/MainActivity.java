package obelab.com.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int BarValue = -10;
    private Handler mHandler = new Handler();
    private UpDownChart setLevel;

    Runnable testChart  = new Runnable() {
        @Override
        public void run() {
            if(BarValue  < 10){
                BarValue ++;
            }else{
                BarValue = -10;
            }
            setLevel.setLevel(BarValue);

            mHandler.postDelayed(testChart , 200);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLevel = (UpDownChart)findViewById(R.id.bar);
        mHandler.postDelayed(testChart , 200);
        setLevel.setLevel(-10);
    }
}
