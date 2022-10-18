package com.example.myapplication_test;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Stack;

public class CalculatorActivity extends AppCompatActivity {

    private Button btn_0;
    private Button btn_1;
    private Button btn_sqrt;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private Button btn_8;
    private Button btn_9;
    private Button btn_point;
    private Button btn_plus;
    private Button btn_minus;
    private Button btn_multiply;
    private Button btn_divide;
    private Button btn_ce;
    private Button btn_clear;
    private Button btn_del;  // 清除一个数字
    private Button btn_equal;
    private TextView tv_num_calculate;//算数表达式
    private TextView tv_result;//计算结果
    private boolean is_new_calculate; //判断是否是新的计算开始
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        //控制button上图标大小
        btn_sqrt = findViewById(R.id.sqrt);
        Drawable drawable1 = getResources().getDrawable(R.drawable.kaifang);
        drawable1.setBounds(20, 20, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        btn_sqrt.setCompoundDrawables(drawable1, null, null, null);//只放左边
//        计算结果
        tv_result = findViewById(R.id.result_text);
        tv_num_calculate = findViewById(R.id.num_calculate);
//        每个按钮注册监听
        btn_0 = findViewById(R.id.zero);
        btn_1 = findViewById(R.id.one);
        btn_2 = findViewById(R.id.two);
        btn_3 = findViewById(R.id.three);
        btn_4 = findViewById(R.id.four);
        btn_5 = findViewById(R.id.five);
        btn_6 = findViewById(R.id.six);
        btn_7 = findViewById(R.id.seven);
        btn_8 = findViewById(R.id.eight);
        btn_9 = findViewById(R.id.nine);
        btn_point = findViewById(R.id.point);
        btn_plus = findViewById(R.id.plus);
        btn_minus = findViewById(R.id.minus);
        btn_multiply = findViewById(R.id.multiply);
        btn_divide = findViewById(R.id.divide);
        btn_del = findViewById(R.id.del);  // 倒数
        btn_equal = findViewById(R.id.equal);  // 等于
        btn_ce = findViewById(R.id.CE_btn);  // 回退
        btn_clear = findViewById(R.id.clear);  // 清除

        btn_0.setOnClickListener(ClickFn);
        btn_1.setOnClickListener(ClickFn);
        btn_2.setOnClickListener(ClickFn);
        btn_3.setOnClickListener(ClickFn);
        btn_4.setOnClickListener(ClickFn);
        btn_5.setOnClickListener(ClickFn);
        btn_6.setOnClickListener(ClickFn);
        btn_7.setOnClickListener(ClickFn);
        btn_8.setOnClickListener(ClickFn);
        btn_9.setOnClickListener(ClickFn);
        btn_point.setOnClickListener(ClickFn);
        btn_plus.setOnClickListener(ClickFn);
        btn_minus.setOnClickListener(ClickFn);
        btn_multiply.setOnClickListener(ClickFn);
        btn_divide.setOnClickListener(ClickFn);
        btn_sqrt.setOnClickListener(ClickFn);
        btn_del.setOnClickListener(ClickFn);
        btn_equal.setOnClickListener(ClickFn);
        btn_ce.setOnClickListener(ClickFn);
        btn_clear.setOnClickListener(ClickFn);

    }


