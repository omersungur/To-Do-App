package com.omersungur.todoapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.omersungur.todoapp.R
import com.omersungur.todoapp.databinding.FragmentAddNoteBinding
import com.omersungur.todoapp.ui.viewmodel.AddNoteViewModel
import com.omersungur.todoapp.util.Constants.colorsNoteTv
import com.omersungur.todoapp.util.Constants.noteTextSizeOption
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var viewModel: AddNoteViewModel
    private var cardBackgroundColor: Int = 0xFFFFCDD2.toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tempViewModel: AddNoteViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etNoteContent.textSize = 16F

        binding.noteCardView.setBackgroundColor(cardBackgroundColor)
        changeCardViewColor()

        setArrayAdapterNoteTextColor()
        setArrayAdapterNoteTextSize()


        binding.spinnerNoteTextSize.setSelection(2) // default olarak 16sp
        spinnerNoteTextSizeItem()

        binding.spinnerNoteTextColor.setSelection(0)
        spinnerNoteTextColorItem()

        fabButtonOnClick()
    }

    private fun setArrayAdapterNoteTextColor() {
        val arrayAdapterNoteTextColor =
            ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                colorsNoteTv
            )

        binding.spinnerNoteTextColor.adapter = arrayAdapterNoteTextColor
    }

    private fun setArrayAdapterNoteTextSize() {
        val arrayAdapterNoteTextSize =
            ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                noteTextSizeOption
            )

        binding.spinnerNoteTextSize.adapter = arrayAdapterNoteTextSize
    }

    @SuppressLint("SimpleDateFormat")
    private fun fabButtonOnClick() {
        binding.fabSave.setOnClickListener {
            val noteTitle = binding.etNoteTitle.text.toString()
            val noteContent = binding.etNoteContent.text.toString()
            val noteTextColor = binding.etNoteContent.currentTextColor

            val scale = resources.displayMetrics.density
            val noteTextSize = binding.etNoteContent.textSize / scale

            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd-MM-yyyy | HH:mm ")
            val noteCreatedTime = formatter.format(time)

            if (!isEntryValid()) {
                Toast.makeText(requireContext(), "Alanları Boş Bırakmayın!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                insertNote(
                    noteTitle,
                    noteContent,
                    cardBackgroundColor,
                    noteTextSize,
                    noteTextColor,
                    noteCreatedTime
                )

                Navigation.findNavController(it)
                    .navigate(R.id.action_addNoteFragment_to_noteListFragment)
            }
        }
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

    private fun insertNote(
        noteTitle: String,
        noteContent: String,
        cardBackgroundColor: Int,
        noteTextSize: Float,
        noteTextColor: Int,
        noteCreatedTime: String
    ) {
        viewModel.insertNote(
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