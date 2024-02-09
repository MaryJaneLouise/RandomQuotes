package com.mariejuana.randomquotes.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mariejuana.randomquotes.R
import com.mariejuana.randomquotes.databinding.FragmentMainBinding
import com.mariejuana.randomquotes.helpers.GetQuote
import java.util.Calendar

class MainFragment : Fragment() {
    private lateinit var quoteOfTheDay: String
    private lateinit var binding: FragmentMainBinding

    // Generates quote for today and will stay up until the next day
    fun getQuoteOfTheDay(seed : Long): String {
        val getQuotes = GetQuote()
        val quotes = getQuotes.getAllQuote()

        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val random = java.util.Random(dayOfYear + seed)

        val randomIndex = random.nextInt(quotes.size)
        return quotes[randomIndex]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Access the date today then converts it to long format
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val seed = dayOfYear.toLong()

        // Enters the generated long date format to the function
        quoteOfTheDay = getQuoteOfTheDay(seed)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            textQuoteOfTheDay.text = quoteOfTheDay

            buttonLove.setOnClickListener{
                val fragment = SharedFragment(1)
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
            }
            buttonInspiration.setOnClickListener{
                val fragment = SharedFragment(2)
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
            }
            buttonFunny.setOnClickListener{
                val fragment = SharedFragment(3)
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
            }
            buttonFave.setOnClickListener{
                val fragment = FaveFragment()
                parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit()
            }
        }
    }
}