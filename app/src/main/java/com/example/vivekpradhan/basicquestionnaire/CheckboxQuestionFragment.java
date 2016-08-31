package com.example.vivekpradhan.basicquestionnaire;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckboxQuestionFragment extends Fragment {
    String [] finalSelection;
    int baseIndex = 1729;
    public CheckboxQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        View w =  inflater.inflate(R.layout.fragment_checkbox_question, container, false);
        Button next = (Button) w.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call the navigate method from main activity
                Log.d("question",Arrays.toString(finalSelection));
                //Add to response JSON
                ((MainActivity) getActivity()).navigateQuestion();
            }
        });
        JSONObject ques = ((MainActivity) getActivity()).getCurrentQuestion();
        TextView questionText = (TextView) w.findViewById(R.id.questionText);
        LinearLayout parentLayout = (LinearLayout) w.findViewById(R.id.check_add_layout);
        try {
            questionText.setText(ques.getString("question"));
            JSONArray options = ques.getJSONArray("options");
            /** Initialize array and set defaults **/
            final String [] selectedOptions = new String[options.length()];
            Arrays.fill(selectedOptions, "none");
            for(int i=0;i< options.length();i++){
                JSONObject opt = options.getJSONObject(i);
                CheckBox checkBox = new CheckBox(getActivity());
                checkBox.setId(baseIndex + opt.getInt("opid"));
                checkBox.setText(opt.getString("opvalue"));
                checkParams.setMargins(10, 10, 10, 10);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                            selectedOptions[buttonView.getId() - (baseIndex + 1)] = buttonView.getText().toString();
                        else
                            selectedOptions[buttonView.getId() - (baseIndex + 1)] = "none";
                        finalSelection = selectedOptions;
                    }
                });

                //checkParams.gravity = Gravity.CENTER;
                parentLayout.addView(checkBox, checkParams);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return w;
    }


}
