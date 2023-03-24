package com.example.pr13

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.example.pr13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var launcher: ActivityResultLauncher<Intent>? = null
    private val zagadki = mutableListOf(
            "Под гору — коняшка, в гору — деревяшка.+Санки",
            "В доску спрячется бедняжка — чуть видна его фуражка.+Гвоздь",
            "Зимой и летом одним цветом.+Ёлка",
            "Сперва блеск, за блеском — треск!+Гроза",
            "Рыжая плутовка, хитрая да ловкая, в сарай попала, кур пересчитала.+Лиса",
            "К нам приехали с бахчи полосатые мячи.+Арбуз",
            "Белые горошки на зелёной ножке.+Ландыш",
            "По реке плывет бревно. Ох, и злющее оно!+Крокодил",
            "Всех я вовремя бужу, хоть часов не завожу.+Дятел",
            "Упадет — поскачет, ударишь — не плачет.+Мяч"
        ).shuffled()
    private var wins = 0
    private var loses = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result: ActivityResult ->
            if (result.resultCode == RESULT_OK){
                binding.txtAnswer.text = result.data?.getStringExtra("answerRadio")
                Check()
                Buttons(false)
                if (binding.txtCount.text == "10/10") End()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    fun GenerateZagadka(view: View){
        binding.txtZagadka.text = zagadki[binding.txtCount.text.split('/')[0].toInt()].substringBeforeLast("+")
        binding.txtCount.text = (binding.txtCount.text.split('/')[0].toInt() + 1).toString() + "/10"
        binding.txtAnswer.setBackgroundColor(Color.TRANSPARENT)
        binding.txtAnswer.text = ""
        Buttons(true)
    }
    fun OpenAnswerList(view: View){
        val intent = Intent(this, AnswerList::class.java)
        intent.putExtra("answers", zagadki.toTypedArray())
        launcher?.launch(intent)
    }
    fun Statistic(view: View){
        val intent = Intent(this, Statistic::class.java)
        intent.putExtra("wins", wins.toString())
        intent.putExtra("loses", loses.toString())
        startActivity(intent)
    }
    fun Restart(view: View) {
        finish()
        startActivity(intent)
    }
    fun Exit(view: View) = finishAndRemoveTask()
    fun Check(){
        if (zagadki[binding.txtCount.text.split('/')[0].toInt()-1].contains(binding.txtAnswer.text)){
            wins++
            binding.txtAnswer.setBackgroundColor(Color.GREEN)
        }
        else {
            loses++
            binding.txtAnswer.setBackgroundColor(Color.RED)
        }
    }
    private fun Buttons(value: Boolean){
        binding.btnZagadka.isEnabled = !value
        binding.btnAnswer.isEnabled = value
    }
    fun End(){
        binding.btnAnswer.isEnabled = false
        binding.btnZagadka.isEnabled = false
        binding.btnStat.isEnabled = true
        binding.txtAnswer.isVisible = false
        binding.txtZagadka.isVisible = false
        binding.txtYourAnswer.isVisible = false
        binding.txtCount.isVisible = false
        binding.LinearLayoutButtons.isVisible = true
    }
}
