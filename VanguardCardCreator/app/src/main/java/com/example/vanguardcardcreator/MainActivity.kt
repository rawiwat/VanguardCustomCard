package com.example.vanguardcardcreator

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vanguardcardcreator.screens.Navigator

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var fetchImageResultLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        fetchImageResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val imageBitmap = getBitmapFromUri(this@MainActivity, uri)
                val imageByteArray = bitmapToByteArray(imageBitmap)
                val intent = Intent("change_image")
                intent.putExtra("change_image", imageByteArray)
                sendBroadcast(intent)
            }
        }
        setContent {
            navController = rememberNavController()
            Navigator(navController = navController as NavHostController)
        }
    }

    fun fetchImage(context: Context) {
        fetchImageResultLauncher.launch("image/*")
    }

    private fun cropBitmap(bitmap: Bitmap, rect: Rect): Bitmap {
        return Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height())
    }
}

