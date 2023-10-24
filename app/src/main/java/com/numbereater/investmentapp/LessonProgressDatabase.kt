package com.numbereater.investmentapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.lang.RuntimeException
import kotlin.math.ceil

class LessonProgressDatabase(context: Context) {
    companion object {
        private const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CompletedLessons(LessonID INTEGER UNIQUE)"
        private const val CLEAR_DATABASE = "DELETE FROM CompletedLessons"
        private const val GET_NUMBER_ENTRIES = "SELECT COUNT(LessonID) FROM CompletedLessons"

        private const val DATABASE_CLOSED_EXCEPTION_MESSAGE = "Database has been closed"
    }

    private var localDatabase: SQLiteDatabase
    private var isOpen = false

    init {
        localDatabase = context.openOrCreateDatabase(
            "lesson_progress.db",
            Context.MODE_PRIVATE,
            null
        )
        isOpen = true

        localDatabase.execSQL(CREATE_TABLE)
    }

    fun isComplete(lessonId: Int): Boolean {
        assertIsOpen()

        val cur = localDatabase.rawQuery("SELECT * FROM CompletedLessons WHERE LessonID=$lessonId", null)
        val isComplete = cur.moveToFirst()
        cur.close()
        return isComplete
    }

    fun getLessonsCompletedCount(): Int {
        assertIsOpen()
        val cur = localDatabase.rawQuery(GET_NUMBER_ENTRIES, null)

        if (!cur.moveToFirst()) {
            throw RuntimeException("Cursor move to first failed.")
        }

        val lessonsCompleted = cur.getInt(0)

        cur.close()

        return lessonsCompleted
    }

    fun setLessonComplete(lessonId: Int) {
        assertIsOpen()
        if (!isComplete(lessonId))
            localDatabase.execSQL("INSERT INTO CompletedLessons(LessonID) VALUES($lessonId)")
    }

    // NOT USED IN PRODUCTION //
    fun clearDatabase() {
        assertIsOpen()
        localDatabase.execSQL(CLEAR_DATABASE)
    }

    fun close() {
        assertIsOpen()
        localDatabase.close()
        isOpen = false
    }

    private fun assertIsOpen() {
        if (!isOpen) {
            throw RuntimeException(DATABASE_CLOSED_EXCEPTION_MESSAGE)
        }
    }
}