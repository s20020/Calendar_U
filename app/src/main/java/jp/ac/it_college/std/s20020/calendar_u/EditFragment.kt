package jp.ac.it_college.std.s20020.calendar_u

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentEditBinding
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentTodayScheduleBinding


class EditFragment : Fragment() {

    private var _binding : FragmentEditBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.fragment_today_schedule, container, false)
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}