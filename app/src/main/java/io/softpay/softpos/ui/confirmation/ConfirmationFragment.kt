package io.softpay.softpos.ui.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.softpay.softpos.R
import io.softpay.softpos.databinding.FragmentConfirmationBinding
import io.softpay.softpos.ui.MainViewModel
import io.softpay.softpos.ui.base.BaseFragment
import io.softpay.softpos.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ConfirmationFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val mConfirmationViewModel: ConfirmationViewModel by viewModels()
    private var _binding: FragmentConfirmationBinding? = null
    private val binding get() = _binding!!
    private val mArgs: ConfirmationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmationBinding.inflate(inflater, container, false)
        binding.confirmationViewModel = mConfirmationViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun setupView() {
        mainViewModel.mTransaction.value?.let {
            Timber.e("viwModel: $it")
            mConfirmationViewModel.showInfo(
                mArgs.time,
                mArgs.date,
                it
            )
        }
        setAnimation()
    }

    override fun setupUiListener() {
        /* NO-OP */
    }

    override fun setupObservers() {
        mConfirmationViewModel.mIsConfirmButtonClicked.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    Constants.BUTTON_CANCEL_CLICKED -> {
                        mainViewModel.cancelTransaction()
                        findNavController().navigateUp()
                    }
                    Constants.BUTTON_CONFIRM_CLICKED -> {
                        mConfirmationViewModel.confirmAmount(true)
                        navigateToProgressFragment()
                    }
                }
            })
    }

    private fun setAnimation() {

        val animationScaleUp = AnimationUtils.loadAnimation(context, R.anim.anim_scale_up)
        binding.imageViewConfirmationFragmentStatus.startAnimation(animationScaleUp)
        binding.textViewConfirmationFragmentStatus.startAnimation(animationScaleUp)

        val animationSlideUp = AnimationUtils.loadAnimation(context, R.anim.anim_slide_up)
        binding.cardViewConfirmationFragmentBottom.startAnimation(animationSlideUp)
    }

    private fun navigateToProgressFragment() {
        val action =
            ConfirmationFragmentDirections.actionConfirmationFragmentToProgressFragment(
                time = mArgs.time,
                date = mArgs.date,
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}