package com.example.vanguardcardcreator

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.activity.compose.ReportDrawn
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.core.content.ContextCompat
import com.example.vanguardcardcreator.model.Card
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

val allNation = listOf(Nation.BRANDT_GATE,Nation.DARK_STATES,Nation.STOICHEIA,Nation.KETER,Nation.DRAGON_EMPIRE,Nation.LYRICAL_MONASTERIO,"None")

fun getGradeStandard(nation:String ,type: String):Int {
    var result = -1
    if (type == CardType.ORDER) {
        when (nation) {
            Nation.BRANDT_GATE -> {
                result = R.drawable.grade_brandt_gate_order
            }
            Nation.DARK_STATES -> {
                result = R.drawable.grade_dark_state_order
            }
            Nation.DRAGON_EMPIRE -> {
                result = R.drawable.grade_dragon_empire_order
            }
            Nation.KETER -> {
                result = R.drawable.grade_keter_sanctuary_order
            }
            Nation.STOICHEIA -> {
                result = R.drawable.grade_stoicheia_order
            }
            Nation.LYRICAL_MONASTERIO -> {
                result = R.drawable.grade_lyrical_monasterio_order
            }
            else -> {
                result = R.drawable.grade_no_nation_order
            }
        }
    } else if (type == CardType.UNIT) {
        when (nation) {
            Nation.BRANDT_GATE -> {
                result = R.drawable.grade_brandt_gate_unit
            }

            Nation.DARK_STATES -> {
                result = R.drawable.grade_dark_state_unit
            }

            Nation.DRAGON_EMPIRE -> {
                result = R.drawable.grade_dragon_empire_unit
            }

            Nation.KETER -> {
                result = R.drawable.grade_keter_sanctuary_unit
            }

            Nation.STOICHEIA -> {
                result = R.drawable.grade_stoicheia_unit
            }

            Nation.LYRICAL_MONASTERIO -> {
                result = R.drawable.grade_lyrical_monasterio_unit
            }
            else -> {
                result = R.drawable.grade_no_nation_unit
            }
        }
    }

    return result
}
fun getNameBarStandard(nation: String, type: String):Int {
    var result = -1
    if (type == CardType.ORDER) {
        when (nation) {
            Nation.BRANDT_GATE -> {
                result = R.drawable.name_brandt_gate_order
            }
            Nation.DARK_STATES -> {
                result = R.drawable.name_dark_state_order
            }
            Nation.DRAGON_EMPIRE -> {
                result = R.drawable.name_no_nation_order
            }
            Nation.KETER -> {
                result = R.drawable.name_no_nation_order
            }
            Nation.STOICHEIA -> {
                result = R.drawable.name_stoicheia_order
            }
            Nation.LYRICAL_MONASTERIO -> {
                result = R.drawable.name_no_nation_order
            }
            else -> {
                result = R.drawable.name_no_nation_order
            }
        }
    } else if (type == CardType.UNIT) {
        when (nation) {
            Nation.BRANDT_GATE -> {
                result = R.drawable.name_brandt_gate_unit
            }

            Nation.DARK_STATES -> {
                result = R.drawable.name_dark_state_unit
            }

            Nation.DRAGON_EMPIRE -> {
                result = R.drawable.name_dragon_empire_unit
            }

            Nation.KETER -> {
                result = R.drawable.name_keter_sanctuary_unit
            }

            Nation.STOICHEIA -> {
                result = R.drawable.name_stoicheia_unit
            }

            Nation.LYRICAL_MONASTERIO -> {
                result = R.drawable.name_lyrical_monasterio_unit
            }
            else -> {
                result = R.drawable.name_no_nation_unit
            }
        }
    }

    return result
}

