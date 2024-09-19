package com.itmo.smarthome.smarthomemobile.app.ui.automation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.itmo.smarthome.smarthomemobile.app.databinding.FragmentAutomationBinding

class AutomationFragment : Fragment() {

    private var _binding: FragmentAutomationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var automationAdapter: AutomationAdapter
    private lateinit var automationViewModel: AutomationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAutomationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        automationViewModel = ViewModelProvider(requireActivity()).get(AutomationViewModel::class.java)

        // Initialize RecyclerView
        val recyclerView = binding.recyclerViewAutomations
        recyclerView.layoutManager = LinearLayoutManager(context)


        automationAdapter = AutomationAdapter(emptyList())
        recyclerView.adapter = automationAdapter

        automationViewModel.automations.observe(viewLifecycleOwner) { automations ->
            automationAdapter.updateAutomations(automations)
        }

        binding.btFragmentAutomationAdd.setOnClickListener {
            //findNavController().navigate(R.id.action_navigation_devices_to_navigation_add_new_device)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}