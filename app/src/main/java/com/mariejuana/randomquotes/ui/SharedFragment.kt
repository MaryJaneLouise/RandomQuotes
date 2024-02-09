package com.mariejuana.randomquotes.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.mariejuana.randomquotes.R
import com.mariejuana.randomquotes.databinding.FragmentSharedBinding
import com.mariejuana.randomquotes.helpers.GetQuote

class SharedFragment(var typeQuote : Int) : Fragment() {
    private lateinit var binding: FragmentSharedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    // Saves the quote to the SharedPreferences
    private fun saveFavoriteQuote(quote: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val timestamp = System.currentTimeMillis()
        val quoteKey = "favoriteQuote"
        val timestampClock = "favoriteQuoteTime"

        editor.putString(quoteKey, quote)
        editor.putLong(timestampClock, timestamp)

        editor.apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSharedBinding.inflate(layoutInflater, container, false)

        val allQuotes = GetQuote()

        when (typeQuote) {
            1 -> {
                val allQuoteLove = allQuotes.getQuoteLove()
                val randomIndex = (allQuoteLove.indices).random()
                val randomQuote = allQuoteLove[randomIndex]

                with(binding) {
                    textTypeQuote.text = "Love Quotes"
                    textQuote.text = randomQuote

                    buttonRoll.setOnClickListener{
                        val randomIndex = (allQuoteLove.indices).random()
                        val randomQuote = allQuoteLove[randomIndex]

                        binding.textQuote.text = randomQuote
                    }

                    buttonFaveQuote.setOnClickListener{
                        saveFavoriteQuote(randomQuote)
                        Toast.makeText(requireContext(), "The quote has been saved.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            2 -> {
                val allQuoteInspiration = allQuotes.getQuoteInspiration()
                val randomIndex = (allQuoteInspiration.indices).random()
                val randomQuote = allQuoteInspiration[randomIndex]

                with(binding) {
                    textTypeQuote.text = "Inspirational Quotes"
                    textQuote.text = randomQuote

                    buttonRoll.setOnClickListener{
                        val randomIndex = (allQuoteInspiration.indices).random()
                        val randomQuote = allQuoteInspiration[randomIndex]

                        textQuote.text = randomQuote
                    }

                    buttonFaveQuote.setOnClickListener{
                        saveFavoriteQuote(randomQuote)
                        Toast.makeText(requireContext(), "The quote has been saved.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            3 -> {
                val allQuoteFunny = allQuotes.getQuoteFunny()
                val randomIndex = (allQuoteFunny.indices).random()
                val randomQuote = allQuoteFunny[randomIndex]

                with(binding) {
                    textTypeQuote.text = "Funny Quotes"
                    textQuote.text = randomQuote

                    buttonRoll.setOnClickListener{
                        val randomIndex = (allQuoteFunny.indices).random()
                        val randomQuote = allQuoteFunny[randomIndex]

                        textQuote.text = randomQuote
                    }

                    buttonFaveQuote.setOnClickListener{
                        saveFavoriteQuote(randomQuote)
                        Toast.makeText(requireContext(), "The quote has been saved.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else -> {
                // Nothing to do here.. literally
            }
        }

        binding.buttonBackToHome.setOnClickListener{
            val fragment = MainFragment()
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        }

        return binding.root
    }
}