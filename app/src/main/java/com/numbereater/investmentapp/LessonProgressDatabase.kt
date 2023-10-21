package com.numbereater.investmentapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.lang.RuntimeException
import java.nio.file.ClosedFileSystemException

class LessonProgressDatabase(context: Context) {
    companion object {
        private const val CREATE_TABLE_IF_NEXISTS = "CREATE TABLE IF NOT EXISTS CompletedLessons(LessonID INTEGER UNIQUE)"
        private const val CLEAR_DATABASE = "DELETE FROM CompletedLessons"

        private const val DATABASE_CLOSED_EXCEPTION_MESSAGE = "Database has been closed"
    }

    private lateinit var localDatabase: SQLiteDatabase
    private var isOpen = false

    init {
        localDatabase = context.openOrCreateDatabase(
            "lesson_progress.db",
            Context.MODE_PRIVATE,
            null
        )
        isOpen = true

        localDatabase.execSQL(CREATE_TABLE_IF_NEXISTS)
    }

    fun isComplete(lessonId: Int): Boolean {
        checkIsOpen()

        val cur = localDatabase.rawQuery("SELECT * FROM CompletedLessons WHERE LessonID=$lessonId", null)
        return cur.moveToFirst()
    }

    fun setLessonComplete(lessonId: Int) {
        checkIsOpen()
        localDatabase.execSQL("INSERT INTO CompletedLessons(LessonID) VALUES($lessonId)")
    }

    fun clearDatabase() {
        checkIsOpen()
        localDatabase.execSQL(CLEAR_DATABASE)
    }

    fun close() {
        checkIsOpen()
        localDatabase.close()
        isOpen = false
    }

    private fun checkIsOpen() {
        if (!isOpen) {
            throw RuntimeException(DATABASE_CLOSED_EXCEPTION_MESSAGE)
        }
    }
}