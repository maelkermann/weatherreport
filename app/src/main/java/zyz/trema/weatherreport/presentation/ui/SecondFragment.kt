package zyz.trema.weatherreport.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import zyz.trema.weatherreport.R
import zyz.trema.weatherreport.base.BaseFragment
import zyz.trema.weatherreport.databinding.FragmentSecondBinding

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>(
    FragmentSecondBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

    }

}