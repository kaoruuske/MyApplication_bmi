package com.example.myapplication_bmi

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.lang.NumberFormatException


class MainActivity : AppCompatActivity() {

//    val pref = this.getPreferences(0)
//    val editor = pref.edit()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //이전 입력 값 불러오기
        LoadData()

        result_Butoon.setOnClickListener {

            if (TextUtils.isEmpty(nameEdit_Text.text) && TextUtils.isEmpty(weightEdit_Text.text) && TextUtils.isEmpty(heightEdit_Text.text))
            {
                toast("값을 입력해 주세요.")
                return@setOnClickListener
            }

            //  결과 출력 전 결과값 저장
            SaveData( nameEdit_Text.text.toString() ,heightEdit_Text.text.toString(), weightEdit_Text.text.toString())

            try {
                //입력 값 다음 화면으로 전달
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("weight", weightEdit_Text.text.toString())
                intent.putExtra("height", heightEdit_Text.text.toString())
                startActivity(intent)

//            startActivity<ResultActivity>(
//                "weight" to weightEdit_Text.text.toString(),
//                "height" to heightEdit_Text.text.toString()
//            )
            }catch ( e: NumberFormatException ){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }

        }//setOnClickListener

    }// OnCreate end

    private fun SaveData( name : String , height : String , weight : String )
    {
        val pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
        val editor  = pref.edit()

//        editor.putInt("KEY_HEIGHT",height)
//        editor.putInt("KEY_WEIGHT",weight)
//        editor.apply()

        App.prefs.KEY_HEIGHT = height
        App.prefs.KEY_WEIGHT = weight
        App.prefs.KEY_NAME = name

    }


    private  fun LoadData()
    {
//        val pref = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
//        val height = pref.getInt("KEY_HEiGHT", 0)
//        val weight = pref.getInt("KEY_WEiGHT", 0)

//        if (height != 0 && weight != 0){
//            heightEdit_Text.setText(height.toString())
//            weightEdit_Text.setText(weight.toString())
//        }

        if (App.prefs.KEY_HEIGHT != null && App.prefs.KEY_WEIGHT != null && App.prefs.KEY_NAME != null){
            heightEdit_Text.setText(App.prefs.KEY_HEIGHT)
            weightEdit_Text.setText(App.prefs.KEY_WEIGHT)
            nameEdit_Text.setText(App.prefs.KEY_NAME)
        }




    }
}


//글로벌 변수 설정
class MySharedPreference(context: Context){

    val PREFS_FILENAME = "prefs"
    val PREF_KEY_HEIGHT = "KEY_HEIGHT"
    val PREF_KEY_WIEGHT = "KEY_WEIGHT"
    val PREF_KEY_NAME = "KEY_NAME"
    val prefs : SharedPreferences = context.getSharedPreferences(PREFS_FILENAME,0)

    //키
    var KEY_HEIGHT : String?
        get() = prefs.getString(PREF_KEY_HEIGHT, "")
        set(value) = prefs.edit().putString(PREF_KEY_HEIGHT, value).apply()

    //몸무게
    var KEY_WEIGHT : String?
        get() = prefs.getString(PREF_KEY_WIEGHT, "")
        set(value) = prefs.edit().putString(PREF_KEY_WIEGHT, value).apply()

    //이름
    var KEY_NAME : String?
        get() = prefs.getString(PREF_KEY_NAME, "")
        set(value) = prefs.edit().putString(PREF_KEY_NAME, value).apply()

}



//App Class MySharedPreference 생성
class App : Application(){
    companion object{
        lateinit var prefs : MySharedPreference
    }

    //OnCreate 실행 부분 생성
    override fun onCreate() {
        prefs = MySharedPreference(applicationContext)
        super.onCreate()
    }

}
