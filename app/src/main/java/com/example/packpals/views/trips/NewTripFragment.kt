package com.example.packpals.views.trips

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.packpals.R
import com.example.packpals.viewmodels.TripsPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTripFragment : Fragment(){
    private val viewModel: TripsPageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_trip, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayout = requireView().findViewById<LinearLayout>(R.id.newTripPalsLinearLayout)
        viewModel.palsList.observe(viewLifecycleOwner) { palsList ->
            linearLayout.removeAllViews()
            for (pal in palsList) {
                val addPalView = LayoutInflater.from(context).inflate(R.layout.view_new_trip_add_pal, linearLayout, false)
                addPalView.findViewById<TextView>(R.id.newTripPalName).text = pal.name
                addPalView.setOnClickListener {
                    if (pal.id != null) {
                        println(pal.id)
                        viewModel.addRemoveTripPal(pal.id!!)
                    }
                }
                linearLayout.addView(addPalView)
            }

        }

        viewModel.currentTripPalIds.observe(viewLifecycleOwner){
            updateTripPals()
        }

        val createTripButton = requireView().findViewById<Button>(R.id.createTripButton)
        createTripButton.setOnClickListener {
            val tripName = requireView().findViewById<TextView>(R.id.tripNameInput).text.toString()
            if (tripName.isNotEmpty()) {
                viewModel.createTrip(tripName)
            }



            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.tripFragmentContainerView, TripsListFragment())
            transaction.commit()
        }
    }

    private fun updateTripPals(){
        val linearLayout = requireView().findViewById<LinearLayout>(R.id.newTripPalsLinearLayout)
        for (i in 0 until linearLayout.childCount){
            val addPalView = linearLayout.getChildAt(i)
            val checkmarkImageView = addPalView.findViewById<ImageView>(R.id.newTripCheckmarkIcon)
            val palId = viewModel.palsList.value?.get(i)?.id

            if(viewModel.currentTripPalIds.value?.contains(palId)==true){
                checkmarkImageView.setImageResource(R.drawable.ic_checked)
            }
            else{
                checkmarkImageView.setImageResource(R.drawable.ic_unchecked)
            }
        }

    }
}