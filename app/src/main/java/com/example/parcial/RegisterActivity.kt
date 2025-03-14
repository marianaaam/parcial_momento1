package com.example.parcial

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.util.Log
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity(){

    private lateinit var editTextNombres : EditText
    private lateinit var editTextApellidos : EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var editTextRcontrasena: EditText
    private lateinit var buttonRegistro : Button
    private lateinit var textLogin : TextView
    private lateinit var checkBoxTyc: CheckBox

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Log.d("RegisterActivity", "onCreate: Inicializando el activity de registro",)

        editTextNombres = findViewById(R.id.et_names)
        editTextApellidos = findViewById(R.id.et_surnames)
        editTextCorreo = findViewById(R.id.et_email)
        editTextTelefono = findViewById(R.id.et_phone)
        editTextContrasena = findViewById(R.id.et_password)
        editTextRcontrasena = findViewById(R.id.et_confirmPassword)
        buttonRegistro = findViewById(R.id.bt_ingresar)
        textLogin = findViewById(R.id.tv_login)
        checkBoxTyc = findViewById(R.id.cb_tyc)

        //Archivo de almacenamiento local

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)

        buttonRegistro.setOnClickListener {
            //Validar campos
            if (validarCampos()){
                //guardar datos
                guardarDatos()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                //redireccionar al usuario a Login
            }

        }

        textLogin.setOnClickListener {
            //redireccionar al usuario a Login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
        }
    }

    private fun validarCampos() : Boolean {
        val nombres = editTextNombres.text.toString().trim()
        val apellidos = editTextApellidos.text.toString().trim()
        val correo = editTextCorreo.text.toString().trim()
        val telefono = editTextTelefono.text.toString().trim()
        val contrasena = editTextContrasena.text.toString().trim()
        val rcontrasena = editTextRcontrasena.text.toString().trim()

        if (nombres.isEmpty()){
            Toast.makeText(this, "El campo nombres es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        }

        if (apellidos.isEmpty()) {
            Toast.makeText(this, "El campo apellidos es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        }

        if (correo.isEmpty()){
            Toast.makeText(this, "El campo correo es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            Toast.makeText(this, "El correo electrónico no es válido", Toast.LENGTH_SHORT).show()
            return false
        }

        if (telefono.isEmpty()) {
            Toast.makeText(this, "El campo teléfono es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        } else if (!telefono.matches(Regex("^[0-9]{10}$"))) {
            Toast.makeText(this, "El teléfono debe tener 10 dígitos", Toast.LENGTH_SHORT).show()
            return false
        }

        if (contrasena.isEmpty()) {
            Toast.makeText(this, "El campo contraseña es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        }

        if (rcontrasena.isEmpty()) {
            Toast.makeText(this, "El campo repetir contraseña es obligatorio", Toast.LENGTH_SHORT).show()
            return false
        } else if (contrasena != rcontrasena) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!checkBoxTyc.isChecked) {
            Toast.makeText(this, "Debes aceptar los términos y condiciones", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }


    private fun guardarDatos(){
        val editor = sharedPreferences.edit()
        editor.putString("nombres", editTextNombres.text.toString().trim())
        editor.putString("apellidos", editTextApellidos.text.toString().trim())
        editor.putString("correo", editTextCorreo.text.toString().trim())
        editor.putString("telefono", editTextTelefono.text.toString().trim())
        editor.putString("contrasena", editTextContrasena.text.toString().trim())
        editor.apply()

        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
    }

}