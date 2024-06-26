package com.example.myintents

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var newUsernameEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        initializeViews()
        setupTextWatchers()
        setupSaveButton()
    }

    private fun initializeViews() {
        newUsernameEditText = findViewById(R.id.editTextNewUsername)
        newPasswordEditText = findViewById(R.id.editTextNewPassword)
        saveButton = findViewById(R.id.buttonSave)
        saveButton.isEnabled = false
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newUsername = newUsernameEditText.text.toString().trim()
                val newPassword = newPasswordEditText.text.toString().trim()
                saveButton.isEnabled = newUsername.isNotEmpty() && newPassword.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        newUsernameEditText.addTextChangedListener(textWatcher)
        newPasswordEditText.addTextChangedListener(textWatcher)
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            val newUsername = newUsernameEditText.text.toString().trim()
            val newPassword = newPasswordEditText.text.toString().trim()

            val resultIntent = Intent()
            resultIntent.putExtra("newUser", newUsername)
            resultIntent.putExtra("newPassword", newPassword)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
