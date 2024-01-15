package com.nextin.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var lastNumber :Boolean =false
   private var lastDot :Boolean = false


    private var tvInput : TextView? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvScreen)
    }
    fun onDigit(view : View)
    {
        tvInput?.append((view as Button).text)
        lastNumber = true
        //Toast.makeText(this,"Button Clicked", Toast.LENGTH_LONG).show()
    }

    fun onClear(view :View)
    {
        tvInput?.text = ""
        lastNumber = false
        lastDot = false
    }

    fun onOperator(view :View)
    {
        tvInput?.text.let {
            if (lastNumber && !isOperatorAdded(it.toString()))
            {
                tvInput?.append((view as Button).text)
                lastNumber = false
                lastDot =false
            }
        }

    }
    fun onDecimal(view :View)
    {
        if(lastNumber && !lastDot)
        {
            tvInput?.append(".")
            lastNumber = false
            lastDot =true
        }
    }

    fun onEquals(view: View)
    {
        if(lastNumber)
        {
            var tvValues = tvInput?.text.toString()
            var prifix = ""
            try {
                if (tvValues.startsWith("-"))
                {
                    prifix="-"
                    tvValues = tvValues.substring(1)
                }
                when{
                    tvValues.contains("/")->
                    {
                        val splitValue = tvValues.split("/")

                        var one = splitValue[0]
                        val two = splitValue[1]

                            if(prifix.isEmpty())
                            {
                                one = prifix +one
                            }
                        tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }

                    tvValues.contains("*")->
                    {
                        val splitValue = tvValues.split("*")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if(prifix.isEmpty())
                        {
                            one = prifix + one
                        }
                        tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }
                    tvValues.contains("+")->
                    {
                        val splitValue = tvValues.split("+")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if(prifix.isEmpty())
                        {
                            one = prifix + one
                        }

                        tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }

                    tvValues.contains("-")->
                    {
                        val splitValue = tvValues.split("-")

                        var one = splitValue[0]
                        val two = splitValue[1]

                        if(prifix.isEmpty())
                        {
                            one = prifix + one
                        }

                        tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                }
            }
            catch (e: ArithmeticException)
            {
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value :String):Boolean{

        return if(value.startsWith("-"))
        {
            false
        }else
        {
            value.contains("+")
                    ||value.contains("/")
                    ||value.contains("*")
                    ||value.contains("-")
        }
    }

    private fun removeZeroAfterDot(result : String) :String{
        var value = result

        if(value.contains(".0"))
        {
            value = result.substring(0,result.length-2)
        }
        return value
    }

}