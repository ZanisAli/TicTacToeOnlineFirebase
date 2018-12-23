package startup.softflix.com.startup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    //declare firebase authentication var

    private var mAuth: FirebaseAuth?=null
//below two lines are connecting firebase real time database which is defined in this project
    //to work with firebase database, need to define firebase dabatase instance
    private  var database=FirebaseDatabase.getInstance()
    //2nd thing is to define reference for the database
    var myRef= database.getReference()//this one has instance to that database and using it can connect to that instance


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
                        var currentUser=mAuth!!.currentUser
                        //save in database
                        if(currentUser!=null) {
                            myRef.child("Users").child(SplitString(currentUser.email.toString())).child("Request").setValue(currentUser.uid) //this child "users" which we created in database manually and second .child is adding child to "users" child and setting value to userid

                        }
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
            intent.putExtra("Uid", currentUser!!.uid) //this uid is  also in authentication tab next to user and each user have unique id
            intent.putExtra("Email", currentUser!!.email)
            //starting activity otherwise it will not show
            startActivity(intent)
        }

    }

    //user id was child of "Users" but we wanted email as child but email have @, firebase don't allow for that we will split email and use only part before @
    fun SplitString(str:String):String
    {
        var split=str.split("@")
        return split[0] //0 location is first part of email without @ sign
    }
}
