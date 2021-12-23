package project.bazaar.viewmodels


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import project.bazaar.repository.Repository

/*
T is the ViewModel we created.
 */

class ListViewModelFactory(private val context: Context, private val repository: Repository) : Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(context, repository) as T
    }
}