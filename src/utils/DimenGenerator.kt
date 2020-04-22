/**
 * Android开发屏幕适配资源文件生成类
 */

package utils

import java.io.File
import java.text.DecimalFormat
import kotlin.math.abs

private const val dimenScale = 720F / 750F / 2F

private val decimalFormat = DecimalFormat("#.###")

private val dimenRange = -10..1280

private val fileDimenSizeArr = arrayOf(240, 270, 360, 384, 400, 411)

fun main() {
  fileDimenSizeArr.forEach {
    val saveDir = File("./result")
    if (saveDir.exists()) {
      saveDir.delete()
    }
    saveDir.mkdirs()
    val dimenDir = File(saveDir, "values-w${it}dp")
    if (dimenDir.exists()) {
      dimenDir.delete()
    }
    dimenDir.mkdirs()
    val dimenFile = File(dimenDir, "dimens.xml")
    val res = genDimenFile(it, 360)
    println("generate dimen --> w${it}dp")
    dimenFile.writeText(res)
  }
}

fun genDimenFile(currentSize: Int, baseSize: Int): String {
  val dimenBuilder = StringBuilder()
  dimenBuilder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
  dimenBuilder.append(System.lineSeparator())
  dimenBuilder.append("<resources>")

  dimenRange.forEach {
    if (it != 0) {
      dimenBuilder.append(System.lineSeparator())
      dimenBuilder.append("\t")
      val pxUnit = if (it < 0) {
        "px_${abs(it)}"
      } else {
        "px${it}"
      }
      val pxValue = "${decimalFormat.format(
          it * dimenScale * (currentSize.toFloat() / baseSize.toFloat())
      )}dp"
      dimenBuilder.append("<dimen name=\"${pxUnit}\">${pxValue}</dimen>")
    }
  }
  dimenBuilder.append(System.lineSeparator())
  dimenBuilder.append("</resources>")
  return dimenBuilder.toString()
}
