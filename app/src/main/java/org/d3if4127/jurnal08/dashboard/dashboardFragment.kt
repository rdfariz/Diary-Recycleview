package org.d3if4127.jurnal08.dashboard

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.d3if4127.jurnal08.R
import org.d3if4127.jurnal08.data.DiaryViewModel
import org.d3if4127.jurnal08.data.DiaryViewModelFactory
import org.d3if4127.jurnal08.database.DiaryDatabase
import org.d3if4127.jurnal08.databinding.FragmentDashboardBinding

/**
 * A simple [Fragment] subclass.
 */
class dashboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var diaryVM: DiaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = DiaryDatabase.getInstance(
            application
        ).DiaryDao
        val viewModelFactory = DiaryViewModelFactory(dataSource, application)
        diaryVM = ViewModelProviders.of(this, viewModelFactory).get(DiaryViewModel::class.java)
        binding.diaryVM = diaryVM
        binding.setLifecycleOwner(this)


        viewManager = LinearLayoutManager(context)
        diaryVM.list_diary.observe(viewLifecycleOwner, Observer {
            viewAdapter = diaryAdapter(it)
            recyclerView = binding.rvDiary.apply {
                layoutManager = viewManager
                adapter = viewAdapter
            }
        })

        binding.toWriteDiary.setOnClickListener {view: View ->

            view.findNavController().navigate(R.id.action_dashboardFragment_to_writeFragment)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.clearData -> clearData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun clearData() {
        diaryVM.onClear()
    }

}
