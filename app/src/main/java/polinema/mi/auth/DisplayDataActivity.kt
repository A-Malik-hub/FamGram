package polinema.mi.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_display_data.*

class DisplayDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_data)

        var paket: Bundle? = intent.extras
        var username=(paket?.getString("username"))
        var email=(paket?.getString("email"))
        var password=(paket?.getString("password"))
        var alamat=(paket?.getString("alamat"))
        var no_telp=(paket?.getString("no_telp"))
        var no_ktp=(paket?.getString("no_ktp"))

        if( username!= null){
            Username.text = username
        }
        if( email!= null){
            Email.text = email
        }
        if( password!= null){
            Password.text = password
        }
        if( alamat!= null){
            Alamat.text = alamat
        }
        if( no_telp!= null){
            No_telp.text = no_telp
        }
        if( no_ktp!= null){
            No_ktp.text = no_ktp
        }
    }
}