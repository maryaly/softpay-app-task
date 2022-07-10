package io.softpay.softpos.ui.amount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.softpay.softpos.R
import io.softpay.softpos.databinding.FragmentAmountBinding
import io.softpay.softpos.ui.MainViewModel
import io.softpay.softpos.ui.base.BaseFragment
import io.softpay.softpos.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AmountFragment : BaseFragment() {

    private val mAmountViewModel: AmountViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private var _binding: FragmentAmountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAmountBinding.inflate(inflater, container, false)
        binding.amountViewModel = mAmountViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun setupView() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.transaction.collectLatest {
                    Timber.e("${it}")
                        navigateToProgressFragment()
                }
            }
        }
    }

    override fun setupUiListener() {
        /* NO-OP */
    }

    override fun setupObservers() {

        mAmountViewModel.mWhichButtonClicked.observe(viewLifecycleOwner, Observer {
            when (it) {
                Constants.BUTTON_CANCEL_CLICKED -> {
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                        mAmountViewModel.cancelTransaction()
                    }
                    findNavController().navigateUp()
                }
                Constants.BUTTON_ENTER_CLICKED -> {

                    mAmountViewModel.getTimeAndDateOfTransaction()

                    if (mAmountViewModel.mAmount.value != mAmountViewModel.mResourceUtilHelper.getResourceString(
                            R.string._0_00
                        ) && mAmountViewModel.mAmount.value != "0"
                    ) {
                        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                            mAmountViewModel.mAmount.value?.let { amount ->
                                mAmountViewModel.dispatchAmount(amount)
                            }
                        }
                    }
                }
            }
        })
    }

    private fun navigateToProgressFragment() {
        val action =
            AmountFragmentDirections.actionAmountFragmentToProgressFragment(
                time = mAmountViewModel.mTime.value.toString(),
                date = mAmountViewModel.mDate.value.toString(),
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}