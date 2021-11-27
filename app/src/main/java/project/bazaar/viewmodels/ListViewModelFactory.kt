package project.bazaar.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import project.bazaar.repository.Repository

class ListViewModelFactory(private val repository: Repository) : Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListViewModel(repository) as T
    }
}