package polinema.mi.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.android.synthetic.main.activity_notif.*
import java.lang.Exception

class NotifActivity : AppCompatActivity() {

    var bundle : Bundle? = null
    var topik = "auth"
    var type = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif)
        Firebase.messaging.subscribeToTopic(topik).addOnCompleteListener {
            var msg = "Subscribe to $topik"
            if (!it.isSuccessful) msg = "Can't subscribe to topic"
            Toast.makeText(this, "$msg", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(!it.isSuccessful) return@addOnCompleteListener
            edToken.setText(it.result!!.toString())
        }

        try {
            bundle = getIntent().getExtras()!!
        }catch (e : Exception){
            Log.e("BUNDLE","bundle is null")
        }

        if(bundle !=null){
            type = bundle!!.getInt("type")
            when(type){
                0 -> {
                    edPromoId.setText(bundle!!.getString("promoId"))
                    edPromo.setText(bundle!!.getString("promo"))
                    edPromoUntil.setText(bundle!!.getString("promoUntil"))
                }
                1 -> {
                    edTitle.setText(bundle!!.getString("title"))
                    edBody.setText(bundle!!.getString("body"))
                }
            }
        }
    }
}