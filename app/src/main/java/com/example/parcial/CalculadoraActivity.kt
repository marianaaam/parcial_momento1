package com.example.parcial

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalculadoraActivity : AppCompatActivity() {

    // Inicializar las variables
    private lateinit var etEnergia: EditText
    private lateinit var etAgua: EditText
    private lateinit var etResiduos: EditText
    private lateinit var etReciclaje: EditText
    private lateinit var btnCalcular: Button
    private lateinit var tvResultado: TextView

    // Factores de impacto
    private val factorEmisionElectrico = 0.5 // kg CO2/kWh
    private val factorEscasezAgua = 0.002 // kg CO2/litro
    private val factorReciclaje = 0.1 // kg CO2/kg

    // Historial de mediciones
    private val historial = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        // Inicializar vistas
        etEnergia = findViewById(R.id.et_cenergía)
        etAgua = findViewById(R.id.et_cagua)
        etResiduos = findViewById(R.id.et_residuos)
        etReciclaje = findViewById(R.id.et_reciclaje)
        btnCalcular = findViewById(R.id.b_calculadora)
        tvResultado = findViewById(R.id.textView)

        // Configurar el botón de calcular
        btnCalcular.setOnClickListener {
            calcularImpactoAmbiental()
        }
    }

    private fun calcularImpactoAmbiental() {
        // Obtener los valores ingresados por el usuario
        val consumoElectrico = etEnergia.text.toString().toDoubleOrNull() ?: 0.0
        val consumoAgua = etAgua.text.toString().toDoubleOrNull() ?: 0.0
        val generacionResiduos = etResiduos.text.toString().toDoubleOrNull() ?: 0.0
        val porcentajeReciclaje = etReciclaje.text.toString().toDoubleOrNull() ?: 0.0

        // Validar que los valores sean mayores a cero
        if (consumoElectrico <= 0 || consumoAgua <= 0 || generacionResiduos <= 0 || porcentajeReciclaje < 0) {
            Toast.makeText(this, "Por favor, ingresa valores válidos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Realizar cálculos
        val impactoElectrico = consumoElectrico * factorEmisionElectrico
        val impactoAgua = consumoAgua * factorEscasezAgua
        val reduccionReciclaje = generacionResiduos * (porcentajeReciclaje / 100) * factorReciclaje
        val huellaTotal = impactoElectrico + impactoAgua - reduccionReciclaje

        // Mostrar resultados
        val resultado = """
            Huella ambiental total: ${"%.2f".format(huellaTotal)} kg CO2
            Impacto por consumo eléctrico: ${"%.2f".format(impactoElectrico)} kg CO2
            Impacto por consumo de agua: ${"%.2f".format(impactoAgua)} kg CO2
            Reducción por reciclaje: ${"%.2f".format(reduccionReciclaje)} kg CO2
        """.trimIndent()
        tvResultado.text = resultado

        // Guardar en el historial
        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val registro = "Fecha: $fechaActual, Huella: ${"%.2f".format(huellaTotal)} kg CO2"
        historial.add(registro)

        // Mostrar mensaje de éxito
        Toast.makeText(this, "Cálculo realizado y guardado en el historial.", Toast.LENGTH_SHORT).show()
    }

    // Función para mostrar el historial (puedes implementar una nueva Activity o Dialog)
    private fun mostrarHistorial() {
        val historialCompleto = historial.joinToString("\n")
        Toast.makeText(this, historialCompleto, Toast.LENGTH_LONG).show()
    }
}
}