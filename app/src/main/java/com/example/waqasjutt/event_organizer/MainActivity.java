package com.example.waqasjutt.event_organizer;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements BoomMenuButton.AnimatorListener {

    public TextView mTitleTextView;
    public ActionBar mActionBar;
    private Context context;
    private ProgressBar animationListener;
    public BoomMenuButton boomMenuButton;
    private boolean isInit = false;
    private View view;
    private static FragmentManager fragmentManager;
    public SharedPreferences sharedPreferences;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //For go back to previous fragment
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        fragmentManager = getSupportFragmentManager();

        context = this;
        mActionBar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        view = mInflater.inflate(R.layout.custom_actionbar, null);
        mTitleTextView = (TextView) view.findViewById(R.id.title_text);
        mTitleTextView.setText("Event Organizer");
        mActionBar.setCustomView(view);
        mActionBar.setDisplayShowCustomEnabled(true);
        ((Toolbar) view.getParent()).setContentInsetsAbsolute(0, 0);
        boomMenuButton = (BoomMenuButton) view.findViewById(R.id.info);

        // If savedinstnacestate is null then replace login fragment
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new Login_Fragment(),
                            Utils.Login_Fragment).commit();
        }

        // On close icon click finish activity
        findViewById(R.id.close_activity).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        finish();
                    }
                });
    }

    @Override
    public void onBackPressed() {

        CustomDialog_Class customDialogClass = new CustomDialog_Class(this);

        // Find the tag of HomePage_fragment
        Fragment Test_Fragment = fragmentManager
                .findFragmentByTag(Utils.Test_Fragment);
        Fragment HomePage_Fragment = fragmentManager
                .findFragmentByTag(Utils.HomePage_Fragment);

        if (boomMenuButton.isClosed()) {
            boomMenuButton.dismiss();
        }

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            boomMenuButton.setVisibility(View.VISIBLE);
            mTitleTextView.setVisibility(View.VISIBLE);
        } else if (HomePage_Fragment != null) {
            customDialogClass.show();
        } else {
            customDialogClass.show();
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void initInfoBoom() {

        Drawable[] drawables = new Drawable[4];
        int[] drawablesResource = new int[]{
                R.drawable.profile_logo,
                R.drawable.contact_us,
                R.drawable.about_company,
                R.drawable.logout
        };

        for (int i = 0; i < 4; i++)
            drawables[i] = ContextCompat
                    .getDrawable(context,
                            drawablesResource[i]);

        int[][] colors = new int[4][3];

        //For Random Color
        for (int i = 0; i < 4; i++) {
            colors[i][1] = GetRandomColor();
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }

        //For Mine Color
//        colors[0][1] = ContextCompat.getColor(MainActivity.this, R.color.Boom_Menu1);
//        colors[0][0] = Util.getInstance().getPressedColor(colors[0][1]);
//
//        colors[1][1] = ContextCompat.getColor(MainActivity.this, R.color.Boom_Menu2);
//        colors[1][0] = Util.getInstance().getPressedColor(colors[0][1]);
//
//        colors[2][1] = ContextCompat.getColor(MainActivity.this, R.color.Boom_Menu3);
//        colors[2][0] = Util.getInstance().getPressedColor(colors[0][1]);

        String[] STRINGS = new String[]
                {
                        " Profile",
                        " Contact us",
                        " About",
                        " Logout"
                };

        final String[] strings = new String[STRINGS.length];

        for (int i = 0; i < STRINGS.length; i++)
            strings[i] = STRINGS[i];


        // Now with Builder, you can init BMB more convenient
        new BoomMenuButton.Builder()
                .subButtons(drawables, colors, strings)
                .button(ButtonType.HAM)
                .boom(BoomType.PARABOLA_2)
                .place(PlaceType.HAM_4_1)
                .subButtonsShadow(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2))
                .subButtonTextColor(ContextCompat.getColor(context, R.color.black))
                .onSubButtonClick(new BoomMenuButton.OnSubButtonClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        if (buttonIndex == 0) {
                            Toast.makeText(context, "You clicked on " + strings[buttonIndex], Toast.LENGTH_SHORT).show();
                            fragmentTransaction =
                                    fragmentManager
                                            .beginTransaction()
                                            .replace(R.id.frameContainer,
                                                    new Profile_Fragment());
                            fragmentTransaction
                                    .addToBackStack(null)
                                    .commit();
                        } else if (buttonIndex == 1) {
                            Toast.makeText(context, "You clicked on " + strings[buttonIndex], Toast.LENGTH_SHORT).show();
                        } else if(buttonIndex == 2){
                            Toast.makeText(context, "You clicked on " + strings[buttonIndex], Toast.LENGTH_SHORT).show();
                        }else if (buttonIndex == 3) {
                            Toast.makeText(context, "You clicked on " + strings[buttonIndex], Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(MainActivity.this).logout();
                            fragmentTransaction = getSupportFragmentManager()
                                    .beginTransaction();
                            fragmentTransaction
                                    .setCustomAnimations(R.anim.left_enter, R.anim.right_enter)
                                    .replace(R.id.frameContainer, new Login_Fragment())
                                    .commit();
                        }
                    }
                })
                .init(boomMenuButton);
    }

    private String[] Colors = {
            "#F44336",
            "#E91E63",
            "#9C27B0",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B",
            "#8a2be2",
            "#458b00",
            "#ff7f24",
            "#9932cc",
            "#ff1493",
            "#ff3030",
            "#ffd700",
            "#adff2f",
            "#ff69b4",
            "#f0e68c",
            "#7cfc00",
            "#f08080",
            "#ffa07a",
            "#20b2aa",
            "#32cd32",
            "#ff00ff",
            "#8b008b",
            "#cd2990",
            "#00fa9a",
            "#ff4500",
            "#ee0000",
            "#54ff9f"
    };

    public int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }

    @Override
    public void toShow() {
        animationListener.setProgress(0);
    }

    @Override
    public void showing(float fraction) {
        animationListener.setProgress((int) (fraction * 100));
    }

    @Override
    public void showed() {
        animationListener.setProgress(100);
    }

    @Override
    public void toHide() {
        animationListener.setProgress(100);
    }

    @Override
    public void hiding(float fraction) {
        animationListener.setProgress((int) ((1 - fraction) * 100));
    }

    @Override
    public void hided() {
        animationListener.setProgress(0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInit) {
            initInfoBoom();
        }
        isInit = true;
    }

    @Override
    public Dialog onCreateDialog(int id) {
        return super.onCreateDialog(id);
    }
}