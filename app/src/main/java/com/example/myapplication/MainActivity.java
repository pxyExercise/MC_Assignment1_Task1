/**
 * @reference ---
 */
package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView process, result;
    MaterialButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnAC, btnC, btnEqual, btnDot;
    MaterialButton btnAdd, btnMinus, btnDivide, btnMulti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //this java file is related to the activity_main.xml

        process = findViewById(R.id.cal_process);
        result = findViewById(R.id.cal_result);

        findBtnId();

        //TODO: complete it
    }

    public void assignID(MaterialButton button, int id){
        button = findViewById(id);
        button.setOnClickListener(this);
    }

    public void findBtnId(){
        assignID(btn0, R.id.button_0);
        assignID(btn1, R.id.button_1);
        assignID(btn2, R.id.button_2);
        assignID(btn3, R.id.button_3);
        assignID(btn4, R.id.button_4);
        assignID(btn5, R.id.button_5);
        assignID(btn6, R.id.button_6);
        assignID(btn7, R.id.button_7);
        assignID(btn8, R.id.button_8);
        assignID(btn9, R.id.button_9);

        assignID(btnAC,R.id.button_ac);
        assignID(btnC,R.id.button_c);
        assignID(btnEqual,R.id.button_equals);
        assignID(btnDot,R.id.button_dot);

        assignID(btnAdd,R.id.button_add);
        assignID(btnMinus,R.id.button_minus);
        assignID(btnMulti,R.id.button_multiple);
        assignID(btnDivide,R.id.button_divide);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String btnText = button.getText().toString();
        String previous = process.getText().toString();

        if(btnText.equals("AC")){
            process.setText("");
            result.setText("0");
            return;
        }

        if(btnText.equals("=")){
            String textResult = getResult(previous);
            if(!textResult.equals("Err")){
                result.setText(textResult);
            }
            process.setText(result.getText());
            return;
        }

        if(btnText.equals("C")){
            if(!previous.equals("")){
                previous = previous.substring(0,previous.length()-1); //包含start但是不包含end
            }
            process.setText(previous);
            return;
        }

        previous = previous + btnText;
        process.setText(previous);
    }

    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            Double num = (Double)context.evaluateString(scriptable,data,"Javascript",1,null);
            String text = num.toString();
            if(text.endsWith(".0")){
                text = text.replace(".0","");
            }

            if(text.length()>8){
                Double result = Double.valueOf((double)Math.round(num.doubleValue()*10000000)/10000000);
                text = result.toString();
            }
            // System.out.println(context.evaluateString(scriptable,data,"Javascript",1,null).getClass());
            return text;
        }catch (Exception e) {
            return "Err";
        }
    }
}