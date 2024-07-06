package com.carbonera.dejafuel

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListarCombustivelView : AppCompatActivity() {

    private lateinit var lvCombustivel : ListView
    private var input: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_combustivel_view)

        lvCombustivel = findViewById(R.id.lvCombustivel)
        input = intent.getIntExtra("input", 0)

        lvCombustivel.setOnItemClickListener { _, _, position, _ ->
            val regex = Regex("[^0-9\\.]")
            val itemSelecionado = lvCombustivel.getItemAtPosition(position).toString()
            val rendimentoSelecionado = itemSelecionado.replace(regex, "")

            intent.putExtra("input", input)
            intent.putExtra("combustivelLabel", itemSelecionado.split(" ")[0])
            intent.putExtra("rendimento", rendimentoSelecionado)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}