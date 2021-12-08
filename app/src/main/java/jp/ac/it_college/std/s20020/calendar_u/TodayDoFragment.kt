package jp.ac.it_college.std.s20020.calendar_u

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentTodayDoListDialogItemBinding
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentTodayDoListDialogBinding


class TodayDoFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentTodayDoListDialogBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTodayDoListDialogBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}