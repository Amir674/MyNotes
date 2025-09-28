package ru.atmaratovich.mynotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.atmaratovich.mynotes.databinding.CreateNotesFragmentBinding

class CreateNotes : Fragment() {

    private var _binding: CreateNotesFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreateNotesFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Читаем аргумент reply
        val args = CreateNotesArgs.fromBundle(requireArguments())
        binding.messageText.text = args.reply

        val a = binding.tvMessageNotes.text.toString()

        binding.buttonToSave.setOnClickListener {
            // Возврощаемся, передавая новый message
            val action = CreateNotesDirections.actionCreateNotesToFragmentListOfNotes(message = a)
            findNavController().navigate(action)
        }
    }

}