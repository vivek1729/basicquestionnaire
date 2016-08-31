package com.example.vivekpradhan.basicquestionnaire;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FragmentManager mFragmentManager;
    JSONArray MasterQuestionList;
    JSONArray ResponseList;
    JSONObject currentQuestion;
    int currentIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(this);*/
        MasterQuestionList = getFromJSON();//PopulateQuestionJSON from assets file
        ResponseList = MasterQuestionList;
        navigateQuestion();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**helper method that returns status of completition of questionnaire**/
    public boolean endOfQuestions(){
        if(currentIndex == MasterQuestionList.length() -1)
            return true;
        return false;
    }

    /***helper method to return Response object***/
    public JSONArray getResponses(){
        return ResponseList;
    }

    /***helper method to parse JSON from assets file**/
    public JSONArray getFromJSON(){
        JSONArray items = null;
        String json = null;
        InputStream is;
        try {
            is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        try {
            JSONObject obj = new JSONObject(json);
            items = obj.getJSONArray("questions");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }
    public void navigateQuestion(){
        currentIndex = currentIndex + 1;
        mFragmentManager = getSupportFragmentManager();
        if(currentIndex == MasterQuestionList.length()){
            //Last question has been completed
            FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.containerView,new QuesCompleteFragment()).commit();
        }
        try {
            currentQuestion = MasterQuestionList.getJSONObject(currentIndex); //Get question
            Log.d("question",currentQuestion.getString("question"));
            Bundle args = new Bundle();
            args.putInt("questionId", currentQuestion.getInt("id"));
            switch (currentQuestion.getString("type")){
                case "blank": {
                    TextBlankQuestionFragment blankQ = new TextBlankQuestionFragment();
                    blankQ.setArguments(args);
                    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView,blankQ).commit();
                    break;
                }

                case "checkbox": {
                    CheckboxQuestionFragment checkboxQ = new CheckboxQuestionFragment();
                    checkboxQ.setArguments(args);
                    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, checkboxQ).commit();
                    break;
                }
                case "radiobutton":{
                    RadiobuttonQuestionFragment radioQ = new RadiobuttonQuestionFragment();
                    radioQ.setArguments(args);
                    FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, radioQ).commit();
                    break;
                }
                default:
                    break;


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateResponse(int qId, String response){
        //Find question for that id and add response property
        for (int i = 0; i < ResponseList.length(); i++) {
            try {
                JSONObject jo_inside = ResponseList.getJSONObject(i);
                if(jo_inside.getInt("id") == qId)
                {
                    jo_inside.put("response",response); //Add response String to response key
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("question",ResponseList.toString());
    }
    public JSONObject getCurrentQuestion(){
        return currentQuestion;
    }
    /**Helper function to return question by id**/
    public JSONObject findQuestion(int qId){
        for (int i = 0; i < MasterQuestionList.length(); i++) {
            try {
                JSONObject jo_inside = MasterQuestionList.getJSONObject(i);
                if(jo_inside.getInt("id") == qId)
                    return jo_inside;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    // Implement the OnClickListener callback
    public void onClick(View v) {
        // do something when the button is clicked
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.next : {
                currentIndex = currentIndex + 1;
                Log.d("Question", "click called?");
                navigateQuestion();
                break;
            }
            default : break;
        }
    }
}
