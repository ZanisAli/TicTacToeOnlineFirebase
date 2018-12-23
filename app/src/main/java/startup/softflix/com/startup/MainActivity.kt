package startup.softflix.com.startup

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
//import firebase object
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    //to work with firebase database, need to define firebase dabatase instance
    private  var database= FirebaseDatabase.getInstance()
    //2nd thing is to define reference for the database
    var myRef= database.getReference()//this one has instance to that database and using it can connect to that instance

    //declare firebase analytics var
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    //my email
     var myEmail:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //b is instance of bundle
        var b:Bundle=intent.extras
        myEmail=b.getString("email")

    }


    fun buClick(view: View)
    {
        val buSelected= view as Button //here above view was button so have to cast(convert) view as button that is why did this
        var cellID=0
        when(buSelected.id)//this will get id of button selected
        {
            R.id.bu1->cellID=1  //when button 1 will be selected then cell id will be 1
            R.id.bu2->cellID=2
            R.id.bu3->cellID=3
            R.id.bu4->cellID=4
            R.id.bu5->cellID=5
            R.id.bu6->cellID=6
            R.id.bu7->cellID=7
            R.id.bu8->cellID=8
            R.id.bu9->cellID=9

        }

       // Toast.makeText(this,"ID is "+cellID, Toast.LENGTH_LONG).show()
        playGame(cellID, buSelected)
    }

    var player1= ArrayList<Int>()//for what player 1 selected
    var player2= ArrayList<Int>()
    var ActivePlayer=1 //player 1 is first to play


    fun playGame(cellID:Int, buSelected:Button)
    {
        if(ActivePlayer==1)
        {
            buSelected.text="X" //change text of button to X for 1st player
            buSelected.setBackgroundResource(R.color.blue)//change background color to green
            player1.add(cellID) //add cell id to 1st player list
            //compulsory to change to second player from current player
            ActivePlayer=2
            autoPlay()
        }
        else{
            buSelected.text="0" //change text of button to X for 1st player
            buSelected.setBackgroundResource(R.color.darkgreen)//change background color to green, these colors are added in colors .xml in values folder
            player2.add(cellID) //add cell id to 1st player list
            //compulsory to change to second player from current player
            ActivePlayer=1
        }
        buSelected.isEnabled=false //so that selected button from above is disabled so no one can enable it.
        CheckWinner()//after each playing checking the winner
    }

    fun CheckWinner()
    {
        var winner=-1//at start there is no winner, complete row or complete colum will be the winner
        //for row 1
        if(player1.contains(1) && player1.contains(2) && player1.contains(3))
        {
            winner=1 //player 1 is the winner
        }
        if(player2.contains(1) && player2.contains(2) && player2.contains(3))
        {
            winner=2 //player 1 is the winner
        }

        //for row 2
        if(player1.contains(4) && player1.contains(5) && player1.contains(6))
        {
            winner=1 //player 1 is the winner
        }
        if(player2.contains(4) && player2.contains(5) && player2.contains(6))
        {
            winner=2 //player 1 is the winner
        }

        //for row 3
        if(player1.contains(7) && player1.contains(8) && player1.contains(9))
        {
            winner=1 //player 1 is the winner
        }
        if(player2.contains(7) && player2.contains(8) && player2.contains(9))
        {
            winner=2 //player 1 is the winner
        }

        //now for colums
        //for colum1
        if(player1.contains(1) && player1.contains(4) && player1.contains(7))
        {
            winner=1 //player 1 is the winner
        }
        if(player2.contains(1) && player2.contains(4) && player2.contains(7))
        {
            winner=2 //player 1 is the winner
        }

        //for colum 2
        if(player1.contains(2) && player1.contains(5) && player1.contains(8))
        {
            winner=1 //player 1 is the winner
        }
        if(player2.contains(2) && player2.contains(5) && player2.contains(8))
        {
            winner=2 //player 1 is the winner
        }

        //for colum 3
        if(player1.contains(3) && player1.contains(6) && player1.contains(9))
        {
            winner=1 //player 1 is the winner
        }
        if(player2.contains(3) && player2.contains(6) && player2.contains(9))
        {
            winner=2 //player 1 is the winner
        }

        //if we have winner now
        if(winner!=1)
        {
            if(winner==1)
            {
                Toast.makeText(this,"Player 1 is the winner", Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(this,"Player 2 is the winner", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun autoPlay()
    {
        var emptyCells= ArrayList<Int>()

        for(cellID in 1..9)
        {
            if(!(player1.contains(cellID)|| player2.contains(cellID)))
            {
                emptyCells.add(cellID)
            }
        }

        val r=Random()
        val randIndex= r.nextInt(emptyCells.size-0)+0

        val cellID=emptyCells[randIndex]
        var buSelect:Button? //? shows empty
        when(cellID){
            1->buSelect=bu1
            2->buSelect=bu2
            3->buSelect=bu3
            4->buSelect=bu4
            5->buSelect=bu5
            6->buSelect=bu6
            7->buSelect=bu7
            8->buSelect=bu8
            9->buSelect=bu9
            else->
            {
                buSelect=bu1
            }

        }
        playGame(cellID,buSelect)
    }

    protected fun buRequestEvent(view:android.view.View)
    {
        var userDemail=etEmail.text.toString()
        //push will create random id, because if use email then alot of people can use that for requesting
        myRef.child("Users").child(userDemail).child("Request").push().setValue(myEmail)
    }

    protected fun buAcceptEvent(view:android.view.View)
    {
        var userDemail=etEmail.text.toString()

    }
}
