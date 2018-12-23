package startup.softflix.com.startup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    //declare firebase authentication var

    private var mAuth: FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //initializing firebase auth var
        mAuth= FirebaseAuth.getInstance()
    }


    fun buLoginEvent(view: View){

        LoginToFireBase(etEmail.text.toString(), etPassword.text.toString())

    }

    fun LoginToFireBase(email:String, password: String)
    {
        //creating user with email and passwor, so no signup in this app
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task->

                    if(task.isSuccessful)
                    {
                        Toast.makeText(applicationContext,"Login Successfull", Toast.LENGTH_SHORT).show()
                        //after successfull login, i call next method for passing user data b/w activities
                        LoadMain()
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"Login Failed", Toast.LENGTH_SHORT).show()
                    }

                }
    }

    //if user already log in then have not to show login activity but main activity

     override fun onStart() {
        super.onStart()
         LoadMain()
    }

    //need to pass user information to the second activity, currentuser instance can have anything , username, email, pass as defined in above function
    fun LoadMain(){
        var currentUser=mAuth!!.currentUser
        if(currentUser!=null) {
            //passing data b/w activites need intent
            var intent = Intent(this, MainActivity::class.java)// will go to main activity
            intent.putExtra("Email", currentUser!!.uid) //this uid is  also in authentication tab next to user and each user have unique id
            intent.putExtra("Email", currentUser!!.email)
            //starting activity otherwise it will not show
            startActivity(intent)
        }

    }
}
