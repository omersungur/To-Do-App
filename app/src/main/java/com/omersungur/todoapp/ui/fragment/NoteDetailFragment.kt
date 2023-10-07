package com.omersungur.todoapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.omersungur.todoapp.R
import com.omersungur.todoapp.data.entity.Note
import com.omersungur.todoapp.databinding.FragmentNoteDetailBinding
import com.omersungur.todoapp.ui.viewmodel.NoteDetailViewModel
import com.omersungur.todoapp.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private lateinit var viewModel: NoteDetailViewModel
    private var cardBackgroundColor: Int = 0xFFFFCDD2.toInt()

    private val bundle: NoteDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: NoteDetailViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId = bundle.noteId

        setArrayAdapterNoteTextColor()
        setArrayAdapterNoteTextSize()

        spinnerNoteTextSizeItem()
        spinnerNoteTextColorItem()

        selectedItemForTextColorSpinner()
        selectedItemForTextSizeSpinner()

        var currentBackgroundColor: Int

        lifecycleScope.launch {
            viewModel.getNoteById(noteId).collect { note ->
                binding.etNoteTitle.setText(note.noteTitle)
                binding.etNoteContent.setText(note.noteContent)
                binding.noteCardView.setBackgroundColor(note.cardBackgroundColor)
                binding.etNoteContent.setTextColor(note.noteTextColor)
                binding.etNoteContent.textSize = note.noteTextSize

                currentBackgroundColor =
                    note.cardBackgroundColor // kaydedilen background'u başka bir değişkene aktardım.
                cardBackgroundColor = currentBackgroundColor
                // Eğer güncelleme yapılırken başka bir card rengi seçilmezse eski rengin korunması için burada atama yaptım.
            }
        }

        changeCardViewColor()
        buttonEditOnClick()
    }

    private fun setArrayAdapterNoteTextColor() {
        val arrayAdapterNoteTextColor =
            ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                Constants.colorsNoteTv
            )

        binding.spinnerNoteTextColor.adapter = arrayAdapterNoteTextColor
    }

    private fun setArrayAdapterNoteTextSize() {
        val arrayAdapterNoteTextSize =
            ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                Constants.noteTextSizeOption
            )

        binding.spinnerNoteTextSize.adapter = arrayAdapterNoteTextSize
    }

    private fun selectedItemForTextColorSpinner() {
//        println(binding.spinnerNoteTextColor.selectedItem)
//        println(binding.spinnerNoteTextColor.selectedItemId)
//        println(binding.spinnerNoteTextColor.selectedItemPosition)

        val spinnerPosition = when (bundle.noteTextColor) {
            "-16777216" -> 0 // BLACK
            "-1" -> 1 // WHITE
            "-16776961" -> 2 // BLUE
            "-65536" -> 3 // RED
            "-8388480" -> 4 // PURPLE
            "-16711681" -> 5 // CYAN
            "-23296" -> 6 // ORANGE
            "-256" -> 7 // YELLOW
            else -> 0
        }

        binding.spinnerNoteTextColor.setSelection(spinnerPosition)
    }

    private fun selectedItemForTextSizeSpinner() {

        val spinnerPosition = when (bundle.noteTextSize) {
            12.0F -> 0
            14.0F -> 1
            16.0F -> 2
            18.0F -> 3
            20.0F -> 4
            22.0F -> 5
            24.0F -> 6
            else -> 2
        }

        binding.spinnerNoteTextSize.setSelection(spinnerPosition)
    }

    private fun buttonEditOnClick() {
        binding.buttonEdit.setOnClickListener {

            val note = getEntry()

            if (!isEntryValid()) {
                Toast.makeText(requireContext(), "Alanları Boş Bırakmayın!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                updateNote(
                    note.noteId,
                    note.noteTitle,
                    note.noteContent,
                    cardBackgroundColor,
                    note.noteTextSize,
                    note.noteTextColor,
                    note.noteCreatedTime
                )

                Navigation.findNavController(it)
                    .navigate(R.id.action_noteDetailFragment_to_noteListFragment)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getEntry(): Note {
        val noteId = bundle.noteId

        val noteTitle = binding.etNoteTitle.text.toString()
        val noteContent = binding.etNoteContent.text.toString()
        val noteTextColor = binding.etNoteContent.currentTextColor

        val scale = resources.displayMetrics.density
        val noteTextSize = binding.etNoteContent.textSize / scale

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yyyy | HH:mm ")
        val noteCreatedTime = formatter.format(time)

        return Note(
            noteId,
            noteTitle,
            noteContent,
            cardBackgroundColor,
            noteTextSize,
            noteTextColor,
            noteCreatedTime
        )
    }

    @SuppressLint("DiscouragedApi")
    private fun changeCardViewColor() {
        val colorsCardView = arrayOf(
            binding.cardViewRed,
            binding.cardViewPink,
            binding.cardViewPurple,
            binding.cardViewDeepPurple,
            binding.cardViewIndigo,
            binding.cardViewBlue,
            binding.cardViewLigthBlue,
            binding.cardViewCyan,
            binding.cardViewTeal,
            binding.cardViewGreen,
            binding.cardViewLigtGreen,
            binding.cardViewLime,
            binding.cardViewYellow,
            binding.cardViewAmber,
            binding.cardViewOrange,
            binding.cardViewDeepOrange,
            binding.cardViewBrown,
            binding.cardViewGrey,
            binding.cardViewBlueGrey
        )

        val resources = resources
        val noteCardView = binding.noteCardView

        for (i in colorsCardView.indices) {
            val colorResId = resources.getIdentifier(
                "colorNoteText${getColorName(i)}",
                "color",
                requireContext().packageName
            )

            colorsCardView[i].setOnClickListener {
                val theme = requireContext().theme
                val colorRes = resources.getColor(colorResId, theme)
                noteCardView.setBackgroundColor(colorRes)
                cardBackgroundColor = colorRes
            }
        }
    }

    private fun getColorName(index: Int): String {
        return when (index) {
            0 -> "Red"
            1 -> "Pink"
            2 -> "Purple"
            3 -> "DeepPurple"
            4 -> "Indigo"
            5 -> "Blue"
            6 -> "LightBlue"
            7 -> "Cyan"
            8 -> "Teal"
            9 -> "Green"
            10 -> "LightGreen"
            11 -> "Lime"
            12 -> "Yellow"
            13 -> "Amber"
            14 -> "Orange"
            15 -> "DeepOrange"
            16 -> "Brown"
            17 -> "Grey"
            18 -> "BlueGrey"
            else -> "Red"
        }
    }

    private fun spinnerNoteTextSizeItem() {
        binding.spinnerNoteTextSize.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedTextSizeStr = parent?.getItemAtPosition(position).toString()

                    val selectedTextSize = selectedTextSizeStr.replace("sp", "").toFloat()

                    binding.etNoteContent.textSize = selectedTextSize
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun spinnerNoteTextColorItem() {
        binding.spinnerNoteTextColor.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (parent?.getItemAtPosition(position).toString()) {
                        "Black" -> {
                            binding.etNoteContent.setTextColor(0XFF000000.toInt())
                            binding.etNoteTitle.setTextColor(0XFF000000.toInt())
                        }

                        "White" -> {
                            binding.etNoteContent.setTextColor(0XFFFFFFFF.toInt())
                            binding.etNoteTitle.setTextColor(0XFFFFFFFF.toInt())
                        }

                        "Red" -> {
                            binding.etNoteContent.setTextColor(0XFFFF0000.toInt())
                            binding.etNoteTitle.setTextColor(0XFFFF0000.toInt())
                        }

                        "Purple" -> {
                            binding.etNoteContent.setTextColor(0XFF800080.toInt())
                            binding.etNoteTitle.setTextColor(0XFF800080.toInt())
                        }

                        "Cyan" -> {
                            binding.etNoteContent.setTextColor(0XFF00FFFF.toInt())
                            binding.etNoteTitle.setTextColor(0XFF00FFFF.toInt())
                        }

                        "Orange" -> {
                            binding.etNoteContent.setTextColor(0XFFFFA500.toInt())
                            binding.etNoteTitle.setTextColor(0XFFFFA500.toInt())
                        }

                        "Yellow" -> {
                            binding.etNoteContent.setTextColor(0XFFFFFF00.toInt())
                            binding.etNoteTitle.setTextColor(0XFFFFFF00.toInt())
                        }

                        "Blue" -> {
                            binding.etNoteContent.setTextColor(0XFF0000FF.toInt())
                            binding.etNoteTitle.setTextColor(0XFF0000ff.toInt())
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
    }

    private fun updateNote(
        noteId: Int,
        noteTitle: String,
        noteContent: String,
        cardBackgroundColor: Int,
        noteTextSize: Float,
        noteTextColor: Int,
        noteCreatedTime: String
    ) {
        viewModel.updateNote(
            noteId,
            noteTitle,
            noteContent,
            cardBackgroundColor,
            noteTextSize,
            noteTextColor,
            noteCreatedTime
        )
    }

    private fun isEntryValid(): Boolean {
        val noteTitle = binding.etNoteTitle.text.toString()
        val noteContent = binding.etNoteContent.text.toString()
        return viewModel.isEntryValid(noteTitle, noteContent)
    }
}