    private View.OnClickListener ClickFn = new View.OnClickListener() {
        private static final String TAG = "CalculatorActivity";

        @Override
        public void onClick(View view) {
            String input_text = tv_num_calculate.getText().toString();
            if (is_new_calculate && view.getId()!=R.id.equal) {
                input_text = "";
                is_new_calculate = false;
                tv_result.setText("=0");
                tv_num_calculate.setText(input_text);
            }
              tv_result.setTextSize(30);
            switch (view.getId()) {
                case R.id.zero:
                case R.id.one:
                case R.id.two:
                case R.id.three:
                case R.id.four:
                case R.id.five:
                case R.id.six:
                case R.id.seven:
                case R.id.eight:
                case R.id.nine:
                    input_text = input_text + ((Button) view).getText().toString();
                    tv_num_calculate.setText(input_text);
                    break;
                case R.id.sqrt:
                    input_text = input_text + "√";
                    ;
                case R.id.point:
                case R.id.plus:
                case R.id.minus:
                case R.id.multiply:
                case R.id.divide:
//                   不允许连续输入多个运算符
                    if (input_text != null && !input_text.equals("")) {
//                        char c = input_text.charAt(input_text.length() - 1);
//                        if ((byte)c>41 && (byte)c<48 || c.equals("－")) {
                        //acsii的42-47为* + , - . /
                        String c = input_text.substring(input_text.length() - 1);
                        if (c.equals("＋") || c.equals("－") || c.equals("×") || c.equals("÷") || c.equals(".") || c.equals("√")) {
//                            判断如果两次按的都是运算符，取最后一个运算符
                            tv_num_calculate.setText(input_text.substring(0, input_text.length() - 1) + ((Button) view).getText());
                        } else {
                            //判断如果第两次按不是运算符，直接按字符串累加
                            tv_num_calculate.setText(input_text + ((Button) view).getText());
                        }
                    }
                    break;
                case R.id.del:
                    if (input_text != null && !input_text.equals("")) {
                        input_text = input_text.substring(0, input_text.length() - 1);
                        tv_num_calculate.setText(input_text);
                    }
                    break;
                case R.id.CE_btn:
                    //第一次ac清空calculate，第二次清空result
                    if(input_text.equals("")){
                        //calculate为空则清空计算结果
                        tv_result.setText("0");
                    }
                    input_text = "";
                    tv_num_calculate.setText(input_text);
                    break;
                case R.id.clear:
                    input_text = "";
                    tv_result.setText("0");
                    tv_num_calculate.setText(input_text);
                case R.id.equal:
//                   按 = 时进行,tv_num_calculate为结果值
                    tv_result.setTextSize(40);

                    if (input_text == null || input_text.equals("")) {
                        tv_result.setText("0");
                    } else {
                        tv_result.setText("=" + input_text);
                        // 每输入一次数字后就进行计算
                        tv_result.setText("=" + getResult(input_text));
                        is_new_calculate = true;
                    }

                    break;
                default:
                    break;
            }

            Log.d(TAG, "onClick: " + input_text);
        }

        private String getResult(String input_text) {

            if (input_text == null || input_text.equals("")) {
                return "";
            }
            // 如果不为空，先判断末尾是否是符号，如果是符号，就去掉
            String c = input_text.substring(input_text.length() - 1);
            if (c.equals("＋") || c.equals("－") || c.equals("×") || c.equals("÷") || c.equals(".") || c.equals("√")) {
                input_text = input_text.substring(0, input_text.length() - 1);
            }
            double return_num = 0;
            return_num = calculator(input_text);
            //    如果小数位为0输出整形int
            String return_string;
            if (return_num % 1 == 0) {
                return_string = String.valueOf((int) return_num);
            } else {
                return_string = String.valueOf(return_num);
            }
            return return_string;
        }

        //        运算，将数字类型改为double并在其中加入小数的运算
        private double calculator(String input_text) {
//            一、 把表达式分解成运算符和数字
            Stack<String> stkEles = new Stack<String>();
            LinkedList<String> tempBackcal = new LinkedList<String>();
            for (int i = 0; i < input_text.length(); i++) {
                // 1.遇到了数字
                if (Character.isDigit(input_text.charAt(i))) {
                    // 注意多位数的获取
                    int k = i + 1;
                    //小数点也加入数字中
                    for (; k < input_text.length() && (Character.isDigit(input_text.charAt(k)) || input_text.charAt(k) == '.'); k++) {

                    }
                    tempBackcal.add(input_text.substring(i, k));
                    i = k - 1;// 更新 i
                    continue;
                }
                // 2.遇到了乘除运算符
//                if (input_text.charAt(i) == '/' || input_text.charAt(i) == '*') {
                if (input_text.charAt(i) == '÷' || input_text.charAt(i) == '×') {

                    while (!stkEles.isEmpty() && (stkEles.lastElement().equals("÷") || stkEles.lastElement().equals("×"))) {
                        tempBackcal.add(stkEles.pop()); // 弹出优先级相同或以上的栈内运算符
                    }
                    stkEles.add(String.valueOf(input_text.charAt(i))); // 运算符入栈
                    continue;
                }
                // 3.遇到了加减运算符
                if (input_text.charAt(i) == '＋' || input_text.charAt(i) == '－') {
                    while (!stkEles.isEmpty() && !isNumeric(stkEles.lastElement())) {
                        tempBackcal.add(stkEles.pop()); // 弹出优先级相同或以上的栈内运算符
                    }
                    stkEles.add(String.valueOf(input_text.charAt(i))); // 运算符入栈
                    continue;
                }
            }
            // 4.最后弹出栈内所有元素到表达式
            while (stkEles.size() > 0) {
                tempBackcal.add(stkEles.pop());
            }
            // 二、 把分解的运算符和数字进行计算
            Stack<Double> calStk = new Stack<Double>();
            for (String c : tempBackcal) {
                // 1.数字，入栈
                if (isNumeric(c)) {
                    calStk.push(Double.valueOf(c)); // string to int
                    continue;
                }
                // 2.非数字，则为符号，出栈两个元素计算出结果然后再入栈该计算值
                else {
                    Double a = calStk.pop();
                    Double b = calStk.pop();
                    switch (c.toCharArray()[0]) {
                        // 注意减法和除法时，注意出栈的顺序与原表达式是相反的
                        case '＋':
                            calStk.push(b + a);
                            continue;
                        case '－':
                            calStk.push(b - a);
                            continue;
                        case '×':
                            calStk.push(b * a);
                            continue;
                        case '÷':
                            calStk.push(b / a);
                            continue;
                    }
                }
            }
            return calStk.pop();
        }

        private boolean isNumeric(String str) {
            for (int i = 0; i < str.length(); i++) {
                //是否为数字或者小数点
                if (!Character.isDigit(str.charAt(i)) && str.charAt(i) != '.') {
                    return false;
                }
            }
            return true;
        }

        ;
    };
}