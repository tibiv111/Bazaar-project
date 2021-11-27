package project.bazaar


import android.app.Application
import project.bazaar.utils.SessionManager

class Bazaar: Application(){
    companion object{
        var token: String =""
    }
}