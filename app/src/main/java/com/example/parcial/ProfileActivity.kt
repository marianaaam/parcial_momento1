package com.example.parcial

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var editTextNombres: EditText
    private lateinit var editTextApellidos: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var buttonEditProfile: Button
    private lateinit var buttonCalculadora: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        editTextNombres = findViewById(R.id.et_names)
        editTextApellidos = findViewById(R.id.et_surnames)
        editTextTelefono = findViewById(R.id.et_phone)
        editTextCorreo = findViewById(R.id.et_email)
        buttonEditProfile = findViewById(R.id.b_editProfile)
        buttonCalculadora = findViewById(R.id.b_calculadora)

        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        // Cargar los datos del usuario desde SharedPreferences
        loadUserData()

        // Configurar el botón para editar el perfil
        buttonEditProfile.setOnClickListener {
            saveUserData()
        }

        buttonCalculadora.setOnClickListener {
            val intent = Intent(this, CalculadoraActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserData() {
        // Recuperar los datos del usuario desde SharedPreferences
        val nombres = sharedPreferences.getString("nombres", "")
        val apellidos = sharedPreferences.getString("apellidos", "")
        val correo = sharedPreferences.getString("correo", "")
        val telefono = sharedPreferences.getString("telefono", "")

        // Mostrar los datos en los EditText
        editTextNombres.setText(nombres)
        editTextApellidos.setText(apellidos)
        editTextCorreo.setText(correo)
        editTextTelefono.setText(telefono)
    }

    private fun saveUserData() {
        // Obtener los valores actuales de los EditText
        val nombres = editTextNombres.text.toString()
        val apellidos = editTextApellidos.text.toString()
        val correo = editTextCorreo.text.toString()
        val telefono = editTextTelefono.text.toString()

        // Guardar los datos en SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("nombres", nombres)
        editor.putString("apellidos", apellidos)
        editor.putString("correo", correo)
        editor.putString("telefono", telefono)
        editor.apply()

        // Mostrar un mensaje de confirmación
        Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
    }
}