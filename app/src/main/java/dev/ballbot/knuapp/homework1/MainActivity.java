package dev.ballbot.knuapp.homework1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {
    private int currentIndex = 0;
    private int[] pocketMons = {
            R.drawable.pocket1,
            R.drawable.pocket2,
            R.drawable.pocket3,
            R.drawable.pocket4,
            R.drawable.pocket5,
            R.drawable.pocket6,
            R.drawable.pocket7,
            R.drawable.pocket8,
            R.drawable.pocket9,
            R.drawable.pocket10,
            R.drawable.pocket11,
            R.drawable.pocket12,
            R.drawable.pocket13,
            R.drawable.pocket14,
            R.drawable.pocket15,
            R.drawable.pocket16,
            R.drawable.pocket17,
            R.drawable.pocket18,
            R.drawable.pocket19,
            R.drawable.pocket20
    };

    private ImageView currentImageView = null;
    private ImageView nextImageView = null;
    private ImageView prevImageView = null;
    private Button nextButton = null;
    private Button prevButton = null;
    private EditText indexEditor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.currentImageView = findViewById(R.id.current_image_view);
        this.nextImageView = findViewById(R.id.next_image_view);
        this.prevImageView = findViewById(R.id.before_image_view);
        this.nextButton = findViewById(R.id.next_button);
        this.prevButton = findViewById(R.id.before_button);
        this.indexEditor = findViewById(R.id.index_input);

        this.nextButton.setOnClickListener(this);
        this.prevButton.setOnClickListener(this);
        this.indexEditor.setOnEditorActionListener(this);

        this.updateControls(0);
    }

    @Override
    public void onClick(View view) {
        this.updateControls(view.getId() == R.id.before_button ? -1 : 1);
    }

    private void updateControls(int adjust) {
        // Control buttons
        this.nextButton.setVisibility(View.VISIBLE);
        this.prevButton.setVisibility(View.VISIBLE);
        if (this.currentIndex + adjust == 0) {
            this.prevButton.setVisibility(View.INVISIBLE);
        }
        if (this.currentIndex + adjust == 19) {
            this.nextButton.setVisibility(View.INVISIBLE);
        }

        // Control images
        this.currentIndex += adjust;
        this.currentImageView.setImageResource(this.pocketMons[this.currentIndex]);

        this.prevImageView.setVisibility(View.INVISIBLE);
        this.nextImageView.setVisibility(View.INVISIBLE);
        if (this.currentIndex - 1 >= 0) {
            this.prevImageView.setImageResource(this.pocketMons[this.currentIndex - 1]);
            this.prevImageView.setVisibility(View.VISIBLE);
        }

        if (this.currentIndex + 1 <= 19) {
            this.nextImageView.setImageResource(this.pocketMons[this.currentIndex + 1]);
            this.nextImageView.setVisibility(View.VISIBLE);
        }

        this.indexEditor.setText(this.currentIndex + 1 + "");
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (
                actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_GO ||
                actionId == EditorInfo.IME_ACTION_SEND ||
                actionId == EditorInfo.IME_ACTION_NEXT
        ) {
            float row = Float.parseFloat(textView.getText().toString()) - 1;
            int newIndex = Math.round(row);
            if (newIndex < 0 || newIndex >= 20) {
                Toast.makeText(getApplicationContext(), "사진은 1~20까지 있습니다.", Toast.LENGTH_SHORT).show();
                newIndex = newIndex <= 0 ? 0 : 19;
            }

            this.updateControls(newIndex - this.currentIndex);
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentIndex", this.currentIndex);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int currentIndex = savedInstanceState.getInt("currentIndex");

        this.currentIndex = 0;
        this.updateControls(currentIndex);
    }
}