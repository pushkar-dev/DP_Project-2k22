package com.example.dp_project

import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.TimePicker
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
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

         val wrongAlarmAlert=AlertDialog.Builder(this)
        wrongAlarmAlert.setTitle(R.string.wrongSelectAlert)
        wrongAlarmAlert.setMessage(R.string.wrongSelectMsg)

        // set view for show time table
        val showBtn = findViewById<View>(R.id.showbtn) as Button
        showBtn.setOnClickListener()
        {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_tt)
            dialog.show()
        }

        //time picker
        val pickBtn=findViewById<View>(R.id.pickTime) as Button
        val topViewTime= Dialog(this)
        topViewTime.setContentView(R.layout.time_picker)

        pickBtn.setOnClickListener()
        {
            topViewTime.show()
        }

        //sync btn
        val syncBtn=findViewById<View>(R.id.syncBtn) as Button
        syncBtn.setOnClickListener()
        {
            alarmSetter.sync()
        }

        //set btn
        val setBtn=findViewById<View>(R.id.setBtn) as Button
        setBtn.setOnClickListener() //tested->OK
        {
            val t=topViewTime.findViewById<View>(R.id.timePicker1) as TimePicker
            println("Hour=${t.hour}")
            println("Minute=${t.minute}")
            val i= adapter1.getPosition(daySelect.selectedItem as String)
            val j= adapter2.getPosition(slotSelect.selectedItem as String)
            val value:Float= (t.hour*100f+t.minute)
            if(i>0 && j>0)
            {
                alarmSetter.timeTable[i-1][j-1]=value
            }
            else
            {
                val alertDialog=wrongAlarmAlert.create()
                alertDialog.show()
            }
            println("slot=${slotSelect.selectedItem}")
            println("day=${daySelect.selectedItem}") // for debugging purposes only
            println("i=${i}, j=${j}, value=${value}")
            alarmSetter.dump()
//            for (i in alarmSetter.fetch())
//            {
//                for( j in i){ println(j)}
//            }

        }
    }
}