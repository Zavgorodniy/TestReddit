package com.butterfly.testreddit.ui.base

import android.content.Context
import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.butterfly.testreddit.R
import com.butterfly.testreddit.extensions.appString
import com.butterfly.testreddit.extensions.bindInterfaceOrThrow
import com.butterfly.testreddit.extensions.hideKeyboard

abstract class BaseFragment<T : BaseViewModel> : Fragment(),
    BaseView,
    BackPressable {

    /**
     * Set the Java-class ViewModel.
     */
    abstract val viewModelClass: Class<T>

    private val textWatchers: Map<EditText?, TextWatcher> = mutableMapOf()

    protected open val viewModel: T by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this).get(viewModelClass)
    }

    /**
     * Set id of layout.
     */
    protected abstract val layoutId: Int

    private var toolbar: Toolbar? = null

    var baseView: BaseView? = null
        private set

    var backPressedCallback: BackPressedCallback? = null
        private set

    protected open val progressObserver = Observer<Boolean> {
        if (it == true) showProgress() else hideProgress()
    }

    protected open val errorObserver = Observer<Any> { error ->
        error?.let { processError(it) }
    }

    /**
     * In the method need to subscribe to the LiveData from the [viewModel].
     */
    abstract fun observeLiveData()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseView = bindInterfaceOrThrow<BaseView>(parentFragment, context)
        backPressedCallback = bindInterfaceOrThrow<BackPressedCallback>(parentFragment, context)
    }

    override fun onDetach() {
        baseView = null
        backPressedCallback = null
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeAllLiveData()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutId, container, false)
        hideKeyboard(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        initToolbar()
        view?.let { hideKeyboard(it) }
    }

    /**
     * Extension for adding [TextWatcher] to [EditText] which will be cleared when fragment is destroyed.
     */
    fun EditText.addTextWatcher(watcher: TextWatcher) = this.apply {
        textWatchers.plus(this to watcher)
        addTextChangedListener(watcher)
    }

    /**
     * Pop the top state off the back stack.
     */
    fun backPressed() {
        backPressedCallback?.backPressed()
    }

    override fun onDestroyView() {
        textWatchers.forEach { (key, value) -> key?.removeTextChangedListener(value) }
        super.onDestroyView()
    }

    /**
     * Called when need to show progress view.
     */
    override fun showProgress() {
        baseView?.showProgress()
    }

    /**
     * Called when need to hide progress view.
     */
    override fun hideProgress() {
        baseView?.hideProgress()
    }

    /**
     * Error handler
     *
     * @param error object [Any] which describe error
     */
    override fun onError(error: Any) {
        baseView?.onError(error)
    }

    protected open fun processError(error: Any) = onError(error)

    /**
     * Set id of screen title
     *
     * @return Id of screen title
     */
    @StringRes
    protected abstract fun getScreenTitle(): Int

    /**
     * Set if fragment has toolbar
     *
     * @return True if fragment has toolbar
     * False if fragment without toolbar
     */
    protected abstract fun hasToolbar(): Boolean

    /**
     * Set id of toolbar
     *
     * @return Toolbar id
     */
    @IdRes
    protected open fun getToolbarId(): Int = R.id.tToolbar

    /**
     * Initialize toolbar
     */
    protected open fun initToolbar() {
        view?.apply {
            if (hasToolbar() && getToolbarId() != NO_TOOLBAR) {
                toolbar = findViewById(getToolbarId())
                with(activity as AppCompatActivity) {
                    setSupportActionBar(toolbar)
                    supportActionBar?.let {
                        setupActionBar(it)
                        backNavigationIcon()?.let { toolbar?.setNavigationIcon(it) }
                        if (needToShowBackNav()) {
                            toolbar?.setNavigationOnClickListener { handleNavigation() }
                        }
                    }
                }
            }
        }
    }

    protected open fun handleNavigation() {
        backPressedCallback?.backPressed()
    }

    protected open fun blockBackAction(): Boolean = false

    protected open fun showBlockBackAlert() {
        processError(appString(R.string.cant_return_back))
    }

    override fun onBackPressed(): Boolean = blockBackAction().apply {
        if (this) showBlockBackAlert()
    }

    protected open fun backNavigationIcon(): Int? = null

    /**
     * Setup action bar
     *
     * @param actionBar Modified action bar
     */
    protected open fun setupActionBar(actionBar: ActionBar) {
        actionBar.apply {
            title = getStringScreenTitle()
            setDisplayHomeAsUpEnabled(needToShowBackNav())
        }
    }

    /**
     * Set if need to show back navigation in toolbar
     *
     * @return True if toolbar has back navigation
     * False if toolbar without back navigation
     */
    protected open fun needToShowBackNav() = true

    /**
     * Set [String] screen title
     *
     * @return Screen title
     */
    protected open fun getStringScreenTitle() =
        if (getScreenTitle() != NO_TITLE) getString(getScreenTitle()) else ""

    private fun observeAllLiveData() {
        observeLiveData()
        with(viewModel) {
            isLoadingLD.observe(this@BaseFragment, progressObserver)
            errorLD.observe(this@BaseFragment, errorObserver)
        }
    }

    protected inline fun <reified T> FragmentManager.invokeInterfaceIfExist(block: (item: T) -> Unit) {
        fragments.forEach { if (it is T) block(it) }
    }
}