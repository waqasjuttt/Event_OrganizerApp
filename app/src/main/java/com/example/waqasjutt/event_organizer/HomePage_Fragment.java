package com.example.waqasjutt.event_organizer;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomePage_Fragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    private TextView textView;
    private SliderLayout sliderLayout;
    private HashMap<String, Integer> HashMapForLocalRes;
    private View view;
    private static FragmentManager fragmentManager;

    public HomePage_Fragment() {
        // Required empty public constructor
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.home_page_fragment, container, false);
        fragmentManager = getActivity().getSupportFragmentManager();

        textView = (TextView) view.findViewById(R.id.tv_info);

        String best = getColoredSpanned("Best ", "#32cd32"),
                event = getColoredSpanned("EVENT", "#9acd32"),
                we = getColoredSpanned("We ", "#8b1c62"),
                are = getColoredSpanned("are ", "#292929"),
                For = getColoredSpanned("for ", "#ff69b4");
        textView.setText(Html.fromHtml(we + are + best + For + "your " + event));

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        textView.startAnimation(anim);

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

        getData();

        return view;
    }

    private void getData() {
        final String strID = SharedPrefManager.getInstance(getActivity()).getUserID();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Paths.URL_UPDATED_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("error") == "false") {
                                SharedPrefManager.getInstance(getActivity())
                                        .userLoginTest(
                                                obj.getString("id"),
                                                obj.getString("first_name"),
                                                obj.getString("last_name"),
                                                obj.getString("email"),
                                                obj.getString("mobile_number"),
                                                obj.getString("cnic"),
                                                obj.getString("date_of_birth"),
                                                obj.getString("geneder"),
                                                obj.getString("interests"),
                                                obj.getString("address"),
                                                obj.getString("telephone_number"),
                                                obj.getString("about"),
                                                obj.getString("domain")
                                        );

                                TastyToast.makeText(getActivity(), "Login Successful.",
                                        TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();

                            } else if (obj.getString("error") == "true") {
                                TastyToast.makeText(getActivity(),
                                        obj.getString("message"),
                                        Toast.LENGTH_SHORT,
                                        TastyToast.ERROR).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),
                                "Server is not responding..." + strID,
                                Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("id", strID);
                return param;
            }
        };
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
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