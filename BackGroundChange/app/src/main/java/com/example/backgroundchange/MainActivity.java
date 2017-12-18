package com.example.backgroundchange;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private int Flag = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public  void ChangeBackground(View view)
    {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.Linear);
        if(Flag == 0)
        {
            Resources resources = getApplicationContext().getResources();
            Drawable drawable = resources.getDrawable(R.drawable.bg1);
            linearLayout.setBackgroundDrawable(drawable);
            Flag = 1;
        }
        else if(Flag == 1)
        {
            Resources resources = getApplicationContext().getResources();
            Drawable drawable = resources.getDrawable(R.drawable.bg2);
            linearLayout.setBackgroundDrawable(drawable);
            Flag = 2;
        }
        else
        {
            Resources resources = getApplicationContext().getResources();
            Drawable drawable = resources.getDrawable(R.drawable.bg0);
            linearLayout.setBackgroundDrawable(drawable);
            Flag = 0;
        }
    }
}
