package io.softpay.softpos.ui.progress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.softpay.sdk.State
import io.softpay.softpos.databinding.FragmentProgressBinding
import io.softpay.softpos.ui.MainViewModel
import io.softpay.softpos.ui.base.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ProgressFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val mArgs: ProgressFragmentArgs by navArgs()

    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun setupView() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.transaction.collectLatest { transaction ->
                    Timber.e("$transaction")
                    when (transaction.state) {
                        State.AWAITING_AMOUNT -> navigateToAmountFragment()
                        State.AWAITING_CONFIRMATION -> navigateToConfirmationFragment()
                        State.SUCCESS, State.FAILURE -> navigateToResultFragment()
                        State.CANCELLED -> activity?.finish()
                    }
                }
            }
        }
    }

    override fun setupObservers() {
        /* NO-OP */
    }

    private fun navigateToAmountFragment() {
        val action =
            ProgressFragmentDirections.actionProgressFragmentToAmountFragment()
        findNavController().navigate(action)
    }

    private fun navigateToConfirmationFragment() {
        val action =
            ProgressFragmentDirections.actionProgressFragmentToConfirmationFragment(
                time = mArgs.time!!,
                date = mArgs.date!!,
            )
        findNavController().navigate(action)
    }

    private fun navigateToResultFragment() {
        val action =
            ProgressFragmentDirections.actionProgressFragmentToResultFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}