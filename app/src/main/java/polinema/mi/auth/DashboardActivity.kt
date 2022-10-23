package polinema.mi.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var auth : FirebaseAuth
    var currentUser : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        btnProfil.setOnClickListener(this)
        btnInputData.setOnClickListener(this)
        btnLogOut.setOnClickListener(this)

        auth = Firebase.auth
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnProfil -> {
                var paket: Bundle? = intent.extras
                var username=(paket?.getString("username"))
                var email=(paket?.getString("email"))
                var password=(paket?.getString("password"))
                var alamat=(paket?.getString("alamat"))
                var no_telp=(paket?.getString("no_telp"))
                var no_ktp=(paket?.getString("no_ktp"))
                Intent(this, DisplayDataActivity::class.java).also {
                    it.putExtra("email", email)
                    it.putExtra("password", password)
                    it.putExtra("username", username)
                    it.putExtra("alamat", alamat)
                    it.putExtra("no_telp", no_telp)
                    it.putExtra("no_ktp", no_ktp)
                    startActivity(it)
                }
            }
            R.id.btnLogOut -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.btnInputData -> {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }
}