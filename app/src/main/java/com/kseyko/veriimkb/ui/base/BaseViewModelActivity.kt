package com.kseyko.veriimkb.ui.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding


/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      16,November,2021      ║
╚════════════════════════════╝
 */
abstract class BaseViewModelActivity<VB : ViewBinding, VM : BaseViewModel> : BaseActivity<VB>() {

    abstract val viewModel: VM

    override fun onPreInit(savedInstanceState: Bundle?) {
        super.onPreInit(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}