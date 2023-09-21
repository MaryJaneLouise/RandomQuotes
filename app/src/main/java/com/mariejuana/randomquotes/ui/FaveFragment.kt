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
import com.mariejuana.randomquotes.R

class FaveFragment : Fragment() {

    private fun saveFavoriteQuote(quote: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("favoriteQuote", quote)
        editor.apply()
    }

    private fun clearFavoriteQuote() {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.remove("favoriteQuote")
        editor.apply()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fave, container, false)
        val buttonGoBack : Button = view.findViewById(R.id.buttonBackToHome)
        val buttonFaveQuote : Button = view.findViewById(R.id.buttonFaveQuote)

        val textViewQuote : TextView = view.findViewById(R.id.text_quote)

        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val favoriteQuote = sharedPreferences.getString("favoriteQuote", "")

        if (favoriteQuote.isNullOrEmpty()) {
            textViewQuote.text = "There's no current favorite quote."
            buttonFaveQuote.isEnabled = false
        } else {
            textViewQuote.text = favoriteQuote
            buttonFaveQuote.isEnabled = true
        }



        buttonGoBack.setOnClickListener{
            val fragment = MainFragment()
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment)?.commit()
        }
        buttonFaveQuote.setOnClickListener{
            clearFavoriteQuote()
            Toast.makeText(requireContext(), "The quote has been deleted.", Toast.LENGTH_SHORT).show()            // Update the TextView to show there's no current favorite quote
            textViewQuote.text = "There's no current favorite quote."
        }
        return view
    }
}