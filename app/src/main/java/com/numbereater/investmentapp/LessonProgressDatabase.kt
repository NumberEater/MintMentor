package com.numbereater.investmentapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class LessonProgressDatabase(context: Context) {
    companion object {
        const val CREATE_TABLE_IF_NEXISTS = "CREATE TABLE IF NOT EXISTS CompletedLessons(LessonID INTEGER UNIQUE)"
    }

    private lateinit var localDatabase: SQLiteDatabase

    init {
        localDatabase = context.openOrCreateDatabase(
            "lesson_progress.db",
            Context.MODE_PRIVATE,
            null
        )

        localDatabase.execSQL(CREATE_TABLE_IF_NEXISTS)
    }

    fun isComplete(lessonId: Int): Boolean {
        val cur = localDatabase.rawQuery("SELECT * FROM CompletedLessons WHERE LessonID=$lessonId", null)
        return cur.moveToFirst()
    }

    fun setLessonComplete(lessonId: Int) {
        localDatabase.execSQL("INSERT INTO CompletedLessons(LessonID) VALUES($lessonId)")
    }

    fun close() {
        localDatabase.close()
    }
}