package project.bazaar.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import project.bazaar.repository.Repository

class OrderDetailViewModelFactory(private val context: Context, private val repository: Repository) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderDetailViewModel(context, repository) as T
    }
}