package com.example.vivekpradhan.basicquestionnaire;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextBlankQuestionFragment extends Fragment {
    int NumBlanks = 0;
    int baseIndex = 1729;
    int questionId;
    public TextBlankQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View w =  inflater.inflate(R.layout.fragment_checkbox_question, container, false);
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
                String response = "";
                for(int i=0;i<NumBlanks;i++){
                    EditText blankHolder = (EditText) w.findViewById(baseIndex + i);
                    Log.d("question", blankHolder.getText().toString());
                    response = response.concat(blankHolder.getText().toString());
                    response = (i < NumBlanks-1) ? response.concat(",") : response; //Add comma
                }
                //Do validation of response here.
                //Add to response JSON
                ((MainActivity) getActivity()).updateResponse(questionId,response);
                ((MainActivity) getActivity()).navigateQuestion();
            }
        });
        JSONObject ques = ((MainActivity) getActivity()).getCurrentQuestion();
        TextView questionText = (TextView) w.findViewById(R.id.questionText);
        LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout parentLayout = (LinearLayout) w.findViewById(R.id.check_add_layout);
        try {
            questionText.setText(ques.getString("question"));
            NumBlanks = ques.getInt("blanks");
            for(int i = 0;i< ques.getInt("blanks");i++){
                EditText blankHolder = new EditText(getActivity());
                blankHolder.setId(baseIndex+i);
                blankHolder.setHint("Blank "+(i+1));
                parentLayout.addView(blankHolder, checkParams);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return w;
    }


}
