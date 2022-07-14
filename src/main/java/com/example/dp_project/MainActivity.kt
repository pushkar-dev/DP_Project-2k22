package com.example.dp_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init the drop down menus
        val daySelect= findViewById<View>(R.id.dayEntry) as Spinner
        val slotSelect= findViewById<View>(R.id.slotEntry) as Spinner

        val adapter1 = ArrayAdapter.createFromResource(this, R.array.Days,android.R.layout.simple_spinner_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item)
        val adapter2 = ArrayAdapter.createFromResource(this, R.array.Slot,android.R.layout.simple_spinner_item)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item)

        daySelect.adapter = adapter1
        slotSelect.adapter = adapter2

        val alarmSetter= Alarm(this.applicationContext)
        // set view for show time table
//        val btnMon = findViewById<View>(R.id.showbtn) as Button
//        btnMon.setOnClickListener()
//        {
//            val dialog = Dialog(this) as Dialog
//            dialog.setContentView(R.layout.dialog_tt)
//            dialog.show()
//        }
        //setSupportActionBar(toolbar
        val syncBtn=findViewById<View>(R.id.syncBtn) as Button
        syncBtn.setOnClickListener()
        {
            alarmSetter.sync()
        }
    }
}