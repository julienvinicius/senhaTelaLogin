package com.example.myintents

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private val usersList = mutableListOf<Pair<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()

        setupTextWatchers()

        setupLoginButton()
    }

    private fun initializeViews() {
        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)

        usersList.add(Pair("user", "1234"))

        loginButton.isEnabled = false
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val username = usernameEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                loginButton.isEnabled = username.isNotEmpty() && password.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        usernameEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)
    }

    private fun setupLoginButton() {
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (isValidUser(username, password)) {
                openSecondActivity()
            } else {
                showErrorDialog()
            }
        }
    }

    private fun isValidUser(username: String, password: String): Boolean {
        return usersList.any { it.first == username && it.second == password }
    }

    private fun openSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY)
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Erro de Login")
            setMessage("Nome de usuário ou senha incorretos.")
            setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            create()
            show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY && resultCode == RESULT_OK) {
            val newUser = data?.getStringExtra("newUser")
            val newPassword = data?.getStringExtra("newPassword")
            newUser?.let { username ->
                newPassword?.let { password ->
                    usersList.add(Pair(username, password))
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_SECOND_ACTIVITY = 100
    }
}
