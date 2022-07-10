package io.softpay.softpos.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import io.softpay.sdk.Transaction
import io.softpay.softpos.R
import io.softpay.softpos.databinding.FragmentProgressBinding
import io.softpay.softpos.databinding.FragmentResultBinding
import io.softpay.softpos.ui.base.BaseFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultFragment : BaseFragment() {

    private val mResultViewModel: ResultViewModel by viewModels()
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        binding.resultViewModel = mResultViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun setupView() {
//        mArgs.transaction?.let { mResultViewModel.showInfo(it) }
        setAnimation()
    }

    override fun setupUiListener() {

    }

    override fun setupObservers() {
        mResultViewModel.mIsConfirmButtonClicked.observe(
            viewLifecycleOwner,
            Observer {
                if (it == true) {
                    navigateToSplashFragment()
                }
            })
    }

    private fun setAnimation() {

        val animationScaleUp = AnimationUtils.loadAnimation(context, R.anim.anim_scale_up)
        binding.textViewResultFragmentStatus.startAnimation(animationScaleUp)
        binding.textViewResultFragmentDetails.startAnimation(animationScaleUp)
        binding.textViewResultFragmentReferenceId.startAnimation(animationScaleUp)

    }

    private fun navigateToSplashFragment() {
        lifecycleScope.launch(Dispatchers.Default) {
            mResultViewModel.cancelTransaction()
        }

        findNavController().navigate(R.id.action_resultFragment_to_splashFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}