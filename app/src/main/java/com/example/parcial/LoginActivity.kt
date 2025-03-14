package com.example.parcial

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity () {

    private lateinit var editTextEmail : EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textRegister: TextView
    private lateinit var textRcontrasena : TextView

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.et_usuario)
        editTextContrasena = findViewById(R.id.et_contraseña)
        buttonLogin = findViewById(R.id.bt_ingresar)
        textRegister = findViewById(R.id.tv_registrate)
        textRcontrasena = findViewById(R.id.tv_RecuperarContraseña)

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextContrasena.text.toString().trim()

            if (validarCampos(email, password)) {
                if (verificarCredenciales(email, password)) {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        textRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        textRcontrasena.setOnClickListener{
            val intent = Intent(this, RecoverPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validarCampos(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            Toast.makeText(this, "El campo correo es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "El correo electrónico no es válido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "El campo contraseña es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun verificarCredenciales(email: String, password: String): Boolean {
        val savedEmail = sharedPreferences.getString("correo", null)
        val savedPassword = sharedPreferences.getString("contrasena", null)

        return email == savedEmail && password == savedPassword
    }
}