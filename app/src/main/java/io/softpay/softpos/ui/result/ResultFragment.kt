package io.softpay.softpos.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.softpay.softpos.R
import io.softpay.softpos.databinding.FragmentResultBinding
import io.softpay.softpos.ui.MainViewModel
import io.softpay.softpos.ui.base.BaseFragment
import timber.log.Timber

@AndroidEntryPoint
class ResultFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
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
        mainViewModel.mTransaction.value?.let {
            Timber.e("viwModel: $it")
            mResultViewModel.showInfo(it)
        }
        setAnimation()
    }

    override fun setupUiListener() {
        /* NO-OP */
    }

    override fun setupObservers() {
        /* NO-OP */
    }

    private fun setAnimation() {

        val animationScaleUp = AnimationUtils.loadAnimation(context, R.anim.anim_scale_up)
        binding.textViewResultFragmentStatus.startAnimation(animationScaleUp)
        binding.textViewResultFragmentDetails.startAnimation(animationScaleUp)
        binding.textViewResultFragmentReferenceId.startAnimation(animationScaleUp)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}