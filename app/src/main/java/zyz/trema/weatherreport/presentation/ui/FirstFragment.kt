package zyz.trema.weatherreport.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import zyz.trema.weatherreport.R
import zyz.trema.weatherreport.base.BaseFragment
import zyz.trema.weatherreport.base.None
import zyz.trema.weatherreport.base.collectWhileStarted
import zyz.trema.weatherreport.databinding.FragmentFirstBinding
import zyz.trema.weatherreport.presentation.viewmodels.Fragment1ViewModel

@AndroidEntryPoint
class FirstFragment : BaseFragment<FragmentFirstBinding>(
    FragmentFirstBinding::inflate
) {

    val viewModel : Fragment1ViewModel by viewModels()

    init {
        lifecycleScope.launchWhenCreated {
            viewModel.uiState.collectWhileStarted(this@FirstFragment) { renderState(it) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

    }

    private fun renderState(state: Fragment1ViewModel.UIState) {
        if ( state.isLoading ) {
            handleLoading()
        } else if ( state.errorEvent != null ) {
            handleErrorEvent(state.errorEvent)
        } else if ( state.data != null ) {
            handleData(state.data)
        }
    }

    private fun handleData(data: None) {


    }

    private fun handleErrorEvent(throwable: Throwable) {


    }

    private fun handleLoading() {


    }

}