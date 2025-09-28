package ru.atmaratovich.mynotes

import android.app.Application

class App: Application() {
    // lateinit var - Озночает что иниициальзация будет происходить позже

    private lateinit var dbProvider: DatabaseProvider //5.Здесь мы создоем приватный dbProvider с типом DatabaseProvider  тоесть наш класс DatabaseProvider. Тоесть мы объявляем переменную которая будет объектом нашего класса DatabaseProvider

    fun dbProvider(): DatabaseProvider = dbProvider //7. Далее мы через метод dbProvider() получаем объект нашего DatabaseProvider приравнивая метод к объекту dbProvider
    // Далее этот метод мы используем в ThirdFragment

    override fun onCreate() {
        super.onCreate()
        dbProvider = DatabaseProvider(this) //6. Далее мы в onCreate создаем или инициализируем наш объект класса DatabaseProvider.
        // Но в нашем классе DatabaseProvider мы указали что у него должен быть Context и поэто пишем DatabaseProvider(this). this - говорит что контекстом будет App
        // Этим мы говорим что наша база данных и dbProvider будут жить пока живо приложение
    }
}