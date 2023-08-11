package com.example.vanguardcardcreator

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import kotlin.math.roundToInt

class UnitPainter constructor(
    private val images:List<ImageBitmap>,
    private val srcOffset: IntOffset = IntOffset.Zero,
    private val srcSize: IntSize = IntSize(images[0].width, images[0].height)
) :Painter() {
    private fun validateSize(srcOffset: IntOffset, srcSize: IntSize): IntSize {
        require(
            srcOffset.x >= 0 &&
                    srcOffset.y >= 0 &&
                    srcSize.width >= 0 &&
                    srcSize.height >= 0 &&
                    srcSize.width <= images[0].width &&
                    srcSize.height <= images[0].height
        )
        return srcSize
    }
    private val size: IntSize = validateSize(srcOffset, srcSize)

    override val intrinsicSize: Size
        get() = size.toSize()

    override fun DrawScope.onDraw() {
        //0 card art image
        drawImage(
            images[0],
            srcOffset,
            srcSize,
            dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            )
        )
        //1 Base
        drawImage(
            images[1],
            srcOffset,
            srcSize,
            dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            )
        )
        //2 Grade symbol
        //3 Grade Number
        //4 Skill icon
        drawImage(
            images[2],
            IntOffset(-33,-410),
            srcSize,
            dstSize = IntSize(
                1010,
                1510
            )
        )
        //5 Name Bar
        //6 Label
        //7 Name Text
        //8 Race Bar
        //9 Race Text
        //10 Shield Symbol
        //11 Shield Number
        //12 Power Number
        //13 Critical
        //14 Persona Ride
    }
}

@Preview
@Composable
fun CombineUnitPreview() {
    val context = LocalContext.current
    val background = drawableToBitmap(context, R.drawable.sample_unit)?.asImageBitmap()
    val base = drawableToBitmap(context, R.drawable.base_normal)?.asImageBitmap()
    val skill = drawableToBitmap(context, R.drawable.skill_tripple_drive)?.asImageBitmap()
    val grade = drawableToBitmap(context, R.drawable.grade_dark_state_unit)?.asImageBitmap()
    val gradeNum = drawableToBitmap(context, R.drawable.sample_unit)?.asImageBitmap()
    val nameBar = drawableToBitmap(context, R.drawable.name_keter_sanctuary_unit)?.asImageBitmap()
    Image(
        painter = UnitPainter(listOf(background,base,grade) as List<ImageBitmap>),
        contentDescription = null)
}

// @Composable
// fun createImageBitmapFromText(text: String, style: TextStyle): ImageBitmap {
// val density = LocalDensity.current.density
// val context = LocalContext.current
//
// val textView = TextView(context)
// textView.text = text
// textView.textSize = style.fontSize.value
//
// val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
// val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
// textView.measure(widthMeasureSpec, heightMeasureSpec)
// textView.layout(0, 0, textView.measuredWidth, textView.measuredHeight)
//
// val bitmap = createBitmap(
// (textView.width * density).toInt(),
// (textView.height * density).toInt(),
// Bitmap.Config.ARGB_8888
// )
// val canvas = Canvas(bitmap)
// canvas.scale(density, density)
// textView.draw(canvas)
//
// return bitmap.asImageBitmap()
// }