package ru.atmaratovich.mynotes

import android.accessibilityservice.GestureDescription
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

// Здесь мы создаем Room для нашей баззы данных

/**
 * @author Amir
 * @since 24.09.2025
 */

// 1. Сущность: Notes
// Для того чтобы нам создать объект нашей базы данных надо определить этот объект
@Entity(tableName = "note") // @Entity - это анотация. Анотация - это «метка, которая указывает, что делать с участком кода помимо исполнения программы».
// Сама по себе аннотация не выполняет никаких действий, но предоставляет информацию, которую можно использовать во время компиляции или во время выполнения программы.
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Тут мы делаем id для нашего объекта Surah чтобы в дальнейшем работать с ним
    val totalAyahs: Int, // Количество аятов
    val text: String = "", // Содержимое суры


)

// 2. DAO: NotesDao
// Далее для того чтобы нам упровлять наштим объектом суры нам нужна конструкция которой мы будем все управлять
// С помощью DAO мы сможеи добовлять, обновлять, удалять, получать CRUD(create, read, update, delete)
@Dao // Здесь мы делаем анотацию
interface NoteDao {
    @Query("SELECT * FROM note")  // Здесь мы говорим что возьмем все элементы которые есть в таблице surah
    fun getAllSurahs(): LiveData<List<Note>> // Мы с помощью LiveData сможем считывать  значение из нашей базы данных

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Insert - Это озночает что мы вставляем какоето значение. Если мы захотим добавить в базу данных такой же элемент то мы будем его подменять
    fun insertSurah(surah: Note)

    @Update
    fun updateSurah(surah: Note) // Здесь мы не пересоздоем весь обьект а обновляем существующий объект notes

    @Delete
    fun deleteSurah(surah: Note) // Этот метод удаляет наш элемент notes из нашей таблицы
}

// 3. База данных: AppDatabase
@Database(entities = [Note::class], version = 1)  // То что мы указываем в скобках аннотации это мы просто явно указываем параметры  аннотации чтоб нам самим было удобнее
// Дальше чтобы наш DAO и entities нам нужно место которое будет все это собирать. И эту работу будет выполнять база данных
abstract class AppDatabase : RoomDatabase() { // abstract class - абстрактный класс озночает что мы не можем создать обьект напрямую. Мы не можем создать объект нашего абстрактного класса
    abstract  fun noteDao(): NoteDao

    // Room - просит нас описать нашу базу данныз именно таким образом тоесть с Абастрактными классами и методами
}

// 4. Класс для создания нашей бд. DatabaseProvider - Поставщик базы данных
class  DatabaseProvider(context: Context) { // Чтобы красиво офрмить все что было в 1,2,3 пунктах нам нужен класс который будет оберткай

    private val database: AppDatabase = Room.databaseBuilder( // В этой строчке мы создоем объект нашей database используя Room и вызывая у него метод databaseBuilder
        context.applicationContext, AppDatabase::class.java, "my_app_database.db").fallbackToDestructiveMigration().build()
    // context.applicationContext - озночает что наша база данныз будет жить пока живо приложение, тоесть если удалить прилоржение то база данных очистится
    // "my_app_database.db" - указываем название базы данных
    // .fallbackToDestructiveMigration() - этот метод помогает нам с миграцией. Тоесть когда версия базы данных меняется чтоб это подстраивалось под пользователя
    // .build() - Создаем базу данных

    private val noteDao: NoteDao = database.noteDao() // Здесь мы делаем приватное поле notesDao это наше Dao интерфейс NotesDao. мы описываем нашу переменную notesDao с типом :NotesDao для того
    // чтобы мы могли иметь доступ к нашим методам внутри NotesDao. И дальше мы у нашего объекта database вызываем метод .notesDao()

    fun noteDao(): NoteDao = noteDao // И дальше мы делаем метод notesDao() который возврощает нам : NotesDao и передаем в него нашу переменную
}
// Дальше нам нужно создать наш DatabaseProvider который на 66 строчке внутри App Классе(Переход в App.kt к 5 пункту)

