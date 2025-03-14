package com.example.parcial

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.SharedPreferences
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RecoverPasswordActivity : AppCompatActivity (){

    private lateinit var EditTextEmail: EditText
    private lateinit var Btcorreo: Button

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recover_password)

        EditTextEmail = findViewById(R.id.ed_email)
        Btcorreo = findViewById(R.id.bt_instructions)

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        Btcorreo.setOnClickListener {
            val email = EditTextEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            } else {
                if (checkEmail(email)) {
                    Toast.makeText(this, "Se han enviado las instrucciones a $email", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "El correo electrónico no existe", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkEmail(email: String): Boolean {
        val savedEmail = sharedPreferences.getString("correo", null)
        return savedEmail == email
    }
}