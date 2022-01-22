package com.ameen.expirydatetracker.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ameen.expirydatetracker.R
import com.ameen.expirydatetracker.data.local.AppDatabase
import com.ameen.expirydatetracker.databinding.ActivityMainBinding
import com.ameen.expirydatetracker.repository.ItemRepository
import com.ameen.expirydatetracker.util.Utils
import com.ameen.expirydatetracker.viewmodel.ItemViewModel
import com.ameen.expirydatetracker.viewmodel.ItemViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private var binding: ActivityMainBinding? = null

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navBottomBar: BottomNavigationView
    private lateinit var navController: NavController

    lateinit var itemViewModel: ItemViewModel
    private lateinit var itemRepository: ItemRepository
    private lateinit var localDataBase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initView()

        initViewModel()

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

    private fun initViewModel() {
        localDataBase = AppDatabase.invoke(this)
        itemRepository = ItemRepository(localDataBase)
        itemViewModel = ViewModelProvider(
            this,
            ItemViewModelProvider(itemRepository)
        ).get(ItemViewModel::class.java)
    }

    private fun initView() {
        binding?.bottomNavigationView?.background = null

        navBottomBar = binding?.bottomNavigationView!!
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        navBottomBar.setupWithNavController(navController)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (intentResult != null) {
            if (intentResult.contents != null) {

                //Get Scanned Data and convert it from JSON to Item Object
                val result = Utils.parsingScannedData(intentResult.contents)

                Log.i(TAG, "onActivityResult: ScannedData $result")
                Log.i(TAG, "onActivityResult: ${Utils.getExpireDaysLeft(result.expireDate!!)}")

                AlertDialog.Builder(this)
                    //Convert data object to String to show it up.
                    .setMessage(Utils.convertScannedData(result))
                    .setTitle("Result")
                    .setPositiveButton("Save", DialogInterface.OnClickListener { _, _ ->
                        val days = Utils.getExpireDaysLeft(result.expireDate)
                        if (days < 0) {
                            result.isExpired = true
                            Toast.makeText(this, "This product is expired", Toast.LENGTH_SHORT)
                                .show()
                        } else result.daysLeft = days
                        itemViewModel.insertItemLocally(result)
                    })
                    .create()
                    .show()
            } else Toast.makeText(this, "No Result Found", Toast.LENGTH_SHORT).show()
        } else super.onActivityResult(requestCode, resultCode, data)
    }

}