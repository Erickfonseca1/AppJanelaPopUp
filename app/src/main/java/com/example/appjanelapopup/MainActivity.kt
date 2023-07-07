package com.example.appjanelapopup

import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var rvNomes: RecyclerView
    private lateinit var faButtonAdd: FloatingActionButton
    private lateinit var etName: EditText
    private lateinit var tts: TextToSpeech
    private var list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.rvNomes = findViewById(R.id.rvNomes)
        this.faButtonAdd = findViewById(R.id.faButtonAdd)

        this.faButtonAdd.setOnClickListener { add() }

        this.rvNomes.adapter = MyAdapter(this.list)
        (this.rvNomes.adapter as MyAdapter).onItemRecyclerView = OnItemClick()

        this.tts = TextToSpeech(this, null)

        ItemTouchHelper(OnSwipe()).attachToRecyclerView(this.rvNomes)
    }

    fun add(){
        this.etName = EditText(this)
        val builder = AlertDialog.Builder(this).apply {
            setTitle("Novo Nome!")
            setMessage("Digite o novo nome")
            setView(this@MainActivity.etName)
            setPositiveButton("Salvar", OnClick())
            setNegativeButton("Cancelar", null)
        }
        builder.create().show()
    }

    inner class OnClick: OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            val nome = this@MainActivity.etName.text.toString()
            (this@MainActivity.rvNomes.adapter as MyAdapter).add(nome)
        }
    }

    inner class OnItemClick: OnItemRecyclerView{
        override fun onItemClick(position: Int) {
            val name = this@MainActivity.list[position]
            this@MainActivity.tts.speak(name, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    inner class OnSwipe: ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.DOWN or ItemTouchHelper.UP,
        ItemTouchHelper.START or ItemTouchHelper.END
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            (this@MainActivity.rvNomes.adapter as MyAdapter).mov(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            (this@MainActivity.rvNomes.adapter as MyAdapter).del(viewHolder.adapterPosition)
        }
    }
}