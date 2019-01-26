package com.example.mboz.pum18.feature;

import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

public class ListActivity extends AppCompatActivity {

    ResponseItem[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle extras = getIntent().getExtras();
        String requestData=extras.getString("requestData");
        try {
            data = new ResponseArray(new JSONObject(requestData)).data;
        }catch (JSONException error){
            Log.e("response error", error.toString());
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        LinearLayout mainView = findViewById(R.id.list_content);
        for(int iterator = 0; iterator < data.length; iterator++){
            int previousId;

            if(iterator == 0){
                previousId = mainView.getId();
            } else {
                previousId = mainView.getChildAt(iterator - 1).getId();
            }
            mainView.addView(createItemCard(data[iterator].title,data[iterator].description,data[iterator].preview, data[iterator].sourceUrl, previousId));
        }
    }

    protected CardView createItemCard(String title, String description, String preview, String url, int previousId){
        ConstraintSet set = new ConstraintSet();
        CardView card = new CardView(this);
        CardView.LayoutParams layoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, 200);
        int id = View.generateViewId();

        // Setting up card containing item data
        card.setCardBackgroundColor(0x88000000);
        layoutParams.setMargins(0,4,0,4);
        card.setLayoutParams(layoutParams);
        card.setId(id);
        set.connect(id, ConstraintSet.TOP, previousId, ConstraintSet.BOTTOM, 8);

        // Setting up image inside card
        ImageView image = new ImageView(this);
        image.setId(View.generateViewId());
        card.addView(image);
        CardView.LayoutParams imageLayout = new CardView.LayoutParams(160, 160);
        imageLayout.setMargins(20,20,20,20);
        image.setLayoutParams(imageLayout);
        set.connect(image.getId(), ConstraintSet.LEFT, id, ConstraintSet.LEFT, 10);
        Picasso.get().load(preview).centerCrop().transform(new RoundedTransform(50,0)).fit().into(image);

        // Setting up card title
        TextView titleView = new TextView(this);
        titleView.setId(View.generateViewId());
        card.addView(titleView);
        CardView.LayoutParams titleLayout = new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);
        titleLayout.setMargins(200,20,20,8);
        titleView.setLayoutParams(titleLayout);
        titleView.setTextColor(0xFFFFFFFF);
        titleView.setTextSize(16);
        titleView.setText(title.replace("&quot;", "\""));
        titleView.setMaxLines(1);
        titleView.setEllipsize(TextUtils.TruncateAt.END);


        // Setting up card description
        TextView descriptionView = new TextView(this);
        descriptionView.setId(View.generateViewId());
        card.addView(descriptionView);
        CardView.LayoutParams descriptionLayout = new CardView.LayoutParams(CardView.LayoutParams.WRAP_CONTENT, CardView.LayoutParams.WRAP_CONTENT);
        descriptionLayout.setMargins(200,80,20,8);
        descriptionView.setLayoutParams(descriptionLayout);
        descriptionView.setTextColor(0x99FFFFFF);
        descriptionView.setTextSize(12);
        descriptionView.setText(description.replace("&quot;", "\""));
        descriptionView.setMaxLines(2);
        descriptionView.setEllipsize(TextUtils.TruncateAt.END);

        return card;
    }
}
