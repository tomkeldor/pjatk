package com.example.helloworld

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        rejestruj.setOnClickListener {
            auth.createUserWithEmailAndPassword(et_login.text.toString(), et_pass.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        Toast.makeText(this, "Rejestracja powiodła się.", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(this, "Rejestracja nie powiodła się.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        zaloguj.setOnClickListener{
            auth.createUserWithEmailAndPassword(et_login.text.toString(), et_pass.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        Toast.makeText(this, "Logowanie powiodło się.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,MainActivity::class.java))
                    }
                    else
                    {
                        Toast.makeText(this, "Logowanie nie powiodło się.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}