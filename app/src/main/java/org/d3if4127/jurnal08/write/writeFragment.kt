package org.d3if4127.jurnal08.write

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import org.d3if4127.jurnal08.R
import org.d3if4127.jurnal08.data.DiaryViewModel
import org.d3if4127.jurnal08.data.DiaryViewModelFactory
import org.d3if4127.jurnal08.database.DiaryDatabase
import org.d3if4127.jurnal08.databinding.FragmentWriteBinding

/**
 * A simple [Fragment] subclass.
 */
class writeFragment : Fragment() {

    private lateinit var binding: FragmentWriteBinding
    private lateinit var diaryVM: DiaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_write, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = DiaryDatabase.getInstance(application).DiaryDao
        val viewModelFactory = DiaryViewModelFactory(dataSource, application)
        diaryVM = ViewModelProviders.of(this, viewModelFactory).get(DiaryViewModel::class.java)
        binding.setLifecycleOwner(this)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.write_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.writeDiary -> writeDiary()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun writeDiary() {
        try {
            var content = binding.inputDiary.text.toString()
            diaryVM.onPush(content)
            Toast.makeText(this.context, "Berhasil tambah diary", Toast.LENGTH_SHORT).show()
            this.view?.findNavController()?.navigate(R.id.action_writeFragment_to_dashboardFragment)
        }catch (e: Exception) {
            Toast.makeText(this.context, "Data tidak valid", Toast.LENGTH_SHORT).show()
        }
    }

}
