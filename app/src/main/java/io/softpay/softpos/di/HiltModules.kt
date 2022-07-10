package io.softpay.softpos.di


import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.softpay.sdk.TransactionManager
import io.softpay.sdk.impl.TransactionManagerImpl
import io.softpay.softpos.utils.number.NumberHelper
import io.softpay.softpos.utils.number.NumberHelperImpl
import io.softpay.softpos.utils.resource.ResourceUtilHelper
import io.softpay.softpos.utils.resource.ResourceUtilHelperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModules {

    @Provides
    @Singleton
    fun provideTransactionManager(@ApplicationContext mContext: Context): TransactionManager = TransactionManagerImpl()

    @Provides
    @Singleton
    fun provideResourceUtilHelperImpl(@ApplicationContext mContext: Context): ResourceUtilHelper =
        ResourceUtilHelperImpl(mContext)
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModules {
    @Binds
    internal abstract fun bindNumberHelper(
        numberHelperImpl: NumberHelperImpl
    ): NumberHelper
}