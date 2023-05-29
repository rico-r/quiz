package com.kamunanya

import android.net.Uri
import android.util.Base64
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.util.zip.Deflater
import java.util.zip.DeflaterOutputStream
import java.util.zip.Inflater
import java.util.zip.InflaterOutputStream

data class QuizData(var id: Long=0, var title: String, var desc: String, var question: MutableList<QuestionData>) {

    fun shuffle(): ShuffledQuizData {
        val result = mutableListOf<ShuffledQuestionData>()
        for(q in question.shuffled()) {
            result.add(q.shuffle())
        }
        return ShuffledQuizData(title, desc, result)
    }

    fun toUri(): Uri {
        val data = ByteArrayOutputStream()
        val deflater = DeflaterOutputStream(data, Deflater(Deflater.BEST_COMPRESSION, true))
        deflater.write(asJson().toByteArray())
        deflater.finish()
        val base64 = Base64.encodeToString(data.toByteArray(), BASE64_FLAGS)
        deflater.close()
        return Uri.Builder()
            .scheme("https")
            .authority("kamu.nanya")
            .path(base64)
            .build()
    }

    fun asJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        const val BASE64_FLAGS = Base64.URL_SAFE or Base64.NO_CLOSE or Base64.NO_PADDING or Base64.NO_WRAP

        fun fromJson(json: String): QuizData {
            return Gson().fromJson(json, QuizData::class.java)
        }

        fun fromUri(uri: Uri): QuizData {
            val data = ByteArrayOutputStream()
            val inflater = InflaterOutputStream(data, Inflater(true))
            inflater.write(Base64.decode(uri.lastPathSegment, BASE64_FLAGS))
            inflater.finish()
            val json = data.toString()
            inflater.close()
            return fromJson(json)
        }
    }
}

data class ShuffledQuizData(
    val title: String,
    val desc: String,
    val question: List<ShuffledQuestionData>
)