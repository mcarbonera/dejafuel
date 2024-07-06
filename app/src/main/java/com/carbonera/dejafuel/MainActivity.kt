package com.carbonera.dejafuel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etRendimento1 : EditText
    private lateinit var etRendimento2 : EditText
    private lateinit var btBuscar1 : Button
    private lateinit var btBuscar2 : Button
    private lateinit var etCusto1 : EditText
    private lateinit var etCusto2 : EditText
    private lateinit var btCalcular : Button

    private lateinit var nomeCombustivel1 : String
    private lateinit var nomeCombustivel2 : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etRendimento1 = findViewById(R.id.et_combustivel1)
        etRendimento2 = findViewById(R.id.et_combustivel2)
        btBuscar1 = findViewById(R.id.bt_buscar1)
        btBuscar2 = findViewById(R.id.bt_buscar2)
        etCusto1 = findViewById(R.id.et_custo1)
        etCusto2 = findViewById(R.id.et_custo2)
        btCalcular = findViewById(R.id.bt_calcular)

        btBuscar1.setOnClickListener {
            btBuscarRendimentoOnClick(1)
        }

        btBuscar2.setOnClickListener {
            btBuscarRendimentoOnClick(2)
        }

        btCalcular.setOnClickListener {
            btCalcularMelhorCombustivel()
        }
    }

    private fun btBuscarRendimentoOnClick(input: Int) {
        val intent = Intent(this, ListarCombustivelView::class.java)
        intent.putExtra("input", input)
        getResult.launch(intent)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == RESULT_OK && it.data != null) {
            val input = it.data?.getIntExtra("input", 0)
            val nomeCombustivel = it.data?.getStringExtra("combustivelLabel")
            val rendimentoStr = it.data?.getStringExtra("rendimento")
            val rendimento = rendimentoStr?.toFloat()
            if(input == 1) {
                etRendimento1.setText(rendimento.toString())
                nomeCombustivel1 = nomeCombustivel.toString()
            }
            if(input == 2) {
                etRendimento2.setText(rendimento.toString())
                nomeCombustivel2 = nomeCombustivel.toString()
            }
        }
    }

    private fun btCalcularMelhorCombustivel() {
        if(etRendimento1.text.toString() == "" ||
            etRendimento2.text.toString() == "" ||
            etCusto1.text.toString() == "" ||
            etCusto2.text.toString() == "") {
            Toast.makeText(this, getString(R.string.toast_invalid_input), Toast.LENGTH_LONG).show()
            return
        }
        val rendimento1 = etRendimento1.text.toString().toFloat()
        val rendimento2 = etRendimento2.text.toString().toFloat()
        val custo1 = etCusto1.text.toString().toFloat()
        val custo2 = etCusto2.text.toString().toFloat()
        val indice1 = rendimento1/custo1
        val indice2 = rendimento2/custo2

        if(indice1 > indice2) {
            Toast.makeText(this,
                getString(R.string.maior_rendimento) + " " + nomeCombustivel1,
                Toast.LENGTH_LONG).show()
        } else if(indice1 < indice2) {
            Toast.makeText(this,
                getString(R.string.maior_rendimento) + " " + nomeCombustivel2,
                Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, getString(R.string.rendimento_igual),
                Toast.LENGTH_LONG).show()
        }
    }
}