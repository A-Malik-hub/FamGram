package polinema.mi.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var bundle : Bundle? =null
    var topik = "auth"
    var type = 0
    lateinit var userdata:HashMap<String,String>

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnSignIn ->{
                val email :String = edUsername.text.toString().trim()
                val password :String = edPassword.text.toString().trim()
                if(email.isEmpty()){
                    edUsername.error = "Email harus diisi"
                    edUsername.requestFocus()
                    return
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edUsername.error = "Email tidak valid"
                    edUsername.requestFocus()
                    return
                }
                if(password.isEmpty() || password.length < 8){
                    edPassword.error = "Password tidak boleh kurang dari 8 karakter"
                    edPassword.requestFocus()
                    return
                }
                loginUser(email, password)
            }

            R.id.txSignUp ->{
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser!!
                    if (currentUser!=null){
                        if (currentUser!!.isEmailVerified){
                            val email = auth.currentUser!!.email.toString()
                            val df = db.collection("users").document(email).get()
                            df.addOnSuccessListener(OnSuccessListener {
                                val df = db.collection("users").document(email).get()
                                Log.d(TAG, "addOnSuccessListener: "+ df)
                                val password = it.data?.get("password").toString()
                                val username = it.data?.get("username").toString()
                                val alamat = it.data?.get("alamat").toString()
                                val no_telp = it.data?.get("no_telp").toString()
                                val no_ktp = it.data?.get("password").toString()

                                Intent(this, DashboardActivity::class.java).also {
                                    it.putExtra("email", email)
                                    it.putExtra("password", password)
                                    it.putExtra("username", username)
                                    it.putExtra("alamat", alamat)
                                    it.putExtra("no_telp", no_telp)
                                    it.putExtra("no_ktp", no_ktp)
                                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(it)
                                    return@OnSuccessListener
                                }})
                        }
                        else{
                            Toast.makeText(
                                this, "Email anda belum terverifikasi",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@addOnCompleteListener
                        }
                    }
                }else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Email/Password salah",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@addOnCompleteListener
                }
            }
    }

    companion object {
        var TAG = MainActivity::class.java.simpleName
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore
        Firebase.messaging.subscribeToTopic(topik).addOnCompleteListener {
            var msg = "Subscribe to $topik"
            if (!it.isSuccessful) msg = "Can't subscribe to topic"
            Toast.makeText(this, "$msg", Toast.LENGTH_LONG).show()
        }

        btnSignIn.setOnClickListener(this)
        txSignUp.setOnClickListener(this)
    }
}