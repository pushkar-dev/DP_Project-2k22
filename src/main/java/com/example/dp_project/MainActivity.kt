package com.example.dp_project

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val itemAdapter=Adapters(this.applicationContext, this)
        val alarmSetter= itemAdapter.alarmSetter

        // init the drop down menus
        val daySelect= findViewById<View>(R.id.dayEntry) as Spinner
        val slotSelect= findViewById<View>(R.id.slotEntry) as Spinner

        daySelect.adapter = itemAdapter.getDayAdapter()
        slotSelect.adapter = itemAdapter.getSlotAdapter()


        // set view for show time table
        val showBtn = findViewById<View>(R.id.showbtn) as Button
        showBtn.setOnClickListener{itemAdapter.buildShowOnclick()}

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
            itemAdapter.alarmSetButtonEvent(topViewTime,daySelect.selectedItem as String, slotSelect.selectedItem as String)
        }

        //default button
        val defaultBtn=findViewById<View>(R.id.defaultBtn)
        defaultBtn.setOnClickListener()
        {
            alarmSetter.ensureDefault()
        }

        //status text
        val statusText=findViewById<View>(R.id.statusText) as TextView
        val handler=Handler(Looper.getMainLooper())
        handler.post {
            var isConnected=false
            Thread{
                val status = Connectivity.ping(alarmSetter.hostUrl)
                isConnected=(status=="Ready")
            }.start()
            if(isConnected)
            {
                statusText.text=R.string.connected.toString()
                statusText.setTextColor(Color.parseColor("#03fc66"))
            }
            else
            {
                statusText.text=R.string.disconnected.toString()
                statusText.setTextColor(Color.parseColor("#fc2c03"))
            }
        }
    }
}