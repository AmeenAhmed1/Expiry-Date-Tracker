package com.ameen.expirydatetracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ameen.expirydatetracker.R
import com.ameen.expirydatetracker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navBottomBar: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initView()

        binding?.scannerButton?.setOnClickListener {
            IntentIntegrator(this).apply {
                captureActivity = ScannerActivity::class.java
                setOrientationLocked(false)
                setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                setPrompt("Scanner")
                initiateScan()
            }
        }
    }

    private fun initView() {
        binding?.bottomNavigationView?.background = null

        navBottomBar = binding?.bottomNavigationView!!
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        navBottomBar.setupWithNavController(navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (intentResult != null) {
            if (intentResult.contents != null) {
                AlertDialog.Builder(this)
                    .setMessage(intentResult.contents)
                    .setTitle("Result")
                    .create()
                    .show()
            } else Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show()
        } else super.onActivityResult(requestCode, resultCode, data)

    }
}