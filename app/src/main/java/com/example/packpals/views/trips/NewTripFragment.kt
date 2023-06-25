package com.example.packpals.views.trips

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.packpals.R
import com.example.packpals.viewmodels.TripsPageViewModel
import androidx.navigation.fragment.findNavController
import com.example.packpals.views.login.RegisterFragment
import kotlinx.android.synthetic.main.activity_trips_page.tripFragmentContainerView
import java.util.Date

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

        val linearLayout = requireView().findViewById<LinearLayout>(R.id.tripPalsLinearLayout)
        viewModel.currentTripPalIds.observe(viewLifecycleOwner) { currentTripPalIds ->
            for (pal in viewModel.palsList.value!!) {
                val addPalView = LayoutInflater.from(context).inflate(R.layout.view_add_pal, linearLayout, false)

                addPalView.findViewById<TextView>(R.id.palName).text = pal.name
                addPalView.setOnClickListener {
                    if (pal.id != null) {
                        viewModel.addRemoveTripPal(pal.id)
                    }
                }
                linearLayout.addView(addPalView)
            }
        }
        val createTripButton = requireView().findViewById<Button>(R.id.createTripButton)
        createTripButton.setOnClickListener {
            val tripName = requireView().findViewById<TextView>(R.id.tripNameLabel).text.toString()
            if (tripName.isNotEmpty()) {
                viewModel.createTrip(tripName)
            }

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.tripFragmentContainerView, TripsListFragment())
            transaction.commit()
        }
    }
}