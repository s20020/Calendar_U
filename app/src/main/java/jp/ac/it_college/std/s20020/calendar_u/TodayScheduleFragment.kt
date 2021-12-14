package jp.ac.it_college.std.s20020.calendar_u

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentStartBinding
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentTodayScheduleBinding


class TodayScheduleFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentTodayScheduleBinding? = null
    private val binding get() = _binding!!

    var year = 0
    var month = 0
    var day = 0

    val args: TodayScheduleFragmentArgs by navArgs()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        inflater.inflate(R.layout.fragment_today_schedule, container, false)
        _binding = FragmentTodayScheduleBinding.inflate(inflater, container, false)

        // 選ばれた日付ごとのタイトルを設定。
        setTodayTitle()

        return binding.root
    }
        fun setTodayTitle() {
            year = args.year
            month = args.month
            day = args.day
            binding.todaytitle.text = "${year}年${month}月${day}日の日程"
        }

}