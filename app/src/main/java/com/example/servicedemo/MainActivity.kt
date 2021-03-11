package com.example.servicedemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var mStartButton: Button
    private lateinit var mStopButton: Button
    private lateinit var mBindButton: Button
    private lateinit var mUnbindButton: Button
    private lateinit var downloadBinder: MyService.DownloadBinder

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            //服务建立绑定时调用
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            TODO("当服务被意外关闭时调用")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mStartButton = findViewById(R.id.startServiceButton)
        mStopButton = findViewById(R.id.stoptServiceButton)
        mBindButton = findViewById(R.id.bindServiceButton)
        mUnbindButton = findViewById(R.id.unbindServiceButton)

        mStartButton.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }

        mStopButton.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent)
        }

        mBindButton.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            bindService(intent,connection, BIND_AUTO_CREATE)
        }

        mUnbindButton.setOnClickListener {
            unbindService(connection)
        }
    }
}