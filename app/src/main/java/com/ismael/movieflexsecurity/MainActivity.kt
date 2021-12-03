package com.ismael.movieflexsecurity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ismael.movieflexsecurity.encryption.Security

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val security: Security = Security(this)

        val asd = security.encrypt("Ismael Ochoa")
        println("Encription " + asd)
        val dsa = security.decrypt(asd)
        println("Decryption: $dsa" )

    }
}