package com.mariejuana.randomquotes.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.mariejuana.randomquotes.R
import com.mariejuana.randomquotes.databinding.FragmentFaveBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FaveFragment : Fragment() {
    private lateinit var binding: FragmentFaveBinding

    // Clears the SharedPreferences
    private fun clearFavoriteQuote() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.remove("favoriteQuote")
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFaveBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Accesses the SharedPreferences for the favorite quotes
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val favoriteQuote = sharedPreferences.getString("favoriteQuote", "")
        val timestamp = sharedPreferences.getLong("favoriteQuoteTime", 0)

        if (favoriteQuote.isNullOrEmpty()) {
            with(binding) {
                textQuote.text = "There's no current favorite quote."
                buttonFaveQuote.isEnabled = false
            }
        } else {
            // Convert the timepstamp into a date-time format
            val formattedTimestamp = SimpleDateFormat("MM-dd-yyyy | hh:mm:ss", Locale.getDefault()).format(
                Date(timestamp)
            )

            with(binding) {
                textQuote.text = "Favorite Quote:\n$favoriteQuote\n\nTimestamp: \n$formattedTimestamp"
                buttonFaveQuote.isEnabled = true
            }
        }

        with(binding) {
            buttonBackToHome.setOnClickListener{
                val fragment = MainFragment()
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            }

            buttonFaveQuote.setOnClickListener{
                clearFavoriteQuote()
                Toast.makeText(requireContext(), "The quote has been deleted.", Toast.LENGTH_SHORT).show()            // Update the TextView to show there's no current favorite quote
                textQuote.text = "There's no current favorite quote."
            }
        }
    }
}