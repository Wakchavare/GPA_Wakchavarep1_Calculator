package com.example.gpa_wakchavarep1_calculator;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText[] courseGrades;
    TextView gpaDisplay;
    Button computeButton;
    boolean isFormCleared = false;
    boolean isGpaComputed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        courseGrades = new EditText[]{
                findViewById(R.id.editTextNumberDecimal9),
                findViewById(R.id.editTextNumberDecimal8),
                findViewById(R.id.editTextNumberDecimal7),
                findViewById(R.id.editTextNumberDecimal6),
                findViewById(R.id.editTextNumberDecimal)
        };

        gpaDisplay = findViewById(R.id.textView13);
        computeButton = findViewById(R.id.button);

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFormCleared)
                    clearForm(v);
                else
                    computeGPA();
            }
        });

        // Add TextWatcher to monitor changes in EditText fields
        for (EditText editText : courseGrades) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (isGpaComputed) {
                        clearGpaDisplay();
                        computeButton.setText("Compute GPA");
                        isFormCleared = false;
                        isGpaComputed = false;
                    }
                }
            });
        }
    }

    private void computeGPA() {
        double totalPoints = 0;

        for (EditText inputText : courseGrades) {
            String grade = inputText.getText().toString().trim();
            if (!grade.isEmpty()) {
                double courseGrade = Double.parseDouble(grade);
                if (courseGrade >= 0 && courseGrade <= 100) {
                    totalPoints += courseGrade;
                } else {
                    gpaDisplay.setText("Please fill in grades between 0 - 100.");
                    return;
                }
            } else {
                gpaDisplay.setText("Please fill in all the grades");
                return;
            }
        }

        double totalGPA = totalPoints / 5;
        gpaDisplay.setText(String.format("GPA: %.2f", totalGPA));

        if (totalGPA <= 60)
            gpaDisplay.setBackgroundColor(Color.RED);
        else if (totalGPA >= 61 && totalGPA <= 79)
            gpaDisplay.setBackgroundColor(Color.YELLOW);
        else
            gpaDisplay.setBackgroundColor(Color.GREEN);

        computeButton.setText("Clear Form");
        isFormCleared = true;
        isGpaComputed = true;
    }

    public void clearForm(View view) {
        for (EditText editText : courseGrades) {
            editText.setText("");
        }
        clearGpaDisplay();
        computeButton.setText("Compute GPA");
        isFormCleared = false;
        isGpaComputed = false;
    }

    private void clearGpaDisplay() {
        gpaDisplay.setText("");
        gpaDisplay.setBackgroundColor(Color.TRANSPARENT);
    }
}
