package com.example.waqasjutt.event_organizer;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

public class HomePage_Fragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    private SliderLayout sliderLayout;
    private HashMap<String, Integer> HashMapForLocalRes;
    private View view;
    private static FragmentManager fragmentManager;

    public HomePage_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_page_fragment, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();

        sliderLayout = (SliderLayout) view.findViewById(R.id.slider);

        //Call this method to add images from local drawable folder .
        AddImageUrlFormLocalRes();

        for (String name : HashMapForLocalRes.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());

            if (name.toString().contains("-2")) {
                textSliderView
                        .description(name.toString().replace("-2", ""))
                        .image(HashMapForLocalRes.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                sliderLayout.addSlider(textSliderView);
            } else if (name.toString().contains("-3")) {
                textSliderView
                        .description(name.toString().replace("-3", ""))
                        .image(HashMapForLocalRes.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                sliderLayout.addSlider(textSliderView);
            } else if (name.toString().contains("-4")) {
                textSliderView
                        .description(name.toString().replace("-4", ""))
                        .image(HashMapForLocalRes.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                sliderLayout.addSlider(textSliderView);
            } else {
                textSliderView
                        .description(name)
                        .image(HashMapForLocalRes.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                sliderLayout.addSlider(textSliderView);
            }
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);

        //For go back to previous fragment
        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        //For Boom Menu Button
        ((MainActivity) getActivity()).boomMenuButton.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).mTitleTextView.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).initInfoBoom();
        ((MainActivity) getActivity()).mTitleTextView.setText("  Event Organizer");

        if (!SharedPrefManager.getInstance(getActivity()).isLoggedIn()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                new MainActivity().finishAffinity();
            } else {
                new MainActivity().finish();
            }
            fragmentManager.beginTransaction()
                    .replace(R.id.frameContainer,
                            new HomePage_Fragment(),
                            Utils.HomePage_Fragment)
                    .commit();
        }

        return view;
    }

    private void AddImageUrlFormLocalRes() {
        HashMapForLocalRes = new HashMap<>();

        HashMapForLocalRes.put("Wedding Stage", R.drawable.wedding);
        HashMapForLocalRes.put("Birth Day", R.drawable.birthday);
        HashMapForLocalRes.put("Event-2", R.drawable.event2);
        HashMapForLocalRes.put("DJ Event", R.drawable.dj_event);
        HashMapForLocalRes.put("Walima Stage", R.drawable.walima);
        HashMapForLocalRes.put("Event-3", R.drawable.event3);
        HashMapForLocalRes.put("Event", R.drawable.event);
        HashMapForLocalRes.put("Wedding Decoration", R.drawable.hall3);
        HashMapForLocalRes.put("Wedding Hall-3", R.drawable.hall4);
        HashMapForLocalRes.put("Wedding Hall-4", R.drawable.hall5);
        HashMapForLocalRes.put("Wedding Hall", R.drawable.hall);
        HashMapForLocalRes.put("Mehfil-e-Melaad Stage", R.drawable.mehfil);
        HashMapForLocalRes.put("Wedding Hall-2", R.drawable.hall2);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(final int state) {
    }
}