fun getBase(category: String): Int {
    return when(category) {
        Label.NORMAL -> R.drawable.base_normal
        Label.TRIGGER -> R.drawable.base_trigger
        Label.ORDER -> R.drawable.base_normal_order
        Label.SET -> R.drawable.base_set_order
        Label.BLITZ -> R.drawable.base_blitz_order
        CardType.MARKER -> R.drawable.base_marker
        else -> R.drawable.base_normal
    }
}

@Composable
fun getFlag(name: String): Painter {
    return when(name) {
        Nation.BRANDT_GATE -> painterResource(id = R.drawable.flag_brandt_gate)
        Nation.KETER -> painterResource(id = R.drawable.flag_keter_sanctuary)
        Nation.STOICHEIA -> painterResource(id = R.drawable.flag_stoicheia)
        Nation.DRAGON_EMPIRE -> painterResource(id = R.drawable.flag_dragon_empire)
        Nation.LYRICAL_MONASTERIO -> painterResource(id = R.drawable.flag_lyrical_monasterio)
        Nation.DARK_STATES -> painterResource(id = R.drawable.flag_dark_state)
        else -> painterResource(id = R.drawable.ic_launcher_foreground)
    }
}

fun getAbilityIcon(name: String):Int {
    return when(name) {
        Ability.BOOST -> R.drawable.skill_boost
        Ability.INTERCEPT -> R.drawable.skill_intercept
        Ability.TWIN_DRIVE -> R.drawable.skill_twin_drive
        Ability.TRIPLE_DRIVE -> R.drawable.skill_tripple_drive
        else -> R.drawable.skill_empty
    }
}

fun getEffectBox(text:String) {
    //TODO
}

fun getLabel(label: String): Int {
    return when(label) {
        Label.ORDER -> R.drawable.label_normal_order
        Label.NORMAL -> R.drawable.label_normal
        Label.TRIGGER -> R.drawable.label_trigger
        Label.BLITZ -> R.drawable.label_blitz
        Label.SET -> R.drawable.label_set
        Label.G -> R.drawable.label_g
        else -> 0
    }
}

fun getLabelNation(nation: String):Int {
    return when(nation) {
        Nation.STOICHEIA -> R.drawable.label_nation_stocheia
        Nation.KETER -> R.drawable.label_nation_keter_sanctuary
        Nation.LYRICAL_MONASTERIO -> R.drawable.label_nation_lyrical_monastrerio
        Nation.DRAGON_EMPIRE -> R.drawable.label_nation_dragon_empire
        Nation.DARK_STATES -> R.drawable.label_nation_dark_states
        Nation.BRANDT_GATE -> R.drawable.label_nation_brandt_gate
        else -> 0
    }
}

val all_order = listOf(Label.ORDER,Label.BLITZ,Label.SET)

fun getBitmapFromUri(context: Context, imageUri: Uri?): Bitmap? {
    var inputStream: InputStream? = null
    try {
        val contentResolver: ContentResolver = context.contentResolver
        // Open an input stream from the image URI
        inputStream = imageUri?.let { contentResolver.openInputStream(it) }

        // Decode the input stream into a Bitmap
        return BitmapFactory.decodeStream(inputStream)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        // Close the input stream to release resources
        inputStream?.close()
    }
    return null
}

fun bitmapToByteArray(bitmap: Bitmap?): ByteArray {
    val stream = ByteArrayOutputStream()
    bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun byteArrayToBitmap(byteArray: ByteArray): Bitmap? {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}


fun drawableToBitmap(context: Context, drawableResId: Int): Bitmap? {
    // Step 1: Get the Drawable from the resource
    val drawable = ContextCompat.getDrawable(context, drawableResId)

    // Check if the drawable is not null
    if (drawable != null) {
        // Step 2: Convert the Drawable to a Bitmap
        if (drawable is BitmapDrawable) {
            // If the drawable is already a BitmapDrawable, return its Bitmap
            return drawable.bitmap
        } else {
            // If the drawable is not a BitmapDrawable, create a Bitmap from it
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
    }
    return null
}


