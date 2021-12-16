package jp.ac.it_college.std.s20020.calendar_u

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentStartBinding
import java.time.Year
import kotlin.math.ceil


class StartFragment : Fragment() {

    var title = ""
    var s_time = ""
    var e_time = ""
    var memo = ""

    val myArray = arrayListOf<ArrayList<String>>()


    private var Year = ""
    private var Month = ""
    private var Day = ""
//    private var list = List
//    private var listlist = arrayListOf<Array>()

    private var _binding : FragmentStartBinding? = null
    private val binding get() = _binding!!

    private lateinit var _helper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //DatabaseHelperオブジェクトを生成
        _helper = DatabaseHelper(requireContext())





        // NavHostの取得
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // NavController取得
        val navController = navHostFragment.navController



        binding.calendarView.setOnDateChangeListener { view, year, month, day ->
            datebaseSelect(year, month+1, day)
            //アクション
            val action = StartFragmentDirections.actionStartFragmentToTodayScheduleFragment(year,month+1,day)
            navController.navigate(action)
        }

    }

    override fun onDestroy() {
        _helper.close()
        super.onDestroy()
    }

    fun datebaseSelect(year: Int, month: Int, day: Int) {


        Month = month.toString().padStart(2,'0')
        Day = day.toString().padStart(2,'0')

        val select = """
            SELECT title, s_time, e_time, memo FROM SCHEDULE
            WHERE date = "${year}-${Month}-${Day}"
        """.trimIndent()

        //データベース接続オブジェクト取得
        val db = _helper.writableDatabase



        val c = db.rawQuery(select, null)
        if(c.moveToNext()){
            val cTitle = c.getColumnIndex("title")
            title = c.getString(cTitle)
            val cStime = c.getColumnIndex("s_time")
            s_time = c.getString(cStime)
            val cEtime = c.getColumnIndex("e_time")
            e_time = c.getString(cEtime)
            val cMemo = c.getColumnIndex("memo")
            memo = c.getString(cMemo)
            val a = arrayListOf(title, s_time, e_time, memo)
            myArray.add(a)
            println(title)
            println(s_time)
            println(e_time)
            println(memo)

            while(c.moveToNext()){

//                val cTitle = c.getColumnIndex("title")
                title = c.getString(cTitle)
//                val cStime = c.getColumnIndex("s_time")
                s_time = c.getString(cStime)
//                val cEtime = c.getColumnIndex("e_time")
                e_time = c.getString(cEtime)
//                val cMemo = c.getColumnIndex("memo")
                memo = c.getString(cMemo)
                val b = arrayListOf(title, s_time, e_time, memo)
                myArray.add(b)
                println(title)
                println(s_time)
                println(e_time)
                println(memo)
            }
        }
        else {
            println("予定なし")
        }

        println(myArray)

    }


}