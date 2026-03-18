package com.example.allergi.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.allergi.R;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageDialog extends DialogFragment {

    public interface OnButtonClick {
        void execute();
    }

    private String message;
    private String nameNegativeButton;
    private String namePositiveButton;
    private boolean enabledNegativeButton;
    private OnButtonClick positiveButtonCallback;
    private OnButtonClick negativeButtonCallback;

    public MessageDialog(String message, String nameNegativeButton, String namePositiveButton, boolean enabledNegativeButton, OnButtonClick positiveButtonCallback) {
        this.message = message;
        this.nameNegativeButton = nameNegativeButton;
        this.namePositiveButton = namePositiveButton;
        this.enabledNegativeButton = enabledNegativeButton;
        this.positiveButtonCallback = positiveButtonCallback;
    }

    public MessageDialog(String message, String nameNegativeButton, String namePositiveButton, boolean enabledNegativeButton) {
        this.message = message;
        this.nameNegativeButton = nameNegativeButton;
        this.namePositiveButton = namePositiveButton;
        this.enabledNegativeButton = enabledNegativeButton;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentDialog);
    }

    @NonNull
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstance) {
        return inflater.inflate(R.layout.message_dialog, container);
    }

    @NonNull
    public void onViewCreated (@NonNull View view, @NonNull Bundle savedInstance) {
        TextView messageView = view.findViewById(R.id.text_message);
        Button posButton = view.findViewById(R.id.message_dialog_positive_button);
        Button negButton = view.findViewById(R.id.message_dialog_negative_button);

        messageView.setText(message);
        posButton.setText(namePositiveButton);

        posButton.setOnClickListener(v -> {
            if (positiveButtonCallback != null) {
                positiveButtonCallback.execute();
            }
            dismiss();
        });

        negButton.setOnClickListener(v -> {
            if (negativeButtonCallback != null) {
                negativeButtonCallback.execute();
            }
            dismiss();
        });

        if (enabledNegativeButton) {
            negButton.setText(nameNegativeButton);
        } else {
            negButton.setVisibility(View.GONE);
        }
    }
}
