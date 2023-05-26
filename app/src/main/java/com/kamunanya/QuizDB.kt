package com.kamunanya

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class QuizDB private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private var readDb: SQLiteDatabase? = null
    private var writeDb: SQLiteDatabase? = null

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, fromVersion: Int, toVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun getReadableDatabase(): SQLiteDatabase {
        if (readDb == null) readDb = super.getReadableDatabase()
        return readDb!!
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        if (writeDb == null) writeDb = super.getWritableDatabase()
        return writeDb!!
    }

    fun getAll(): List<QuizData> {
        val projection = arrayOf(COLUMN_ID, COLUMN_DATA)
        val cursor = readableDatabase.query(TABLE_NAME, projection, null, null, null, null, null)
        val result = mutableListOf<QuizData>()
        while (cursor.moveToNext()) {
            result.add(QuizData.fromJson(cursor.getString(1)))
        }
        return result
    }

    fun get(id: Int): QuizData {
        val cursor = readableDatabase.query(
            TABLE_NAME, arrayOf(COLUMN_DATA),
            "$COLUMN_ID=?", arrayOf(id.toString()),
            null, null, null)
        val result = mutableListOf<QuizData>()
        if(cursor.moveToNext()) {
            result.add(QuizData.fromJson(cursor.getString(1)))
        }
        throw Error("Quiz with id $id not found")
    }

    fun create(data: QuizData) {
        val values = ContentValues()
        values.put(COLUMN_DATA, data.asJson())
        data.id = writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun delete(quiz: QuizData): Int {
        val selection = "$COLUMN_ID = ?"
        return writableDatabase.delete(TABLE_NAME, selection, arrayOf(quiz.id.toString()))
    }

    fun update(data: QuizData): Int {
        val values = ContentValues()
        values.put(COLUMN_DATA, data.asJson())
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(data.id.toString())
        return writableDatabase.update(TABLE_NAME, values, selection, selectionArgs)
    }

    companion object {
        private const val DATABASE_NAME = "quiz.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "quiz"
        const val COLUMN_ID = "id"
        const val COLUMN_DATA = "data"

        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_DATA BLOB)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

        private var db: QuizDB? = null
        fun getInstance(ctx: Context): QuizDB {
            if (db == null) {
                db = QuizDB(ctx)
            }
            return db as QuizDB
        }
    }
}