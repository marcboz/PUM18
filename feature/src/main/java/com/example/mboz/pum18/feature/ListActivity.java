package com.example.mboz.pum18.feature;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        if(mainView.getChildCount() == 0){
            for(int iterator = 0; iterator < data.length; iterator++){
                int previousId;

                if(iterator == 0){
                    previousId = mainView.getId();
                } else {
                    previousId = mainView.getChildAt(iterator - 1).getId();
                }
                mainView.addView(
                        createItemCard(
                                data[iterator].title,
                                data[iterator].description,
                                data[iterator].preview,
                                data[iterator].sourceUrl,
                                previousId,
                                data[iterator].voteCount,
                                data[iterator].buryCount
                        )
                );
            }
        }
    }

    protected RelativeLayout createVoteCount(int voteUp, int voteDown){
        // Setting up container
        RelativeLayout voteCount = new RelativeLayout(this);

        // Setting up vote counters
        TextView upVotes = new TextView(this);
        TextView downVotes = new TextView(this);

        RelativeLayout.LayoutParams upVoteLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 100);
        RelativeLayout.LayoutParams downVoteLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 100);
        upVoteLayout.setMargins(0,20,0,0);
        downVoteLayout.setMargins(0,80, 0,20);

        upVotes.setLayoutParams(upVoteLayout);
        downVotes.setLayoutParams(downVoteLayout);
        upVotes.setPadding(0,0,10,0);
        downVotes.setPadding(0,0,10,0);

        upVotes.setTextColor(0xbb00ff00);
        downVotes.setTextColor(0xbbff0000);
        upVotes.setTextSize(16);
        downVotes.setTextSize(16);
        upVotes.setTypeface(upVotes.getTypeface(),Typeface.BOLD);
        downVotes.setTypeface(downVotes.getTypeface(),Typeface.BOLD);

        upVotes.setText("+ " + voteUp);
        downVotes.setText("- " + voteDown);

        voteCount.addView(upVotes);
        voteCount.addView(downVotes);

        return voteCount;
    }

    protected CardView createItemCard(String title, String description, String preview, String url, int previousId, int upVotes, int downVotes){
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
        titleLayout.setMargins(200,20,200,8);
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
        descriptionLayout.setMargins(200,80,200,8);
        descriptionView.setLayoutParams(descriptionLayout);
        descriptionView.setTextColor(0x99FFFFFF);
        descriptionView.setTextSize(12);
        descriptionView.setText(description.replace("&quot;", "\""));
        descriptionView.setMaxLines(2);
        descriptionView.setEllipsize(TextUtils.TruncateAt.END);

        // Setting up vote count
        RelativeLayout voteCount = createVoteCount(upVotes, downVotes);
        RelativeLayout.LayoutParams voteCountLayout = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        voteCountLayout.setMargins(10,0,8,0);
        voteCountLayout.addRule(RelativeLayout.RIGHT_OF, descriptionView.getId());
        voteCount.setId(View.generateViewId());
        voteCount.setPadding(8,8,8,8);
        voteCount.setLayoutParams(voteCountLayout);
        voteCount.setGravity(Gravity.RIGHT);
        card.addView(voteCount);

        // Setting up card onClick action
        final Intent intent = new Intent(ListActivity.this, WebActivity.class);
        intent.putExtra("sourceUrl", url);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        return card;
    }
}
