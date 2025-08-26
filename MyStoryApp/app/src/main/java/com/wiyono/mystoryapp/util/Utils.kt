package com.wiyono.mystoryapp.util

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import com.wiyono.mystoryapp.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    DATE_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs()
        }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, timeStamp)
}

fun File.toBitmap(): Bitmap {
    return BitmapFactory.decodeFile(this.path)
}

fun Bitmap.rotateBitmap(isBackCamera: Boolean = false): Bitmap {
    val matrix = Matrix()
    return if (isBackCamera) {
        matrix.postRotate(90f)
        Bitmap.createBitmap(
            this,
            0,
            0,
            this.width,
            this.height,
            matrix,
            true
        )
    } else {
        matrix.postRotate(-90f)
        matrix.postScale(-1f, 1f, this.width / 2f, this.height / 2f)
        Bitmap.createBitmap(
            this,
            0,
            0,
            this.width,
            this.height,
            matrix,
            true
        )
    }
}

fun uriToFile(selectImage: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectImage) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

@Suppress("DEPRECATION")
fun String.withDateFormat(style: String = "long"): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val date = inputFormat.parse(this) as Date
    val indonesia = Locale("in", "ID")

    return when (style) {
        "short" -> {
            // Format pendek: 26/08/2025
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", indonesia)
            outputFormat.format(date)
        }
        "shortTime" -> {
            // Format pendek: 26/08/2025 11.42
            val outputFormat = SimpleDateFormat("dd/MM/yyyy HH.mm", indonesia)
            outputFormat.format(date)
        }
        "withTime" -> {
            // Format dengan waktu: Selasa, 26/08/2025 11.42
            val outputFormat = SimpleDateFormat("EEEE, dd/MM/yyyy HH.mm", indonesia)
            outputFormat.format(date)
        }
        else -> {
            // Format panjang (default): Selasa, 26 Agustus 2025
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", indonesia)
            outputFormat.format(date)
        }
    }
}