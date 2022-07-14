package com.example.dp_project


import android.content.Context
import android.provider.AlarmClock
import java.io.*


class Alarm(private var ctx: Context, private val filename: String = "alarmDump.txt") {
    private val clock:AlarmClock = AlarmClock()
    private var timeTable: Array<FloatArray> =Array(7){FloatArray(3)}
    private val dayOfWeek: Array<String> =arrayOf("mon","tue","wed","thu","fri","sat","sun")
    init {
        arrayOf(
            floatArrayOf(0f,0f,0f),
            floatArrayOf(0f,0f,0f),
            floatArrayOf(0f,0f,0f),
            floatArrayOf(0f,0f,0f),
            floatArrayOf(0f,0f,0f),
            floatArrayOf(0f,0f,0f),
            floatArrayOf(0f,0f,0f),
        ).also { timeTable = it }
    }

    fun dump(tt: Array<FloatArray>) // dump input data to file
    {
        val f= File(this.ctx.filesDir,this.filename)
        var s=""
        for( i in 0..6)
        {
            for(j in 0..2)
            {
                s+=tt[i][j].toString()+" "
            }
            s+="\n"
        }
        f.writeText(s)
    }
    fun fetch() // fetch data from file dump
    {
        val f= File(this.ctx.filesDir, this.filename)
        val s:String=f.readText()
        for((i, line) in s.trim().split("\n").withIndex())
        {
            for((j,istr) in line.split(" ").withIndex())
            {
                this.timeTable[i][j]=istr.toFloat()
            }
        }
    }

    fun sync() // posts same data to esp32 web server
    {
        var res:String?
        var req:String?="{"
        for((i,row) in this.timeTable.withIndex())
        {
            req+="\""+this.dayOfWeek[i]+"\":["
            for((j,value) in row.withIndex())
            {
                req+=value.toString()
                if(j!=2) req+=","
            }
            req+="]"
            if(i!=6) req+=","
        }
        req+="}"
        // all network operations must not be done in main thread
        Thread{
            res=Connectivity.ping("http://192.168.43.243/")
            if(res=="Ready")
            {
                res=Connectivity.send("http://192.168.43.243/get","data",req)
                println(res) //only for debugging purpose
            }
            else
            {
                println("Host not up, kindly crosscheck the ip or device power!")
            }
        }.start()
    }

    fun setAlarm()// sets alarm for all times in float array
    {
        // sets alarm for all times in float array
    }

}