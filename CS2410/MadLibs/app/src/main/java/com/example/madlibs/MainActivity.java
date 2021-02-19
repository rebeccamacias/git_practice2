package com.example.madlibs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final LinearLayout layout = new LinearLayout(this);

        final AppCompatTextView openMessage = new AppCompatTextView(this);
        openMessage.setText("Welcome to the MadLibs app!");
        //name
        final AppCompatTextView nameMsg = new AppCompatTextView(this);
        nameMsg.setText("Enter a name");
        final AppCompatEditText editName = new AppCompatEditText(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        editName.setLayoutParams(params);

        //number
        final AppCompatTextView ageMsg = new AppCompatTextView(this);
        ageMsg.setText("Enter a number");
        final AppCompatEditText editAge = new AppCompatEditText(this);
        editAge.setLayoutParams(params);

        //place1
        final AppCompatTextView place1Msg = new AppCompatTextView(this);
        place1Msg.setText("Enter the name of a place");
        final AppCompatEditText editPlace1 = new AppCompatEditText(this);
        editPlace1.setLayoutParams(params);

        //monster
        final AppCompatTextView monsterMsg = new AppCompatTextView(this);
        monsterMsg.setText("Enter a monster");
        final AppCompatEditText editMonster = new AppCompatEditText(this);
        editMonster.setLayoutParams(params);

        //country
        final AppCompatTextView countryMsg = new AppCompatTextView(this);
        countryMsg.setText("Enter a country");
        final AppCompatEditText editCountry = new AppCompatEditText(this);
        editCountry.setLayoutParams(params);

        //color
        final AppCompatTextView colorMsg = new AppCompatTextView(this);
        colorMsg.setText("Enter a color");
        final AppCompatEditText editColor = new AppCompatEditText(this);
        editColor.setLayoutParams(params);

        //place2
        final AppCompatTextView place2Msg = new AppCompatTextView(this);
        place2Msg.setText("Enter another name of a place");
        final AppCompatEditText editPlace2 = new AppCompatEditText(this);
        editPlace2.setLayoutParams(params);

        //object
        final AppCompatTextView objectMsg = new AppCompatTextView(this);
        objectMsg.setText("Enter an object");
        final AppCompatEditText editObject = new AppCompatEditText(this);
        editObject.setLayoutParams(params);

        //phraseView
        final AppCompatTextView phraseView = new AppCompatTextView(MainActivity.this);

        //button
        final AppCompatButton storyButton = new AppCompatButton(this);
        storyButton.setText("Create your MadLibs story!");
        storyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                layout.removeView(openMessage);
                layout.removeView(nameMsg);
                layout.removeView(editName);
                layout.removeView(ageMsg);
                layout.removeView(ageMsg);
                layout.removeView(editAge);
                layout.removeView(place1Msg);
                layout.removeView(editPlace1);
                layout.removeView(monsterMsg);
                layout.removeView(editMonster);
                layout.removeView(countryMsg);
                layout.removeView(editCountry);
                layout.removeView(colorMsg);
                layout.removeView(editColor);
                layout.removeView(place2Msg);
                layout.removeView(editPlace2);
                layout.removeView(objectMsg);
                layout.removeView(editObject);
                layout.removeView(storyButton);
                String name = editName.getText().toString();
                String age = editAge.getText().toString();
                String place1 = editPlace1.getText().toString();
                String monster = editMonster.getText().toString();
                String country = editCountry.getText().toString();
                String color = editColor.getText().toString();
                String place2 = editPlace2.getText().toString();
                String object = editObject.getText().toString();
                String story = "When " + name + " was just " + age + " years old, they went to "
                        + place1 + ". At " + place1 + ", a " + monster + " appeared and made all the" +
                        " animals in " + country + " " + color + ". " + name + " knew that it would " +
                        "be problematic if the animals were to stay " + color + " for the rest of " +
                        "time, so " + name + " embarked on a journey to " + place2 + " to find out " +
                        "how to change the animals of " + country + " back to normal. While in " +
                        place2 + ", " + name + " found the " + object + ", which was said to grant " +
                        "whatever the holder's desire was, as long as it was well-intended." +
                        " Figuring that their desire was well-intended, " + name + " made their way" +
                        " back to " + country + " to turn the animals back to their normal colors." +
                        " In the process of wielding the " + object + " and wishing for the animals " +
                        "to revert back, " + name + " somehow managed to defeat the " + monster +
                        " as well, thanks to the " + object + ". The animals were reverted back to " +
                        "their original colors, and everyone lived happily ever after. The end.";
                phraseView.setText(story);
            }
        });

        layout.addView(openMessage);
        layout.addView(nameMsg);
        layout.addView(editName);
        layout.addView(ageMsg);
        layout.addView(editAge);
        layout.addView(place1Msg);
        layout.addView(editPlace1);
        layout.addView(monsterMsg);
        layout.addView(editMonster);
        layout.addView(countryMsg);
        layout.addView(editCountry);
        layout.addView(colorMsg);
        layout.addView(editColor);
        layout.addView(place2Msg);
        layout.addView(editPlace2);
        layout.addView(objectMsg);
        layout.addView(editObject);
        layout.addView(storyButton);
        layout.addView(phraseView);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
    }
}