package com.kseyko.veriimkb.ui.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      16,November,2021      ║
╚════════════════════════════╝
 */
abstract class BaseViewModelFragment<VB : ViewDataBinding, VM : BaseViewModel> :
    BaseFragment<VB>() {
    abstract val viewModel: VM

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}