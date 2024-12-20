package com.labsi.myintentapp

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MoveWithObjectActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_with_object)

        val tvObject:TextView = findViewById(R.id.tv_object_received)

        val person = if (Build.VERSION.SDK_INT >= 33){
//            intent.getParcelableExtra<Person>(EXTRA_PERSON, Person::class.java)
            intent.getParcelableArrayListExtra(EXTRA_PERSON, Person::class.java)
        }else{
            @Suppress("DEPRECATION")
//            intent.getParcelableExtra<Person>(EXTRA_PERSON)
            intent.getParcelableArrayListExtra(EXTRA_PERSON)
        }

//        if (person != null){
//            val text = "Name: ${person.name.toString()},\nYour Age: ${person.age},\nYour Email: ${person.email},\nLocation: ${person.city}"
//            tvObject.text = text
//        }
        if (person != null && person.isNotEmpty()) {
            val personDetails = person.joinToString(separator = "\n\n") { p ->
                "Name: ${p.name},\nYour Age: ${p.age},\nYour Email: ${p.email},\nLocation: ${p.city}"
            }
            tvObject.text = personDetails
        }
    }
}