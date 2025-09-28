package ru.atmaratovich.mynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.atmaratovich.mynotes.databinding.FragmentListOfNotesBinding
import ru.atmaratovich.mynotes.databinding.ItemPostBinding
// №36 Room Database - нужна для сохронения большого количества данных
// №41 NavigationComponent(навигация) в Android Studio — компонент архитектуры навигации из Android Jetpack. Он позволяет упростить реализацию навигации(перемещения) между экранами назначения (destinations) в приложении.
// №44 Паттерн проектирования MVVM( «Model-View-ViewModel») - другой подход нежеле в MVP, Представление(View) отвечает только за то что видет пользователь, ViewModel как инженер который собирает готовит данные а затем подписывает на себя View


class ListOfNotes : Fragment() {

    private var _binding: FragmentListOfNotesBinding? = null
    private val binding get() = _binding!!


    private val myNotesList: MutableList<Note> = mutableListOf() // Room 10. Тут мы делаем мутабл лист нашего объекта Notes и создаем пустой список mutableListOf

    private lateinit var adapter: NoteAdapter // Room 9.  Adapter(переходник) - нужен для того чтобы передавать данные из одного места в другой(Например из списка данных он переносит данные в какуюто сетку или список в приложении)
    // Представьте, что вы находитесь в ресторане, а кухня — это ваш источник данных, который готовит разные блюда. Адаптер в этой аналогии — это официант, который берет каждое блюдо из кухни и
    // красиво подает его на стол. Вы не видите сырые ингредиенты или беспорядок на кухне, вы видите только красиво оформленное блюдо. Так же и в Android: адаптер превращает сырые данные в
    // удобный для пользователя формат.

    private val  dbProvider by lazy { (requireActivity().application as App).dbProvider() }// Room 8. Здесь делаем переменную dbProvider
    // by lazy - Это делигат  который говорит комелятору создать переменную dbProvider только тогда когла мы в первый раз обротимся к нашей переменной
    // Дальше все что в фишурных скобках (requireActivity().application as App).dbProvider() нужно чтобы вернуть наш dbProvider



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfNotesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Тут просто NavigationComponent чтобы переходит к другой странице Читаем аргумент reply
        val args = ListOfNotesArgs.fromBundle(requireArguments())
        binding.messageText.text = args.message

        binding.floatingActionButtonAddNotes.setOnClickListener {
            // Возврощаемся, передавая новый message
            val action = ListOfNotesDirections.actionFragmentListOfNotesToCreateNotes(reply = "Привет, CreateNotes")
            findNavController().navigate(action)

            // Я остоновился на том что пытался настроить навигацию!!!
        }

//        val totalAyahs = 1
//        val newNote = Note(text = message, totalAyahs = totalAyahs  )
//        dbProvider.noteDao().insertSurah(newNote)



        // Room 11. Настройка RecyclerView и адаптера
        adapter = NoteAdapter()
        binding.recyclerViewNotes.adapter = adapter
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())

        // Room 12. Наблюдение за данными
//        dbProvider.noteDao().getAllSurahs().observe(viewLifecycleOwner) { list -> // Далее мы вызываем у нашего dbProvider метод surahDao() далее у него вызываем метод getAllSurahs()(который возврощает нам LiveData и список наших Сур
//
//            adapter.submitList(list) // Далее мы у нашего адаптера вызываем метод submitList() и передаем list и грубо говоря обновляем наш list
//            myNotesList.addAll(list) // И далее обновляем mySurahsList вызываем метод addAll() тоесть добовляем все лементы которые лежат ListSurah и
//            // Тоесть что озноачеат пункт 12 - это значит что когда запуститься onViewCreated мы вызовим наш getAllSurahs и подписываемся на нашу LiveData и когда к нам придут значения из нашей базы данных
//            // Мы просто покажем их в RecyclerView и этот же список мы добавим в mySurahsList
//
//            // Я ОСТОНОВИЛСЯ ВОТ ТУТ
//        }

//        //13. Пример вставки новой Surah (в корутины)
//        binding.floatingActionButtonAddNotes.setOnClickListener { // Здесь мы говорим что при нажатии на кнопку fabAdd
//            lifecycleScope.launch(Dispatchers.IO)  { // Здесь мы запускаем нашу карутину и именно Dispatchers.IO потому что мы будем работать с нашей базой данных не на main потоке
//                // И что мы делаем внутри нашей корутины:
//
//                val  text = message
//                val totalAyahs = 1
//
//                val newNote = Note(text = text, totalAyahs = totalAyahs  ) // Далее мы создаем объект нашей Surah который в Room.kt. Здесь мы у нашего data class Surah вызываем поля name, totalAyahs и кладем туда name, totalAyahs
////                dbProvider.noteDao().insertSurah(newSurah) // Далее мы с помощью dbProvider вызываем метод .surahDao() и .insertSurah(newSurah), тоесть добовляем суру newSurah
//
//
//            }
//        }


    }
}
//Room 9.2
class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val  binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class NoteViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.tvTitle.text = note.text // Я ОСТОНОВИЛСЯ ВОТ ТУТ

        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem:Note): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean =
            oldItem == newItem
    }
}


