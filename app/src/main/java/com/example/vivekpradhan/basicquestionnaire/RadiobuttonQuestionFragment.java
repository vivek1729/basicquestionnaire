package com.example.vivekpradhan.basicquestionnaire;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class RadiobuttonQuestionFragment extends Fragment {
    String finalSelection;
    int baseIndex = 1729;
    int questionId;
    public RadiobuttonQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View w =  inflater.inflate(R.layout.fragment_radiobutton_question, container, false);
        questionId = getArguments().getInt("questionId");
        Button next = (Button) w.findViewById(R.id.next);
        /**Update button text when end reached**/
        if(((MainActivity) getActivity()).endOfQuestions()){
            next.setText("Submit");
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call the navigate method from main activity
                Log.d("question", finalSelection);
                //Add to response JSON
                ((MainActivity) getActivity()).updateResponse(questionId,finalSelection);
                ((MainActivity) getActivity()).navigateQuestion();
            }
        });
        RadioGroup.LayoutParams checkParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        JSONObject ques = ((MainActivity) getActivity()).getCurrentQuestion();
        TextView questionText = (TextView) w.findViewById(R.id.questionText);
        RadioGroup parentLayout = (RadioGroup) w.findViewById(R.id.radioGroup);
        try {
            questionText.setText(ques.getString("question"));
            JSONArray options = ques.getJSONArray("options");
            for(int i=0;i< options.length();i++){
                JSONObject opt = options.getJSONObject(i);
                RadioButton radioButton = new RadioButton(getActivity());
                radioButton.setId(baseIndex + opt.getInt("opid"));
                radioButton.setText(opt.getString("opvalue"));
                parentLayout.addView(radioButton,checkParams);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        parentLayout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                if(checkedRadioButton.isChecked()){
                    finalSelection = checkedRadioButton.getText().toString();
                }
            }
        });
        return w;
    }


}
