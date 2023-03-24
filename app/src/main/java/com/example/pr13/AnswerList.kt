package com.example.pr13

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.pr13.databinding.ActivityAnswerListBinding

class AnswerList : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerListBinding
    private var getAnswer = "Не выбран"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val radioGroup: RadioGroup = findViewById(binding.Group.id)
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            findViewById<RadioButton>(checkedId)?.apply { getAnswer = text.toString() }
        }
        CreateRadioButtons()
    }
    fun Check(view: View){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("answerRadio", getAnswer)
        setResult(RESULT_OK, intent)
        finish()
    }
    private fun CreateRadioButtons(){
        for (i in intent.getStringArrayExtra("answers")!!.indices.shuffled()) {
            val rb = RadioButton(this)
            rb.text = intent.getStringArrayExtra("answers")!![i].substringAfterLast("+")
            rb.id = View.generateViewId()
            binding.Group.addView(rb)
        }
    }
}