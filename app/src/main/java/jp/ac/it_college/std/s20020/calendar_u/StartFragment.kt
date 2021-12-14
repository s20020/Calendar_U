package jp.ac.it_college.std.s20020.calendar_u

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private var _binding : FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // NavHostの取得
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // NavController取得
        val navController = navHostFragment.navController



        binding.calendarView.setOnDateChangeListener { view, year, month, dayOffMonth ->
            //アクション
            val action = StartFragmentDirections.actionStartFragmentToTodayScheduleFragment(year,month,dayOffMonth)
            navController.navigate(action)
        }

    }


}