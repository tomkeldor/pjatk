package com.example.helloworld

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.helloworld.MediaPlayerService.LocalBinder
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var player: MediaPlayerService
    var serviceBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        permissionCheck()
        auth = FirebaseAuth.getInstance()

        val serviceConnection: ServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                val binder = service as LocalBinder
                player = binder.service
                serviceBound = true
                Log.d("onServiceConnected", getString(R.string.done))
            }

            override fun onServiceDisconnected(name: ComponentName) {
                serviceBound = false
                Log.d("onServiceDisconnected", getString(R.string.done))
            }
        }
        val playerIntent = Intent(this, MediaPlayerService::class.java)
        startService(playerIntent)
        bindService(playerIntent, serviceConnection, Context.BIND_AUTO_CREATE)

        rejestruj.setOnClickListener {
            if (et_login.text.isNullOrEmpty() || et_pass.text.isNullOrEmpty()) {
                Toast.makeText(this, "Podaj e-mail oraz hasło", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(et_login.text.toString(), et_pass.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Rejestracja powiodła się", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                    this,
                                    "Rejestracja nie powiodła się",
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        zaloguj.setOnClickListener {
            if (et_login.text.isNullOrEmpty() || et_pass.text.isNullOrEmpty()) {
                Toast.makeText(this, "Podaj e-mail oraz hasło", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(et_login.text.toString(), et_pass.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Logowanie powiodło się.", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(this, "Logowanie nie powiodło się.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
    }

    private fun permissionCheck() {
        val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED

        ) {
            requestPermissions(perms, 0)
        }
    }
}