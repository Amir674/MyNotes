package ru.atmaratovich.mynotes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.atmaratovich.mynotes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding // Тут подключаем binding
    private lateinit var navController: NavController // Тут добовляем NavController который грубо говоря будет отвечает за нашу навигацию, тоесть с помощью него мы будем навигироваться

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NavHostFragment уже в разметке, получаем оттуда NavController
        val host =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment // Далее мы берем хост нашего фрагмента. Мы вызываем
        navController = host.navController // Далее в наш NavController мы присваеваем host.NavController

    }
}





/* 22.09.25
* Я начал приложение Мои заметки с того что сделал 2 фаргмента один из них будет содержать в себе заметки а в другом мы будем писать эти заметки
* Затем Добовляем навигатион компонент в наше приложение
* После этого нам нужно сделать интерфейс
* */







