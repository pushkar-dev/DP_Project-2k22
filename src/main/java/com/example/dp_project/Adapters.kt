package com.example.dp_project

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TimePicker
import androidx.annotation.RequiresApi

class Adapters(appCtx:Context, ctx:Context) {
    val alarmSetter:Alarm
    private val ctx:Context

    init {
        alarmSetter = Alarm(appCtx)
        this.ctx=ctx
    }

    //fun getAlarmSetter():Alarm{return alarmSetter}

    fun getDayAdapter():ArrayAdapter<CharSequence>
    {
        val adapter = ArrayAdapter.createFromResource(
            ctx,
            R.array.Days,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        return adapter
    }
    fun getSlotAdapter():ArrayAdapter<CharSequence> {
        val adapter = ArrayAdapter.createFromResource(
            ctx,
            R.array.Slot,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        return adapter
    }
    private fun wrongSelectAlertBuilder():AlertDialog.Builder
    {
        val wrongAlarmAlert=AlertDialog.Builder(ctx)
        wrongAlarmAlert.setTitle(R.string.wrongSelectAlert)
        wrongAlarmAlert.setMessage(R.string.wrongSelectMsg)
        return wrongAlarmAlert
    }

    fun buildShowOnclick()
    {
        val dialog = Dialog(ctx)
        dialog.setContentView(R.layout.dialog_tt)
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun alarmSetButtonEvent(topViewTime:Dialog, selectedDay:String, selectedSlot:String)
    {
        val t=topViewTime.findViewById<View>(R.id.timePicker1) as TimePicker
        println("Hour=${t.hour}")
        println("Minute=${t.minute}")
        val i= getDayAdapter().getPosition(selectedDay)
        val j= getSlotAdapter().getPosition(selectedSlot)
        val value:Int= (t.hour*100+t.minute)
        if(i>0 && j>0)
        {
            alarmSetter.timeTable[i-1][j-1]=value
        }
        else
        {
            val alertDialog=wrongSelectAlertBuilder()
            alertDialog.show()
        }
        println("slot=${selectedDay}")
        println("day=${selectedSlot}") // for debugging purposes only
        println("i=${i}, j=${j}, value=${value}")
        alarmSetter.dump()
    }
}